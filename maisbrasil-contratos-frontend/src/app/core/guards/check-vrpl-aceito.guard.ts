import { PropostaService } from '../../model/proposta/proposta.service';
import { tap, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Store } from '@ngxs/store';
import { Navigate } from '@ngxs/router-plugin';

@Injectable()
export class CheckVRPLAceitoGuard implements CanActivate {

  constructor(private readonly store: Store, private readonly propostaService: PropostaService) { }

  canActivate(_next: ActivatedRouteSnapshot, _state: RouterStateSnapshot): Observable<boolean> {

    const ERRO_PROPOSTA_SEM_VRPL_ACEITO = 540930;
    const ERRO_PROPOSTA_NAO_ENCONTRADA = 513493;
    const ERRO_LICITACAO_NAO_ENCONTRADA_PROPOSTA = 7034561;

    return this.propostaService.verificaInicioContratos().pipe(
      tap(_retorno => {
        return true;
      }
      ),
      catchError(err => {

        if (err.error == null) {
          this.store.dispatch(new Navigate(['erro-carregamento-proposta']));
        }else{

          switch (err.error.data.codigo) {
            case ERRO_LICITACAO_NAO_ENCONTRADA_PROPOSTA:
                this.store.dispatch(new Navigate(['proposta-nao-encontrada-vrpl']));
                break;

            case ERRO_PROPOSTA_SEM_VRPL_ACEITO:
                this.store.dispatch(new Navigate(['nao-existe-vrpl-aceito']));
                break;

            case ERRO_PROPOSTA_NAO_ENCONTRADA:
                this.store.dispatch(new Navigate(['proposta-nao-encontrada']));
                break;

            default:
                this.store.dispatch(new Navigate(['erro-carregamento-proposta']));
                break;
          }

        }

        return of(false);
      })
    );

  }
}
