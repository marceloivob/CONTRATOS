import { Component, TemplateRef } from '@angular/core';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Store, Select } from '@ngxs/store';
import { ChecklistContrato, ConcluirContrato, VerificarEmissaoAIO, CancelarEmissaoAIO, 
  VerificarCancelarEmissaoAIO  } from 'src/app/model/contrato/contrato.actions';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { ContratoModel, CheckModel, DadosChecklistModel, MensagemModel,
  SeveridadeMensagem, 
  SituacaoAIO,
  EmissaoAIORequestModel} from 'src/app/model/contrato/contrato.state.model';
import { ContratoSelecionadoState } from 'src/app/model/contrato/contratoSelecionado.state';
import { Observable } from 'rxjs';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { ActivatedRoute } from '@angular/router';
import { Navigate } from '@ngxs/router-plugin';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';
import { LoadContratoSelecionado } from 'src/app/model/anexo/anexo.actions';
import { AppConfig } from 'src/app/core/app.config';

@Component({
  selector: 'app-checklist',
  templateUrl: './checklist.component.html',
  styleUrls: ['./checklist.component.scss']
})
export class ChecklistComponent extends BaseComponent {

  @Select(ContratoSelecionadoState.checklistContrato) checklistObservable: Observable<DadosChecklistModel>;

  contratoSelecionado: ContratoModel;
  dadosChecklist: DadosChecklistModel;
  checklistParaAIO: CheckModel[] = [];
  checklistParaContratos: CheckModel[] = [];
  requestEmissaoAIO: EmissaoAIORequestModel;

  modalRef: BsModalRef;

  editar = false;

  constructor(
    protected store: Store,
    private readonly route: ActivatedRoute,
    private readonly modalService: BsModalService,
    private readonly authService: UserAuthorizerService,
    private readonly  alertMessageService: AlertMessageService) {
      super(store);
  }

  init() {
    this.alertMessageService.dismissAll();
    if (this.route.parent.firstChild.snapshot.url[1].path === 'e') {
      this.editar = true;
    }
  }

  loadActions() {
    this.requestEmissaoAIO = new EmissaoAIORequestModel();    

    this.contratoSelecionado = this.store.selectSnapshot(ContratoState.contratoSelecionado);
    if (this.contratoSelecionado) {
      this.requestEmissaoAIO.idContrato = this.contratoSelecionado.id;

      return new ChecklistContrato(this.contratoSelecionado.id);
    }

    this.dadosChecklist = null;
    return null;
  }

  onLoad() {

    // clear arrays
    this.checklistParaAIO.length = 0;
    this.checklistParaContratos.length = 0;

    this.checklistObservable.subscribe(dados => {
      if (dados) {
        this.dadosChecklist = dados;

        dados.checklist.forEach(check => {

          if (check.itemDeVerificacaoPara === 'AIO') {
            if (!this.checklistParaAIO.find(e => e.indice === check.indice)) {
              this.checklistParaAIO.push(check);
            }

          } else if (check.itemDeVerificacaoPara === 'CONTRATO') {
            if (!this.checklistParaContratos.find(e => e.indice === check.indice)) {
              this.checklistParaContratos.push(check);
            }
          }

          if (check.mensagemErro != null && check.mensagemErro !== '') {
            this.alertMessageService.error(check.mensagemErro);
          }
        });
      }
    });
  }

  get contratoConcluido() {
    if (this.contratoSelecionado) {
      return this.contratoSelecionado.inSituacao !== 'RAS';
    }
    return false;
  }  

  get emitidaAIO() {
    return this.dadosChecklist
            && (this.dadosChecklist.situacaoAIO !== SituacaoAIO.NAO_EMITIDA);
  }

  get exibeBotaoConcluir(): boolean {
    return this.editar
          && this.authService.podeEditarContrato
          && this.authService.podeConcluirContrato
          && !this.contratoConcluido;
  }

  get exibeBotaoCancelarAIO(): boolean {
    return this.emitidaAIO 
          && this.authService.podeCancelarAIO;
  }

  get exibeBotaoEmitirAIO() {
    return this.contratoConcluido
            && this.dadosChecklist
            && (this.dadosChecklist.situacaoAIO === SituacaoAIO.NAO_EMITIDA)            
            && this.authService.podeEmitirAIO;
  }

  get emissaoAIOinativadaPeriodoEleitoral(): boolean {    
    return AppConfig.inativarEmissaoAIOperiodoEleitoral;
  }

