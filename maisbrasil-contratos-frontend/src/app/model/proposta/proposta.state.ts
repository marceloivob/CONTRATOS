import { State, Action, StateContext, Store, Selector } from '@ngxs/store';
import { tap, catchError } from 'rxjs/operators';

import { PropostaStateModel, Modalidade } from './proposta.state.model';
import { LoadHistoricoContratosDaProposta, LoadHistoricoContratosExcluidosDaProposta, LoadProposta } from './proposta.actions';
import { PropostaService } from './proposta.service';
import { forkJoin, of } from 'rxjs';
import { SiconvLegacyService } from '../siconv/siconv-legacy.service';
import { Navigate } from '@ngxs/router-plugin';

@State<PropostaStateModel>({
  name: 'proposta',
  defaults: null
})
export class PropostaState {

  constructor(
    private readonly propostaService: PropostaService,
    private readonly siconvService: SiconvLegacyService) { }



   @Selector()
    static getProposta(state: PropostaStateModel) {
      if (state) {
        return state;
      }
    }

  @Selector()
  static getModalidade(state: PropostaStateModel) {
    if (state && state.modalidade) {
      return state.modalidade;
    }
  }

  @Selector()
  static getPossuiMandataria(state: PropostaStateModel) {
    if (state && state.termoCompromissoTemMandatar) {
      return state.termoCompromissoTemMandatar;
    }
  }

  @Selector()
  static getFornecedores(state: PropostaStateModel) {
    if (state && state.fornecedores) {
      return state.fornecedores;
    }
  }

  @Selector()
  static getMensagens(state: PropostaStateModel) {
    if (state && state.mensagens) {
      return state.mensagens;
    }
  }

  @Selector()
  static getHistoricoContratos(state: PropostaStateModel) {
    if (state && state.historicoContratos) {
      return state.historicoContratos;
    }
    return [];
  }

  @Selector()
  static getHistoricoContratosExcluidos(state: PropostaStateModel) {
    if (state && state.historicoContratosExcluidos) {
      return state.historicoContratosExcluidos;
    }
    return [];
  }


  @Selector()
  static getModalidadeString(state: PropostaStateModel) {
    if (state && state.modalidade) {
      switch (state.modalidade) {
        case Modalidade.CONTRATO_REPASSE:
          return 'Contrato de Repasse';
        case Modalidade.CONVENIO:
          return 'Convênio';
        case Modalidade.CONVENIO_CONTRATO_REPASSE:
          return 'Convênio ou Contrato de Repasse';
          case Modalidade.TERMO_DE_COMPROMISSO:
            return 'Termo de Compromisso';
        default:
          return 'Instrumento (modalidade desconhecida)';
      }
    }
  }

  @Action(LoadProposta)
  loadProposta(ctx: StateContext<PropostaStateModel>) {
    return forkJoin(
        this.propostaService.loadProposta()
          .pipe(catchError( err => {
            console.error('Falha carregamento Proposta!', err);
            ctx.dispatch(new Navigate(['erro-carregamento-proposta']));
            throw err;
          })),
        this.siconvService.loadUrlRetornoSiconv()
          .pipe(catchError( err => {
            console.error('Falha integração SICONV!!');
            return of('');
          })),
        this.siconvService.getMenu().pipe(catchError( err => {
          console.error('Falha integração SICONV!!');
          return of({InfoConvenio: ''});
        }))
      ).pipe(
        tap(([propostaOriginal, urlRetorno, menu]) => {
          const propostaModel: PropostaStateModel = {
            ...propostaOriginal,
            urlRetornoSiconv: urlRetorno,
            infoConvenio: menu.InfoConvenio
          };
          ctx.patchState(propostaModel);
        })
      );
  }

  @Action(LoadHistoricoContratosDaProposta)
  loadHistoricoContratosDaProposta(ctx: StateContext<PropostaStateModel>, action: LoadHistoricoContratosDaProposta) {
    return this.propostaService.loadHistoricoContratosDaProposta(action.idProposta).pipe(
        tap(historico => {
            const state = ctx.getState();
            ctx.setState({
              ...state,
              historicoContratos: historico
            });
        })
    );
  }

  @Action(LoadHistoricoContratosExcluidosDaProposta)
  loadHistoricoContratosExcluidosDaProposta(ctx: StateContext<PropostaStateModel>, action: LoadHistoricoContratosExcluidosDaProposta) {
    return this.propostaService.loadHistoricoContratosExcluidosDaProposta(action.idProposta).pipe(
        tap(historico => {
            const state = ctx.getState();
            ctx.setState({
              ...state,
              historicoContratosExcluidos: historico
            });
        })
    );
  }


}
