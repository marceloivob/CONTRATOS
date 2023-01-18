import { State, StateContext, Action, Selector } from '@ngxs/store';
import { AnexoService } from '../anexo/anexo.service';
import { tap } from 'rxjs/operators';
import { LoadAnexosPorTipo, LoadAnexosPorConjuntoTipos, LoadAnexosTermoAditivoPorConjuntoTipos } from '../anexo/anexo.actions';
import { StaticReflector } from '@angular/compiler';
import { TermoAditivoModel } from './termo-aditivo.state.model';
import { TermoAditivoService } from './termo-aditivo.service';

@State<TermoAditivoModel>({
    name: 'termoaditivoselecionado',
    defaults: null
})
export class TermoAditivoSelecionadoState {

    // injecao de dependencia dos services que chamarao o backend
    constructor(
        private anexoService: AnexoService,
        private termoAditivoService: TermoAditivoService
    ) {}

    @Selector()
    static anexosTermoAditivo(state: TermoAditivoModel) {
      if (state && state.anexos) {
        return state.anexos;
      }
      return [];
    }

     @Action(LoadAnexosTermoAditivoPorConjuntoTipos)
     recuperarAnexosPorConjuntoTipos(ctx: StateContext<TermoAditivoModel>, acao: LoadAnexosTermoAditivoPorConjuntoTipos)  {
       return this.anexoService.recuperarAnexosTermoAditivoPorConjuntoTipos(acao.idTermoAditivo, acao.tiposAnexo).pipe(
         tap(anexosP => {
           const state = ctx.getState();
           ctx.setState({
             ...state,
             anexos: anexosP
           });
         })
       );
     }


    // @Action(LoadAnexosPorTipo)
    // recuperarAnexosPorTipo(ctx: StateContext<ContratoModel>, acao: LoadAnexosPorTipo)  {
    //   return this.anexoService.recuperarAnexosPorTipo(acao.idContrato, 'INSTRUMENTO_CONTRATUAL').pipe(
    //     tap(anexosP => {
    //       const state = ctx.getState();
    //       ctx.setState({
    //         ...state,
    //         anexos: anexosP
    //       });
    //     })
    //   );
    // }

}
