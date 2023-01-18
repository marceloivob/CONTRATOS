import { State, Action, StateContext, Selector } from '@ngxs/store';
import { ContratoStateModel } from './contrato.state.model';
import { LoadContratosPorProposta, DeleteContrato, SalvarContrato, AtualizarContrato,
  LimparContratoSelecionado, ConcluirContrato, VerificarEmissaoAIO, VerificarCancelarEmissaoAIO,
  CancelarEmissaoAIO } from './contrato.actions';
import { ContratoService } from './contrato.service';
import { tap } from 'rxjs/operators';
import { LoadContratoSelecionado, SaveAnexo, DeleteAnexo } from '../anexo/anexo.actions';
import { AnexoService } from '../anexo/anexo.service';
import { ContratoSelecionadoState } from './contratoSelecionado.state';

@State<ContratoStateModel>({
    name: 'contrato',
    defaults: {},
    children: [ContratoSelecionadoState]
})
export class ContratoState {

    // injecao de dependencia dos services que chamarao o backend
    constructor(
        private contratoService: ContratoService,
        private anexoService: AnexoService
    ) {}

    // selectors

    @Selector()
    static contratosPorProposta(state: ContratoStateModel) {
      if (state.contratos) {
        return state.contratos;
      }
      return [];
    }

    @Selector()
    static contratoSelecionado(state: ContratoStateModel) {
        return state.contratoSelecionado;
    }

    @Selector()
    static anexosContratoSelecionado(state: ContratoStateModel) {
        return state.contratoSelecionado.anexos;
    }

    @Selector()
    static mensagensContrato(state: ContratoStateModel) {
      if (state.mensagens) {
        return state.mensagens;
      }
      return [];
    }

    // actions

    @Action(LoadContratosPorProposta)
    loadContratosPorProposta(ctx: StateContext<ContratoStateModel>, contrato: LoadContratosPorProposta) {
        return this.contratoService.loadContratosPorProposta(contrato.idProposta)
            .pipe(
                tap(conts => {
                    const state = ctx.getState();
                    ctx.setState({
                      ...state,
                      contratos: conts
                    });
                })
            );
    }

    @Action(DeleteContrato)
    deleteContrato(ctx: StateContext<ContratoStateModel>, action: DeleteContrato) {
        return this.contratoService.deleteContrato(action.id);
    }

    @Action(SalvarContrato)
    salvarContrato(ctx: StateContext<ContratoStateModel>, action: SalvarContrato) {
        return this.contratoService.salvarContrato(action.contrato).pipe(
          tap(contratoSalvo => {
              const state = ctx.getState();
              ctx.setState({
                ...state,
                contratoSelecionado: contratoSalvo
              });
          })
      );
    }

    @Action(AtualizarContrato)
    atualizarContrato(ctx: StateContext<ContratoStateModel>, action: AtualizarContrato) {
        return this.contratoService.atualizarContrato(action.contrato).pipe(
          tap(contratoSalvo => {
              const state = ctx.getState();
              ctx.setState({
                ...state,
                contratoSelecionado: contratoSalvo
              });
          })
      );
    }

    @Action(LoadContratoSelecionado)
    loadContratoSelecionado({ patchState }: StateContext<ContratoStateModel>, action: LoadContratoSelecionado) {
      return this.contratoService.loadContratoPorId(action.idContrato).pipe(
        tap(contrato => {
          patchState({
            contratoSelecionado: {
              ...contrato
            }
          });
        })
      );
    }

  @Action(DeleteAnexo)
  deleteAnexo(ctx: StateContext<ContratoStateModel>, action: DeleteAnexo) {
    return this.anexoService.deleteAnexo(action.anexo);
  }

  @Action(SaveAnexo)
  saveAnexo(ctx: StateContext<ContratoStateModel>, action: SaveAnexo) {
    return this.anexoService.salvarAnexo(action.idContrato, action.anexo);
  }

  @Action(LimparContratoSelecionado)
  limparContratoSelecionado(ctx: StateContext<ContratoStateModel>, action: LimparContratoSelecionado) {
      const state = ctx.getState();
      ctx.setState({
        ...state,
        contratoSelecionado: null
      });
  }


  @Action(ConcluirContrato)
  ConcluirContrato(ctx: StateContext<ContratoStateModel>, action: ConcluirContrato) {
    return this.contratoService.concluirContrato(action.contrato).pipe(
      tap(msgs => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            mensagens: msgs
          });
      })
    );
  }

  @Action(VerificarEmissaoAIO)
  VerificarEmissaoAIO(ctx: StateContext<ContratoStateModel>, action: VerificarEmissaoAIO) {
    return this.contratoService.verificarEmissaoAIO(action.request).pipe(
      tap(msgs => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            mensagens: msgs
          });
      })
    );
  }

  @Action(VerificarCancelarEmissaoAIO)
  VerificarCancelarEmissaoAIO(ctx: StateContext<ContratoStateModel>, action: VerificarCancelarEmissaoAIO) {
    return this.contratoService.verificarCancelarEmissaoAIO(action.idProposta).pipe(
      tap(msgs => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            mensagens: msgs
          });
      })
    );
  }

  @Action(CancelarEmissaoAIO)
  CancelarEmissaoAIO(ctx: StateContext<ContratoStateModel>, action: CancelarEmissaoAIO) {
    return this.contratoService.cancelarEmissaoAIO(action.idProposta).pipe(
      tap(msgs => {
          const state = ctx.getState();
          ctx.setState({
            ...state,
            mensagens: msgs
          });
      })
    );
  }

}
