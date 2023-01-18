import { State, Action, StateContext, Selector } from '@ngxs/store';

import { tap } from 'rxjs/operators';
import { DeleteAnexoTermoAditivo, SaveAnexoTermoAditivo } from '../anexo/anexo.actions';
import { AnexoService } from '../anexo/anexo.service';
import { AtualizarTermoAditivo, ConcluirNovoTermoAditivo, ConcluirTermoAditivo, DeleteTermoAditivo, LimparTermoAditivoSelecionado, LoadTermoAditivoSelecionado, LoadTermosAditivosPorContrato, SalvarTermoAditivo } from './termo-aditivo.actions';
import { TermoAditivoService } from './termo-aditivo.service';
import { TermoAditivoStateModel } from './termo-aditivo.state.model';

@State<TermoAditivoStateModel>({
    name: 'termoaditivo',
    defaults: {},
   // children: [TermoAditivoSelecionadoState]
})
export class TermoAditivoState {

    // injecao de dependencia dos services que chamarao o backend
    constructor(
        private termoAditivoService: TermoAditivoService,
        private anexoService: AnexoService
    ) {}

    // selectors

    @Selector()
    static termosAditivosPorContrato(state: TermoAditivoStateModel) {
      if (state && state.termosAditivos) {
        return state.termosAditivos;
      }
      return [];
    }

    @Selector()
    static termoAditivoSelecionado(state: TermoAditivoStateModel) {
        return state.termoAditivoSelecionado;
    }

    // actions

    @Action(LoadTermosAditivosPorContrato)
    loadTermosAditivosPorContrato(ctx: StateContext<TermoAditivoStateModel>, action: LoadTermosAditivosPorContrato) {
        return this.termoAditivoService.loadTermosAditivosPorContrato(action.idContrato)
            .pipe(
                tap(termos => {
                    const state = ctx.getState();
                    ctx.setState({
                      ...state,
                      termosAditivos: termos
                    });
                })
            );
    }

    @Action(DeleteTermoAditivo)
    deleteTermoAditivo(ctx: StateContext<TermoAditivoStateModel>, action: DeleteTermoAditivo) {
      return this.termoAditivoService.deleteTermoAditivo(action.id);
    }

    @Action(SalvarTermoAditivo)
    salvarTermoAditivo(ctx: StateContext<TermoAditivoStateModel>, action: SalvarTermoAditivo) {
        return this.termoAditivoService.salvarTermoAditivo(action.termoAditivo).pipe(
          tap(termoAditivoSalvo => {
              const state = ctx.getState();
              ctx.setState({
                ...state,
                termoAditivoSelecionado: termoAditivoSalvo
              });
          })
      );
    }

    @Action(AtualizarTermoAditivo)
    atualizarTermoAditivo(ctx: StateContext<TermoAditivoStateModel>, action: AtualizarTermoAditivo) {
        return this.termoAditivoService.atualizarTermoAditivo(action.termoAditivo).pipe(
          tap(termoAditivoSalvo => {
              const state = ctx.getState();
              ctx.setState({
                ...state,
                termoAditivoSelecionado: termoAditivoSalvo
              });
          })
      );
    }

    @Action(LoadTermoAditivoSelecionado)
    loadTermoAditivoSelecionado({ patchState }: StateContext<TermoAditivoStateModel>, action: LoadTermoAditivoSelecionado) {
      return this.termoAditivoService.loadTermoAditivoPorId(action.idTermoAditivo).pipe(
        tap(termo => {
          patchState({
            termoAditivoSelecionado: {
              ...termo
            }
          });
        })
      );
    }

  @Action(LimparTermoAditivoSelecionado)
  limparTermoAditivoSelecionado(ctx: StateContext<TermoAditivoStateModel>, action: LimparTermoAditivoSelecionado) {
      const state = ctx.getState();
      ctx.setState({
        ...state,
        termoAditivoSelecionado: null
      });
  }

  @Action(ConcluirNovoTermoAditivo)
  concluirNovoTermoAditivo(ctx: StateContext<TermoAditivoStateModel>, action: ConcluirNovoTermoAditivo) {
      return this.termoAditivoService.concluirNovoTermoAditivo(action.termoAditivo).pipe(
        tap(termoAditivoSalvo => {
            const state = ctx.getState();
            ctx.setState({
              ...state,
              termoAditivoSelecionado: termoAditivoSalvo
            });
        })
    );
  }

  @Action(ConcluirTermoAditivo)
  concluirTermoAditivo(ctx: StateContext<TermoAditivoStateModel>, action: ConcluirTermoAditivo) {
      return this.termoAditivoService.concluirTermoAditivo(action.termoAditivo).pipe(
        tap(termoAditivoSalvo => {
            const state = ctx.getState();
            ctx.setState({
              ...state,
              termoAditivoSelecionado: termoAditivoSalvo
            });
        })
    );
  }

  @Action(DeleteAnexoTermoAditivo)
  deleteAnexo(ctx: StateContext<TermoAditivoState>, action: DeleteAnexoTermoAditivo) {
    return this.anexoService.deleteAnexoTermoAditivo(action.anexo);
  }

  @Action(SaveAnexoTermoAditivo)
  saveAnexo(ctx: StateContext<TermoAditivoState>, action: SaveAnexoTermoAditivo) {
    return this.anexoService.salvarAnexoTermoAditivo(action.idTermoAditivo, action.anexo);
  }
}
