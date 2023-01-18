import { State, StateContext, Action, Selector } from '@ngxs/store';
import { ContratoModel } from './contrato.state.model';
import { ContratoService } from './contrato.service';
import { AnexoService } from '../anexo/anexo.service';
import { tap } from 'rxjs/operators';
import { LoadAnexosPorTipo, LoadAnexosPorConjuntoTipos } from '../anexo/anexo.actions';
import { StaticReflector } from '@angular/compiler';
import { ChecklistContrato, ConcluirContrato } from './contrato.actions';

@State<ContratoModel>({
    name: 'contratoSelecionado',
    defaults: null
})
export class ContratoSelecionadoState {

    // injecao de dependencia dos services que chamarao o backend
    constructor(
        private anexoService: AnexoService,
        private contratoService: ContratoService
    ) {}

    @Selector()
    static anexosContrato(state: ContratoModel) {
      if (state.anexos) {
        return state.anexos;
      }
      return [];
    }

    @Selector()
    static checklistContrato(state: ContratoModel) {
      if (state.dadosChecklist) {
        return state.dadosChecklist;
      }
      return null;
    }

    @Action(LoadAnexosPorTipo)
    recuperarAnexosPorTipo(ctx: StateContext<ContratoModel>, acao: LoadAnexosPorTipo)  {
      return this.anexoService.recuperarAnexosPorTipo(acao.idContrato, 'INSTRUMENTO_CONTRATUAL').pipe(
        tap(anexosP => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            anexos: anexosP
          });
        })
      );
    }

    @Action(ChecklistContrato)
    checklistContrato(ctx: StateContext<ContratoModel>, action: ChecklistContrato) {
      return this.contratoService.checklistContrato(action.id)
        .pipe(
          tap(dadosCheck => {
            const state = ctx.getState();
            ctx.setState({
              ...state,
              dadosChecklist: dadosCheck
            });
          })
        );
    }

    @Action(LoadAnexosPorConjuntoTipos)
    recuperarAnexosPorConjuntoTipos(ctx: StateContext<ContratoModel>, acao: LoadAnexosPorConjuntoTipos)  {
      return this.anexoService.recuperarAnexosPorConjuntoTipos(acao.idContrato, acao.tiposAnexo).pipe(
        tap(anexosP => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            anexos: anexosP
          });
        })
      );
    }
}
