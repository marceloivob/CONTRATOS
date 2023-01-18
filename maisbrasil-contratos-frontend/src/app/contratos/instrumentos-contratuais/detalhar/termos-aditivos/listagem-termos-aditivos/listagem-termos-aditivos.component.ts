import { Component, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Navigate } from '@ngxs/router-plugin';
import { Select, Store } from '@ngxs/store';
import { DataExport } from 'src/app/model/data-export';
import { Observable } from 'rxjs';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { ContratoModel } from 'src/app/model/contrato/contrato.state.model';
import { DeleteTermoAditivo, LoadTermosAditivosPorContrato } from 'src/app/model/termo-aditivo/termo-aditivo.actions';
import { TermoAditivoState } from 'src/app/model/termo-aditivo/termo-aditivo.state';
import { SituacaoTermoAditivo, TermoAditivoModel } from 'src/app/model/termo-aditivo/termo-aditivo.state.model';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';

@Component({
  selector: 'app-listagem-termos-aditivos',
  templateUrl: './listagem-termos-aditivos.component.html',
  styleUrls: ['./listagem-termos-aditivos.component.css']
})
export class ListagemTermosAditivosComponent extends BaseComponent {

  @Select(TermoAditivoState.termosAditivosPorContrato) termosAditivosObservable: Observable<TermoAditivoModel[]>;
  
  termosAditivos: TermoAditivoModel[];
  modalRef: BsModalRef;

  termoAditivoAExcluir: TermoAditivoModel;

  contratoSelecionado: ContratoModel;

  editar: boolean = false;

  export: DataExport;

  lista: any[];

  constructor(
    protected store: Store,
    private readonly route: ActivatedRoute,
    private readonly modalService: BsModalService,
    private readonly authService: UserAuthorizerService,
    private readonly  alertMessageService: AlertMessageService) {
      super(store);
  }

  init() {
    
    if (this.route.parent.snapshot.url[1].path === 'e') {
      this.editar = true;
    } else if (this.route.parent.snapshot.url[1].path === 'd') {
      this.editar = false;
    }
    
  }

  loadActions() {
    this.contratoSelecionado = this.store.selectSnapshot(ContratoState.contratoSelecionado);

    return new LoadTermosAditivosPorContrato(this.contratoSelecionado.id);
  }

  onLoad() {
    this.termosAditivosObservable.subscribe(termos =>{
      this.termosAditivos = termos;
      this.getExport(this.termosAditivos);
    });
  }

  voltar() {
    this.store.dispatch(this.voltarListagemAction());
    this.alertMessageService.dismissAll();
  }

  voltarListagemAction() {
    let caminho = '../../../../listagem';

    return new Navigate([caminho], {}, { relativeTo: this.route });
  }

  isProponente() {
    return this.authService.isProponente;
  }

  permiteEdicao(){
    return this.isProponente && this.editar;

  }

  getListaPaginada(listap) {
    this.lista = listap;
  }

  getExport(termos: TermoAditivoModel[]): TermoAditivoModel[] {
    const data = [];
    const columns = [
      'Número', 'Tipo do Aditivo',
      'Data de Fim de Vigência', 'Data de Publicação'
    ];

    if (termos) {
      termos.forEach(termo => {
        const linha = [];
        linha.push(termo.numero);
        linha.push(termo.tipoTermoAditivo);
        linha.push(termo.novaDataFimVigencia);
        linha.push(termo.dataPublicacao);
        data.push(linha);
      });
    }

    this.export = new DataExport(columns, data);

    return termos;
  }


  deleteTermoAditivo(termo: TermoAditivoModel, template: TemplateRef<any>) {
    this.termoAditivoAExcluir = termo;
    this.modalRef = this.modalService.show(template);
  }

  confirmaExclusao() {
    this.dispatch(new DeleteTermoAditivo(this.termoAditivoAExcluir.id))
      .subscribe( () => {
        this.alertMessageService.success('Exclusão do Termo Aditivo realizada com sucesso.');
        this.dispatch(
          [
            new LoadTermosAditivosPorContrato(this.contratoSelecionado.id)
          ]
        );
      });

    this.modalRef.hide();
    this.termoAditivoAExcluir = null;
  }

  cancelaExclusao() {
    this.modalRef.hide();
    this.termoAditivoAExcluir = null;
  }

  exibirBotaoAdicionar() {
    let exibir = false;

    if (!this.termosAditivos || this.termosAditivos.length === 0) {
      exibir = true;
    } else {
      exibir = (this.termosAditivos.length > 0) && 
        !this.termosAditivos.find(termo => termo.inSituacao === SituacaoTermoAditivo.EM_RASCUNHO);
    }
    
    return exibir;
  }
}