  get desabilitarBotaoEmissaoAIO(): any {
    if (this.emissaoAIOinativadaPeriodoEleitoral) {
      if (this.authService.podeEmitirAIOdurantePeriodoEleitoral) {
        // Nao desabilitar
        return null;
      
      } else {
        // Desabilitar a emissao de AIO
        return true;
      }

    } else {
      // Nao desabilitar
      // Nao vamos retornar "false" pois o atributo "disabled=false" tambem ira desabilitar o botao
      return null;
    }
  }

  get posFixoDescricaoEmissaoAIO(): string {
    let posFixo = "";

    if (this.desabilitarBotaoEmissaoAIO) {
      posFixo = " (Desabilitada: Período Eleitoral)";
    }

    return posFixo;
  }

  get exibirFormularioEmissaoAIO(): boolean {
    // 605665: SICONV-InstrumentosContratuais-ECU-ListarChecklist - [A3.1.3]    
    return this.exibeBotaoEmitirAIO && this.authService.precisaPreencherFormularioEmissaoAIO;
  }

  concluir(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  confirmaConclusao() {
    this.dispatch(new ConcluirContrato(this.contratoSelecionado))
      .subscribe( () => {
        let mensagens: MensagemModel[];
        mensagens = this.store.selectSnapshot(ContratoState.mensagensContrato);
        this.exibirMensagens(mensagens);

        this.dispatch(new LoadContratoSelecionado(this.contratoSelecionado.id)).subscribe( () => {
          this.contratoSelecionado = this.store.selectSnapshot(ContratoState.contratoSelecionado);
          this.dispatch(new ChecklistContrato(this.contratoSelecionado.id)).subscribe(() => this.onLoad());
        });
      });

    this.safeCloseModal();
  }

  verificarEmissaoAIO() {
    this.dispatch(new VerificarEmissaoAIO(this.requestEmissaoAIO))
      .subscribe( () => {
        let mensagens: MensagemModel[];
        mensagens = this.store.selectSnapshot(ContratoState.mensagensContrato);
        this.exibirMensagens(mensagens);

        this.dispatch(new ChecklistContrato(this.contratoSelecionado.id)).subscribe(() => this.onLoad());
      });

    this.safeCloseModal();
  }

  verificarCancelarEmissaoAIO(template: TemplateRef<any>) {
    this.dispatch(new VerificarCancelarEmissaoAIO (this.contratoSelecionado.propostaIdSiconv))
      .subscribe( () => {
        let mensagens: MensagemModel[];
        mensagens = this.store.selectSnapshot(ContratoState.mensagensContrato);
        if (mensagens.length === 0) {
          this.modalRef = this.modalService.show(template);
        }
        else {
          this.exibirMensagens(mensagens);
        }
          
      });

  }

  confirmaCancelarAIO() {
    this.dispatch(new CancelarEmissaoAIO (this.contratoSelecionado.propostaIdSiconv))
      .subscribe( () => {
        let mensagens: MensagemModel[];
        mensagens = this.store.selectSnapshot(ContratoState.mensagensContrato);
        this.exibirMensagens(mensagens);

        this.dispatch(new ChecklistContrato(this.contratoSelecionado.id)).subscribe(() => this.onLoad());
      });

    this.safeCloseModal();
  }

  exibirMensagens(mensagens: MensagemModel[]) {
    mensagens.forEach(msg => {
      if (msg.severidade === SeveridadeMensagem.ERROR) {
        this.alertMessageService.error(msg.texto);
      } else if (msg.severidade === SeveridadeMensagem.SUCESS) {
        this.alertMessageService.success(msg.texto);
      } else if (msg.severidade === SeveridadeMensagem.WARN) {
        this.alertMessageService.warn(msg.texto);
      } else if (msg.severidade === SeveridadeMensagem.INFO) {
        this.alertMessageService.info(msg.texto);
      }
    });
  }

  cancelaConclusao() {
    this.safeCloseModal();
  }

  cancelaCancelarAIO() {
    this.safeCloseModal();
  }

  safeCloseModal() {
    if (this.modalRef) {
      this.modalRef.hide();
    }
  }

  voltar() {
    this.store.dispatch(this.voltarListagemAction());
    this.alertMessageService.dismissAll();
  }

  voltarListagemAction() {
    const id = this.route.snapshot.paramMap.get('id');
    let caminho;
    if (id) {
      caminho = '../../../../listagem';
    } else {
      caminho = '../../../listagem';
    }
    return new Navigate([caminho], {}, { relativeTo: this.route });
  }

}
