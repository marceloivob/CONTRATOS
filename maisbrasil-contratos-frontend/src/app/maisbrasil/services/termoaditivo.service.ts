import { Observable } from 'rxjs/Observable';
import { Injectable, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { ContratoModel, SituacaoContrato } from 'src/app/model/contrato/contrato.state.model';

// Source: https://stackoverflow.com/a/41989983/6644646
@Injectable({ providedIn: 'root'})
export class ShowTermoAditivoTabService implements OnDestroy {
    // Observable string sources
    private emitChangeSource = new Subject<any>();

    // Observable string streams
    changeEmitted$ = this.emitChangeSource.asObservable();

    exibeTermoAditivo(contrato: ContratoModel): boolean {

        const hoje: Date = new Date();
        hoje.setHours(0, 0, 0, 0); // Remove a hora e o TimeZone
        hoje.setDate(1); // ignorar dia do mes (só leva em conta mes/ano)

        let fimVigencia: Date = new Date(contrato.fimVigencia);
        fimVigencia = new Date(fimVigencia.toISOString().slice(0, -1)); // ajustar timezone
        fimVigencia.setDate(1); // ignorar dia do mes (só leva em conta mes/ano)
    
        const vigenciaExpirada = hoje > fimVigencia;

        let situacao = contrato.inSituacaoExibicao;

        if (!situacao) {
            situacao = contrato.inSituacao;
        }

        let show = situacao == SituacaoContrato.CONCLUIDO || 
            situacao == SituacaoContrato.RASCUNHO_ADITIVACAO_EM_RASCUNHO ||
            situacao == SituacaoContrato.RASCUNHO_ADITIVACAO_CONCLUIDA ||
            situacao == SituacaoContrato.CONCLUIDO_ADITIVACAO_EM_RASCUNHO ||
            situacao == SituacaoContrato.CONCLUIDO_ADITIVACAO_CONCLUIDA ||
            (situacao == SituacaoContrato.EM_RASCUNHO && vigenciaExpirada);

        return show;
    }

    exibeEmitTermoAditivo(contrato: ContratoModel) {
        this.emitChangeSource.next(this.exibeTermoAditivo(contrato));

    }

    ngOnDestroy() {
        this.emitChangeSource.unsubscribe();
    }

}
