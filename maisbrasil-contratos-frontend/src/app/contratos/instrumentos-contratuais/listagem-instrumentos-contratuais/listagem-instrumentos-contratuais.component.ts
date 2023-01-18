import { Component, TemplateRef } from '@angular/core';
import { ContratoModel, MensagemModel, PermissaoAlteracaoContrato, SeveridadeMensagem } from 'src/app/model/contrato/contrato.state.model';
import { DataExport } from 'src/app/model/data-export';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Store, Select } from '@ngxs/store';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { Observable } from 'rxjs';
import { LoadContratosPorProposta, DeleteContrato } from 'src/app/model/contrato/contrato.actions';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { ActivatedRoute } from '@angular/router';
import { UserState } from 'src/app/model/user/user.state';
import { PropostaState } from 'src/app/model/proposta/proposta.state';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';
import { Modalidade, PropostaStateModel } from 'src/app/model/proposta/proposta.state.model';

@Component({
  selector: 'app-listagem-instrumentos-contratuais',
  templateUrl: './listagem-instrumentos-contratuais.component.html',
  styleUrls: ['./listagem-instrumentos-contratuais.component.scss']
})
export class ListagemInstrumentosContratuaisComponent extends BaseComponent {

  @Select(ContratoState.contratosPorProposta) contratosObservable: Observable<ContratoModel[]>;
  @Select(PropostaState) propostaObservable: Observable<PropostaStateModel>;

  instrumentosContratuais: ContratoModel[];
  modalidade: string;
  contratoAExcluir: ContratoModel;

  export: DataExport;
  modalRef: BsModalRef;

  idProposta: number;

  tituloDaTela: string;
  labelNumeroConvenio: string;

  propostaModel: PropostaStateModel[];
  modalidadeProposta: number;
  possuiMandataria?: boolean;

  mensagensProposta: MensagemModel[];

  constructor(
    protected readonly store: Store,
    private route: ActivatedRoute,
    private readonly modalService: BsModalService,
    private readonly authService: UserAuthorizerService,
    private readonly  alertMessageService: AlertMessageService
  ) {
    super(store);
  }

  loadActions() {
    this.idProposta = this.store.selectSnapshot(UserState.getProposta);
    this.modalidade =  this.store.selectSnapshot(PropostaState.getModalidadeString);
    this.propostaModel = this.store.selectSnapshot(PropostaState);
    this.modalidadeProposta = this.store.selectSnapshot(PropostaState.getModalidade);
    this.possuiMandataria = this.store.selectSnapshot(PropostaState.getPossuiMandataria);
    this.mensagensProposta = this.store.selectSnapshot(PropostaState.getMensagens);
    return new LoadContratosPorProposta(this.idProposta);
  }

  init() {
    this.alertMessageService.dismissAll();
    this.exibirMensagens(this.mensagensProposta);
  }

  // Convênio == 1 ou 6
  public isPropostaDoTipoConvenio(): boolean {
    if ( (this.modalidadeProposta === Modalidade.CONVENIO) || 
         (this.modalidadeProposta === Modalidade.CONVENIO_CONTRATO_REPASSE) ){
      
      this.tituloDaTela = 'Dados do Convênio';
      this.labelNumeroConvenio = 'Número do Convênio';
      
      return true;
    
    } else {
      return false;
    }
  }

  // Contrato de Repasse == 2
  public isPropostaDoTipoContratoDeRepasse(): boolean {
    if (this.modalidadeProposta === Modalidade.CONTRATO_REPASSE) {
      this.tituloDaTela = 'Dados do Contrato de Repasse';
      this.labelNumeroConvenio = 'Número do Contrato de Repasse';
      return true;
    } else {
      return false;
    }
  }

  public isPropostaDoTipoTermoDeCompromissoMandataria(): boolean {
    if(this.modalidadeProposta === Modalidade.TERMO_DE_COMPROMISSO && this.possuiMandataria) {
      this.tituloDaTela = 'Dados do Termo de Compromisso';
      this.labelNumeroConvenio = 'Número do Termo de Compromisso';
      return true;
    } else {
      return false;
    }
  }

