import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, finalize } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import { HTTPRequest, HTTPResponse } from '../../model/app/app.actions';
import { InterceptorMessageSkipHeader } from './app.state.model';
import { AlertMessageService } from '@serpro/ngx-siconv';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(private store: Store, private alertMessageService: AlertMessageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.store.dispatch(new HTTPRequest());
    const skipConnectionError = req.headers.has(InterceptorMessageSkipHeader);

    const request = req.clone({
      headers: req.headers.delete(InterceptorMessageSkipHeader)
    });

    return next.handle(request).pipe(
      map(event => {
        if (event instanceof HttpResponse) {
          const body = event.body;
          if (body && body.data !== undefined) {
            event = event.clone({ body: body.data });
          } else {
            event = event.clone({ body: body });
          }
        }
        return event;
      }),
      catchError(err => {
        const HTTP_STATUS_CONFLICT = 409;

        if (err.status === HTTP_STATUS_CONFLICT) {
          const MENSAGEM_ERRO_CONCORRENCIA = 'Não foi possível realizar a operação. ' +
            'O conjunto atual de Documentos Orçamentários já foi modificado por outra transação. ' +
            'Será necessário acessar novamente a aba Verificação do Resultado do Processo Licitatório.';
          this.alertMessageService.error(MENSAGEM_ERRO_CONCORRENCIA);
        } else {
          const body = err.error;

          if (body && body.status && body.status === 'fail') {
            if (body.data.severity === 'ERROR') {
              this.alertMessageService.error(body.data.message);
            } else if (body.data.severity === 'WARN') {
              this.alertMessageService.warn(body.data.message);
            }
          } else if (body && body.status && body.status === 'error') {
            this.alertMessageService.error(body.message, body.trace);
          } else if (!skipConnectionError) {
            this.alertMessageService.error('Falha de comunicação com o servidor!');
          }
        }

        return throwError(err);
      }),
      finalize(() => {
        this.store.dispatch(new HTTPResponse());
      })
    );
  }
}