  public isPropostaDoTipoTermoDeCompromissoConcedente(): boolean {
    if(this.modalidadeProposta === Modalidade.TERMO_DE_COMPROMISSO && !this.possuiMandataria) {
      this.tituloDaTela = 'Dados do Termo de Compromisso';
      this.labelNumeroConvenio = 'Número do Termo de Compromisso';
      return true;
    } else {
      return false;
    }
  }

    // Tipo do Instrumento diferente de Convêio (1 ou 6) e Contrato de Repasse (2)
  public isPropostaDoTipoDesconhecido(): boolean {
    if (this.modalidadeProposta != Modalidade.CONVENIO &&
        this.modalidadeProposta != Modalidade.CONTRATO_REPASSE &&
        this.modalidadeProposta != Modalidade.CONVENIO_CONTRATO_REPASSE &&
        this.modalidadeProposta != Modalidade.TERMO_DE_COMPROMISSO ) {

      this.tituloDaTela = 'Dados Básicos';
      this.labelNumeroConvenio = 'Número do Instrumento na Plataforma +Brasil';
      
      return true;
    
    } else {
      return false;
    }
  }

  carregarTitulo(){
    if (!this.isPropostaDoTipoContratoDeRepasse() && 
        !this.isPropostaDoTipoConvenio() &&
        !this.isPropostaDoTipoTermoDeCompromissoConcedente() &&
        !this.isPropostaDoTipoTermoDeCompromissoMandataria() &&
        !this.isPropostaDoTipoDesconhecido()) {
        this.tituloDaTela = "Dados Básicos";
      }
  }

  // Conforme RN: isExibeModalidade
  // Campo Mandatária só será considerado quando a modalidade da proposta for "Contrato de Repasse" ou "Termo de Compromisso com Mandatária"
  public isExibeModalidade(): boolean {
    return this.isPropostaDoTipoContratoDeRepasse() || this.isPropostaDoTipoTermoDeCompromissoMandataria();
  }


  onLoad() {
    this.contratosObservable.subscribe(contratos => {
      this.instrumentosContratuais = contratos;
      this.carregarTitulo();
      this.getExport(this.instrumentosContratuais);
    });
  }

  isProponente() {
    return this.authService.isProponente;
  }

  permissaoIndisponivel(permissao: PermissaoAlteracaoContrato) {
    return this.authService.isProponente &&
      permissao === PermissaoAlteracaoContrato.INDISPONIVEL;
  }

  podeEditar(permissao: PermissaoAlteracaoContrato) {
    return this.authService.isProponente &&
      (permissao === PermissaoAlteracaoContrato.TOTAL ||
        permissao === PermissaoAlteracaoContrato.PARCIAL);
  }

  permissaoTotal(permissao: PermissaoAlteracaoContrato) {
    return this.authService.isProponente &&
      permissao === PermissaoAlteracaoContrato.TOTAL;
  }

  deleteContrato(contrato: ContratoModel, template: TemplateRef<any>) {
    this.contratoAExcluir = contrato;
    this.modalRef = this.modalService.show(template);
  }

  confirmaExclusao() {
    this.dispatch(new DeleteContrato(this.contratoAExcluir.id))
      .subscribe( () => {
        this.alertMessageService.success('Exclusão do Instrumento Contratual realizada com sucesso.');
        this.dispatch(
          [
            new LoadContratosPorProposta(this.idProposta)
          ]
        );
      });

    this.modalRef.hide();
    this.contratoAExcluir = null;
  }

  cancelaExclusao() {
    this.modalRef.hide();
    this.contratoAExcluir = null;
  }

  getListaPaginada(listap) {
    this.instrumentosContratuais = listap;
  }

  getExport(conts: ContratoModel[]): ContratoModel[] {
    const data = [];
    const columns = [
      'Nº do Instrumento Contratual', 'Situação', 'Valor Total',
      `Valor Referente ao ${this.modalidade}`,
      'Data de Início de Vigência', 'Data de Fim de Vigência'
    ];

    if (conts) {
      conts.forEach(cont => {
        const linha = [];
        linha.push(cont.numero);
        linha.push(cont.inSituacao);
        linha.push(cont.valorTotal);
        linha.push('?');
        linha.push(cont.inicioVigencia);
        linha.push(cont.fimVigencia);
        data.push(linha);
      });
    }

    this.export = new DataExport(columns, data);

    return conts;
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

}
