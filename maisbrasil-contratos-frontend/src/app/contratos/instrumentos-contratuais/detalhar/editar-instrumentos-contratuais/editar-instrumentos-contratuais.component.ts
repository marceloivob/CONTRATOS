import { Component, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Store, Select } from '@ngxs/store';
import {
  ContratoModel, SituacaoContrato,
  LicitacaoModel, LoteModel, PermissaoAlteracaoContrato, MensagemModel, SeveridadeMensagem
} from 'src/app/model/contrato/contrato.state.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of, Subscription } from 'rxjs';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { Navigate } from '@ngxs/router-plugin';
import { SalvarContrato, AtualizarContrato, LimparContratoSelecionado } from 'src/app/model/contrato/contrato.actions';
import { UserState } from 'src/app/model/user/user.state';
import { ContratoService } from 'src/app/model/contrato/contrato.service';
import { PropostaState } from 'src/app/model/proposta/proposta.state';
import { LoadContratoSelecionado, LoadAnexosPorConjuntoTipos } from 'src/app/model/anexo/anexo.actions';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { AnexoModel, ContratoAnexoModel } from 'src/app/model/anexo/anexo.state.model';
import { AnexoService } from 'src/app/model/anexo/anexo.service';
import { FornecedorModel } from 'src/app/model/proposta/proposta.state.model';
import { ContratoSelecionadoState } from 'src/app/model/contrato/contratoSelecionado.state';
import { CnpjPipe } from 'src/app/maisbrasil/pipes/cnpj.pipe';
import { CpfPipe } from 'src/app/maisbrasil/pipes/cpf.pipe';
import { InscricaoGenericaPipe } from 'src/app/maisbrasil/pipes/inscricaogenerica.pipe';
import { CurrencyHelperService } from 'src/app/maisbrasil/services/currency-helper.service';
import { DatePipe } from '@angular/common';
import { ShowChecklistTabService } from 'src/app/maisbrasil/services/checklist.service';
import { ShowTermoAditivoTabService } from 'src/app/maisbrasil/services/termoaditivo.service';

@Component({
  selector: 'app-editar-instrumentos-contratuais',
  templateUrl: './editar-instrumentos-contratuais.component.html',
  styleUrls: ['./editar-instrumentos-contratuais.component.scss']
})
export class EditarInstrumentosContratuaisComponent extends BaseComponent {

  @Select(ContratoState.contratoSelecionado) contratoSelecionado: Observable<ContratoModel>;
  @Select(ContratoSelecionadoState.anexosContrato) anexosObservable: Observable<AnexoModel[]>;
  novoContrato: boolean;

  exibirMensagemSemLotesDisponiveis: boolean;

  exibirModalidadeLicitacao: boolean;

  contratoForm: FormGroup;
  submitted: boolean;
  contratoEdicao: ContratoModel;
  mensagemSucesso: string;
  idProposta: number;

  modalidadeInstrumento: string;

  private subscription: Subscription = new Subscription();

  caracteresRestantesTxObjeto: number;

  visualizar: boolean;

  MAX_TX_OBJETO = 5000;

  licitacoes: LicitacaoModel[];
  fornecedores: FornecedorModel[];
  fornecedorSel: FornecedorModel;
  fornecedorSelecionado: any;
  licitacaoSelecionada: LicitacaoModel;

  licitacoesDaProposta: LicitacaoModel[];
  lotesDaProposta: LoteModel[];

  fornecedoresLabel: any;
  licitacoesLabel: any;

  lotes: LotesHelper;

  mensagemErroAoSalvar: string;

  edicaoParcial = false;

  tiposAnexo: string[] = ['INSTRUMENTO_CONTRATUAL', 'PUBLICACAO_EXTRATO', 'OUTROS'];

  labelLotesAssociar = 'Lote(s) a Associar';
  labelLotesAssociados = 'Lote(s) Associado(s)';

  contratoAnexos: ContratoAnexoModel[] = new Array();

  DEZ_MEGAS_BYTES = 10485760;

  constructor(
    readonly store: Store,
    private alertMessageService: AlertMessageService,
    private readonly route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private readonly contratoService: ContratoService,
    private readonly cnpjPipe: CnpjPipe,
    private readonly cpfPipe: CpfPipe,
    public datepipe: DatePipe,
    private readonly inscricaoGenericaPipe: InscricaoGenericaPipe,
    public currencyHelper: CurrencyHelperService,
    private readonly anexoService: AnexoService,
    private checklist: ShowChecklistTabService,
    private termoAditivo: ShowTermoAditivoTabService
  ) { super(store); }


  loadActions() {
    this.idProposta = this.store.selectSnapshot(UserState.getProposta);
    this.modalidadeInstrumento = this.store.selectSnapshot(PropostaState.getModalidadeString);
    this.fornecedores = this.store.selectSnapshot(PropostaState.getFornecedores);
    this.atualizarFornecedoresLabel();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.store.dispatch(new LoadAnexosPorConjuntoTipos(Number(id), this.tiposAnexo));
    }

    this.lotes = new LotesHelper([]);
  }

  onLoad() {
  }

  init(): void {
    this.alertMessageService.dismissAll();
    this.mensagemErroAoSalvar = '';
    this.submitted = false;
    this.exibirMensagemSemLotesDisponiveis = false;
    if (this.route.snapshot.url[0].path === 'detalhar') {
      this.visualizar = true;
    }

    this.contratoService.loadLicitacoesDaProposta().subscribe(
      licitacoes => {
        this.licitacoesDaProposta = licitacoes;
        this.contratoService.loadLotesDaProposta().subscribe(
          lotes => {
            this.lotesDaProposta = lotes;
            this.carregarContrato();
          }
        );
      }
    );
  }

  carregarContrato() {
    const idContrato = this.route.snapshot.paramMap.get('id');
    this.novoContrato = false;
    if (idContrato) {
      this.store.dispatch(new LoadContratoSelecionado(Number(idContrato))).subscribe(
        () => {
          this.contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);

          if (!this.visualizar && this.contratoEdicao.permissaoAlteracao === PermissaoAlteracaoContrato.INDISPONIVEL) {
            this.visualizar = true;
            this.alertMessageService.warn('Serviço Indisponível. Erro ao tentar consultar dados do módulo de Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil. Favor tentar novamente mais tarde.');
          }
          
          if (!this.visualizar && this.contratoEdicao.permissaoAlteracao === PermissaoAlteracaoContrato.NAO_PERMITIDA) {
            this.visualizar = true;
            this.alertMessageService.warn('Não é possível alterar os dados deste instrumento contratual, '+
                  'pois já existem informações referentes a este instrumento contratual inseridas no módulo'+
                  ' de Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil.');
          }

          this.edicaoParcial = this.contratoEdicao.permissaoAlteracao === PermissaoAlteracaoContrato.PARCIAL;
          this.initFornecedorSelecionado();
          this.store.dispatch(new LoadAnexosPorConjuntoTipos(Number(idContrato), this.tiposAnexo));
        }
      );
    } else {
      this.store.dispatch(new LimparContratoSelecionado());
      this.novoContrato = true;
      this.contratoEdicao = ({
        numero: '',
        objeto: '',
        dataAssinatura: null,
        dataPublicacao: null,
        inicioVigencia: null,
        fimVigencia: null,
        fimVigenciaOriginal: null,
        valorTotal: null,
        valorReferenteModalidade: null,
        inSituacao: SituacaoContrato.EM_RASCUNHO,
        inSituacaoDescricao: 'Em Rascunho',
        id: null,
        propostaId: null,
        propostaIdSiconv: this.idProposta,
        permissaoAlteracao: PermissaoAlteracaoContrato.TOTAL,
        versao: 1

      });
      this.initFornecedorSelecionado();
    }
  }

  initFornecedorSelecionado() {
    this.initForm();

    if (this.contratoEdicao.lotes && this.contratoEdicao.lotes.length > 0) {

      this.fornecedorSelecionado = this.fornecedorLabel(
        this.fornecedores.find(f => f.id === this.contratoEdicao.lotes[0].fornecedorId));

      this.fornecedorSel = 
          this.fornecedores.find(f => f.id === this.contratoEdicao.lotes[0].fornecedorId);
  


      this.carregarLicitacoes(this.fornecedorSelecionado);
      this.initLicitacaoSelecionada();

    } else if (this.fornecedores.length === 1) {
      this.fornecedorSelecionado = this.fornecedorLabel(this.fornecedores[0]);

      this.fornecedorSel = this.fornecedores[0];

      this.fornecedorSelecionadoChanged(this.fornecedorSelecionado);
    }

  }

  initLicitacaoSelecionada() {
    this.licitacaoSelecionada = this.licitacaoLabel(
      this.licitacoes.find(l => l.id === this.contratoEdicao.lotes[0].licitacaoId));
    this.carregarLotes(this.licitacaoSelecionada);
  }



  initForm() {

    const valorTotal = this.contratoEdicao.valorTotal? this.converterParaFormatadorSemArredondamento(this.contratoEdicao.valorTotal) : 0;

    this.contratoForm = this.fb.group({
      numero: [{ value: this.contratoEdicao.numero, disabled: this.visualizar }, [Validators.required, Validators.maxLength(20)]],
      objeto: [{ value: this.contratoEdicao.objeto, disabled: this.visualizar },
      [Validators.required, Validators.maxLength(this.MAX_TX_OBJETO)]],
      dataAssinatura: [{ value: this.contratoEdicao.dataAssinatura, disabled: this.visualizar },
      [Validators.required]],
      dataPublicacao: [{ value: this.contratoEdicao.dataPublicacao, disabled: this.visualizar },
      [Validators.required]],
      dataInicioVigencia: [{ value: this.contratoEdicao.inicioVigencia, disabled: this.visualizar },
      [Validators.required]],
      dataFimVigencia: [{ value: this.contratoEdicao.fimVigencia, disabled: this.visualizar },
      [Validators.required]],
      valorTotal: [{ value: valorTotal, disabled: this.visualizar }, [Validators.required]]
    });
    this.calcularCaracteresRestantesTxObjeto();

  }


  converterParaFormatadorSemArredondamento(valor:number): number{
    
    var numerText = valor + '';
    var indicePonto = numerText.indexOf( "." );
    if (indicePonto == -1) {
      numerText = valor + '00';
    } else {
      var aposVirgula = numerText.substring(indicePonto + 1);
      var antesVirgula = numerText.substring(0,indicePonto);
      if (aposVirgula.length == 0) {
        antesVirgula = antesVirgula + '00'
      } else if (aposVirgula.length == 1) {
        antesVirgula = antesVirgula + aposVirgula + '0'
      } else if (aposVirgula.length == 2){
        antesVirgula = antesVirgula + aposVirgula;
      } else if (aposVirgula.length > 2){
        var digito = aposVirgula.substring(2,3);
        if (parseInt(digito) >= 5) {
          var arredondamento = parseInt(antesVirgula + aposVirgula.substring(0,2)) + 1;
          antesVirgula = arredondamento + '';
        } else {
          antesVirgula = antesVirgula + aposVirgula.substring(0,2);
        }
      }
      numerText = antesVirgula;
    }
    return parseInt(numerText);
  }

  field(fieldName: string) {
    return this.contratoForm.get(fieldName);
  }

  showError(fieldName: string) {
    const field = this.field(fieldName);
    return !field.valid && (!field.pristine || this.submitted);
  }

  recuperarAnexos(id: number): Observable<AnexoModel[]> {
    return this.anexoService.recuperarAnexosPorTipo(id, 'INSTRUMENTO_CONTRATUAL');
  }

  calcularCaracteresRestantesTxObjeto() {
    const formValues = this.contratoForm.value;
    this.caracteresRestantesTxObjeto = this.MAX_TX_OBJETO -
      (formValues.objeto ? formValues.objeto.length : 0);
  }


  get erroFornecedor() {
    return !this.fornecedorSelecionado && this.submitted;
  }

  get corDaBordaComboFornecedor() {
    return this.erroFornecedor ? '1px solid #dc3545' : '1px solid #ccc';
  }

  get erroLicitacao() {
    return !this.licitacaoSelecionada && this.submitted;
  }

  get corDaBordaComboLicitacao() {
    return this.erroLicitacao ? '1px solid #dc3545' : '1px solid #ccc';
  }

  get exibirMensagemErroSemLotes() {
    return (this.lotes === null ||
      this.lotes.selecionados === null ||
      this.lotes.selecionados.length === 0)
      && this.submitted;
  }

  submit() {
    this.submitted = true;

    if (!this.contratoForm.valid || this.exibirMensagemErroSemLotes) {
      this.alertMessageService.error('Verifique erros nos campos!');
      return;
    }

    if (this.isDataAssinaturaMaiorQueAtual() ||
      this.isDataPublicacaoMaiorQueAtual() ||
      this.isDataPublicacaoMenorQueAssinatura() ||
      this.isDataInicioVigenciaMenorQueAssinatura() ||
      this.isDataInicioMaiorQueFim() ||
      this.isDataFimMenorQueAssinatura() ||
      this.isDataFimMenorQuePublicacao() ||
      (this.novoContrato && this.isSomaTamanhoAnexosMaiorQueOPermitido())
    ) {

      this.alertMessageService.error(this.mensagemErroAoSalvar);

      return;
    }

    const formValues = this.contratoForm.value;

    const valorTotalRaw = this.currencyHelper.rawValue(this.contratoForm.get('valorTotal').value);

    const contrato: ContratoModel = {
      id: this.contratoEdicao.id,
      numero: formValues.numero,
      objeto: formValues.objeto,
      dataAssinatura: formValues.dataAssinatura,
      dataPublicacao: formValues.dataPublicacao,
      inicioVigencia: formValues.dataInicioVigencia,
      fimVigencia: formValues.dataFimVigencia,
      fimVigenciaOriginal: formValues.dataFimVigencia,
      valorTotal: valorTotalRaw,
      valorReferenteModalidade: this.lotes.somaValoresSubmetasAssociadasLotes,
      inSituacao: this.contratoEdicao.inSituacao,
      inSituacaoDescricao: '',
      propostaId: this.contratoEdicao.propostaId,
      propostaIdSiconv: this.idProposta,
      versao: this.contratoEdicao.versao,
      permissaoAlteracao: this.contratoEdicao.permissaoAlteracao,
      lotes: this.lotes.selecionados,
      contratoAnexos: this.contratoAnexos
    };

    if (this.novoContrato) {
      this.store.dispatch(new SalvarContrato(contrato))
        .subscribe(() => {
          this.alertMessageService.success(this.mensagemSucesso);
          this.novoContrato = false;
          this.contratoAnexos = new Array();
          this.contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);
          this.exibirMensagens(this.contratoEdicao.mensagens);
          this.store.dispatch(new LoadAnexosPorConjuntoTipos(Number(this.contratoEdicao.id), this.tiposAnexo));
          this.checklist.exibeChecklist(true);
          this.termoAditivo.exibeEmitTermoAditivo(contrato);
        });
    } else {
      this.store.dispatch(new AtualizarContrato(contrato))
        .subscribe(() => {
          this.alertMessageService.success(this.mensagemSucesso);
          this.contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);
          this.exibirMensagens(this.contratoEdicao.mensagens);
          this.store.dispatch(new LoadAnexosPorConjuntoTipos(Number(contrato.id), this.tiposAnexo));
          this.checklist.exibeChecklist(true);
          this.termoAditivo.exibeEmitTermoAditivo(contrato);
        });
    }

  }


  voltar() {
    this.store.dispatch(this.voltarListagemAction());
  }

  voltarListagemAction() {
    const id = this.route.snapshot.paramMap.get('id');
    let caminho;
    if (id) {
      caminho = '../../../listagem';
    } else {
      caminho = '../../listagem';
    }
    return new Navigate([caminho], {}, { relativeTo: this.route });
  }

  salvar() {
    if (this.novoContrato) {
      this.mensagemSucesso = 'Inclusão do Instrumento Contratual realizada com sucesso.';
    } else {
      this.mensagemSucesso = 'Alteração do Instrumento Contratual realizada com sucesso.';
    }

    this.submit();
  }

  onAnexosChanged() {
    const id = this.contratoEdicao.id;
    if (id) {
      this.subscription.add(this.store.dispatch(new LoadAnexosPorConjuntoTipos(Number(id), this.tiposAnexo)).subscribe());
    }
  }

  fornecedorLabel(fornecedor: FornecedorModel) {
    if (fornecedor == null) {
      return null;
    }
    
    let tipoDaIdentificacao = fornecedor.tipoIdentificacao;

    let identificacao = fornecedor.identificacao;

    if (tipoDaIdentificacao === 'CNPJ') {
      identificacao = this.cnpjPipe.transform(fornecedor.identificacao);
    } else if (tipoDaIdentificacao === 'IG') {
      identificacao = this.inscricaoGenericaPipe.transform(fornecedor.identificacao);
    } else {
      tipoDaIdentificacao = 'CPF';
      identificacao = this.cpfPipe.transform(fornecedor.identificacao);
    }

    const fornecedorLabel = {
      ...fornecedor,
      label: `${fornecedor.tipoIdentificacao}: ${identificacao} - ${fornecedor.razaoSocial}`
    };

    return fornecedorLabel;
  }

  atualizarFornecedoresLabel() {
    if (this.fornecedores) {
      this.fornecedoresLabel = this.fornecedores.map(fornecedor => {
        return this.fornecedorLabel(fornecedor);
      });
    }
  }

  get exibirValorTotalSubmetas() {
    return (this.lotes && this.lotes.somaValoresSubmetasAssociadasLotes > 0);
  }

  get visualizarCombo() {
    return this.visualizar ||
      (this.lotes && this.lotes.selecionados && this.lotes.selecionados.length > 0) ||
      this.isEdicaoParcial;
  }

  get fornecedorSelecionadoLabel() {
    let fornecedorLabel = this.fornecedorLabel(this.fornecedorSelecionado);
    
    return fornecedorLabel ? fornecedorLabel.label : "";
  }

  get licitacaoSelecionadaLabel() {
    let licitacaoLabel = this.licitacaoLabel(this.licitacaoSelecionada);
    return licitacaoLabel ? licitacaoLabel.label : "";
  }


  fornecedorSelecionadoChanged(fornecedor: FornecedorModel) {

    this.limparLicitacoes();

    if (this.fornecedorSelecionado) {
      this.carregarLicitacoes(fornecedor);

      if (this.licitacoes && this.licitacoes.length === 1) {
        this.licitacaoSelecionada = this.licitacaoLabel(this.licitacoes[0]);
        this.licitacaoSelecionadaChanged(this.licitacaoSelecionada);
      }
    }
  }

  carregarLicitacoes(fornecedor: FornecedorModel) {
    if (fornecedor) {
      this.licitacoes = this.licitacoesDaProposta.filter(
        lic => this.lotesDaProposta.some(
          lote => (lote.fornecedorId === fornecedor.id && lote.licitacaoId === lic.id)
        )
      );
      this.atualizarLicitacoesLabel();
    }
  }

  limparLicitacoes() {
    this.licitacaoSelecionada = null;
    this.licitacoes = null;
    this.licitacoesLabel = null;
    this.limparLotes();
  }

  limparLotes() {
    this.contratoEdicao = ({
      ...this.contratoEdicao,
      lotes: []
    });

    this.exibirMensagemSemLotesDisponiveis = false;

    this.lotes = new LotesHelper([]);
  }

  licitacaoLabel(licitacao: LicitacaoModel) {
    if (licitacao == null) {
      return null;
    }

    const licitacaoLabel = {
      ...licitacao,
      label: `${licitacao.numeroAno}`
    };

    return licitacaoLabel;
  }

  atualizarLicitacoesLabel() {
    if (this.licitacoes) {
      this.licitacoesLabel = this.licitacoes.map(licitacao => {
        return this.licitacaoLabel(licitacao);
      });
    }
  }

  licitacaoSelecionadaChanged(licitacao: LicitacaoModel) {

    this.limparLotes();
    this.carregarLotes(licitacao);

  }

  carregarLotes(licitacao: LicitacaoModel) {
    if (licitacao && this.fornecedorSelecionado) {
      this.exibirModalidadeLicitacao = (licitacao.processoDeExecucao === 'Licitação');

      this.initLotes(this.lotesDaProposta.filter(
        lote => (lote.fornecedorId === this.fornecedorSelecionado.id && lote.licitacaoId === licitacao.id)));
    }
  }

  initLotes(lotesDoParFornecedorLicitacao: LoteModel[]) {
    let lotesDisponiveis: LoteModel[] = [];

    if (!this.novoContrato && this.contratoEdicao) {
      lotesDisponiveis = lotesDoParFornecedorLicitacao.filter(
        lote => lote.contratoId === null || lote.contratoId === this.contratoEdicao.id);
    } else {
      lotesDisponiveis = lotesDoParFornecedorLicitacao.filter(lote => lote.contratoId === null);
    }

    this.exibirMensagemSemLotesDisponiveis = (lotesDisponiveis.length === 0) && (this.licitacaoSelecionada != null);

    this.lotes = new LotesHelper(lotesDisponiveis);

    if (!this.novoContrato && this.contratoEdicao) {
      this.lotes.updateControleLotes(this.contratoEdicao.lotes);
    }
  }

  destroy() {
    this.subscription.unsubscribe();
  }

  get exibirAnexos() {
    return (this.contratoEdicao && this.contratoEdicao.id);
  }

  get isEdicaoParcial() {
    return this.edicaoParcial;
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  isDataAssinaturaMaiorQueAtual() {
    let retorno: boolean;
    const dataAssinatura = this.contratoForm.get('dataAssinatura').value;

    const hoje: Date = new Date();
    hoje.setHours(0, 0, 0, 0); // Remove a hora e o TimeZone

    retorno = !dataAssinatura || dataAssinatura > hoje;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de assinatura informada (' +
        this.datepipe.transform(dataAssinatura, 'dd/MM/yyyy') + ') não pode ser superior à data atual (' +
        this.datepipe.transform(hoje, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataPublicacaoMaiorQueAtual() {
    let retorno: boolean;
    const dataPublicacao = this.contratoForm.get('dataPublicacao').value;

    const hoje: Date = new Date();
    hoje.setHours(0, 0, 0, 0); // Remove a hora e o TimeZone

    retorno = !dataPublicacao || dataPublicacao > hoje;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de publicação informada (' +
        this.datepipe.transform(dataPublicacao, 'dd/MM/yyyy') + ') não pode ser superior à data atual (' +
        this.datepipe.transform(hoje, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataPublicacaoMenorQueAssinatura() {
    let retorno: boolean;
    const dataAssinatura = this.contratoForm.get('dataAssinatura').value;
    const dataPublicacao = this.contratoForm.get('dataPublicacao').value;

    retorno = !dataAssinatura || !dataPublicacao || dataPublicacao < dataAssinatura;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de publicação informada (' +
        this.datepipe.transform(dataPublicacao, 'dd/MM/yyyy') + ') não pode ser menor que a data de assinatura (' +
        this.datepipe.transform(dataAssinatura, 'dd/MM/yyyy') + ')';
    }
    return retorno;
  }

  isDataInicioMaiorQueFim() {
    let retorno: boolean;
    const dataInicioVigencia = this.contratoForm.get('dataInicioVigencia').value;
    const dataFimVigencia = this.contratoForm.get('dataFimVigencia').value;

    retorno = !dataInicioVigencia || !dataFimVigencia || dataInicioVigencia > dataFimVigencia;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de início de vigência informada (' +
        this.datepipe.transform(dataInicioVigencia, 'dd/MM/yyyy') + ') não pode ser superior à data de fim de vigência (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataFimMenorQueAssinatura() {

    let retorno: boolean;
    const dataAssinatura = this.contratoForm.get('dataAssinatura').value;
    const dataFimVigencia = this.contratoForm.get('dataFimVigencia').value;

    retorno = !dataAssinatura || !dataFimVigencia || dataFimVigencia < dataAssinatura;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de assinatura (' +
        this.datepipe.transform(dataAssinatura, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataInicioVigenciaMenorQueAssinatura() {
    let retorno: boolean;

    const dataInicioVigencia = this.contratoForm.get('dataInicioVigencia').value;
    const dataAssinatura = this.contratoForm.get('dataAssinatura').value;

    retorno = !dataInicioVigencia || dataInicioVigencia < dataAssinatura;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de início de vigência informada (' +
        this.datepipe.transform(dataInicioVigencia, 'dd/MM/yyyy') + ') não pode ser menor que a data de assinatura (' +
        this.datepipe.transform(dataAssinatura, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataFimMenorQuePublicacao() {

    let retorno: boolean;

    const dataPublicacao = this.contratoForm.get('dataPublicacao').value;
    const dataFimVigencia = this.contratoForm.get('dataFimVigencia').value;

    retorno = !dataPublicacao || !dataFimVigencia || dataFimVigencia < dataPublicacao;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de publicação (' +
        this.datepipe.transform(dataPublicacao, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  exibirMensagens(mensagens: MensagemModel[]) {
    if (mensagens && mensagens.length > 0) {
      mensagens.forEach(msg => {
        if (msg.texto && msg.texto !== '') {
          if (msg.severidade === SeveridadeMensagem.ERROR) {
            this.alertMessageService.error(msg.texto);
          } else if (msg.severidade === SeveridadeMensagem.SUCESS) {
            this.alertMessageService.success(msg.texto);
          } else if (msg.severidade === SeveridadeMensagem.WARN) {
            this.alertMessageService.warn(msg.texto);
          } else if (msg.severidade === SeveridadeMensagem.INFO) {
            this.alertMessageService.info(msg.texto);
          }
        }
      });
    }
  }

  setAnexos(event: any) {
    this.contratoAnexos = event;
  }

  isSomaTamanhoAnexosMaiorQueOPermitido() {
    let total = 0;
    let retorno: boolean;

    this.contratoAnexos.forEach(anexo => {
      total = total + anexo.tamanhoArquivo;
    });

    retorno = (total > (7) * this.DEZ_MEGAS_BYTES);

    if (retorno) {
      this.mensagemErroAoSalvar = 'A soma dos tamanhos dos anexos inseridos excede o limite permitido (70 MB)';
    }
    return retorno;
  }

  get exibirVigenciaOriginal() {
    return this.contratoEdicao && (this.contratoEdicao.fimVigencia !== this.contratoEdicao.fimVigenciaOriginal);
  }

  get vigenciaOriginal(){
    return this.datepipe.transform(this.contratoEdicao.fimVigenciaOriginal, 'dd/MM/yyyy')
  }


}

class LotesHelper {
  public target: any;
  public source: any;
  public selecionados: LoteModel[] = [];
  public lotesProposta: LoteModel[] = [];

  somaValoresSubmetasAssociadasLotes: number;

  constructor(
    private lotesPropostaOriginal: LoteModel[] = []
  ) {
    this.lotesProposta = this.cloneLotes(lotesPropostaOriginal);
    this.updateControleLotes([]);
  }

  updateLotesSelecionados(novosLotesSelecionados: LoteModel[]) {
    this.selecionados = novosLotesSelecionados.slice().sort((a, b) => a.numero - b.numero);
    this.atualizarSomaSubmetas();
  }

  atualizarSomaSubmetas() {
    this.somaValoresSubmetasAssociadasLotes = 0;
    if (this.selecionados && this.selecionados.length > 0) {
      this.somaValoresSubmetasAssociadasLotes = this.selecionados.reduce((acc, lote) =>
        acc + lote.somatorioSubmetas, 0);
    }
  }

  updateControleLotes(novosLotesSelecionados: LoteModel[]) {
    this.updateLotesSelecionados(novosLotesSelecionados);
    this.target = novosLotesSelecionados
      .slice()
      .sort((a, b) => a.numero - b.numero)
      .map(lote => {
        return { name: `Lote ${lote.numero}`, data: lote };
      });

    this.source = this.lotesProposta
      .filter(lote => !this.selecionados.some(loteSel => lote.id === loteSel.id))
      .filter(lote => lote.submetas.length > 0)
      .sort((a, b) => a.numero - b.numero)
      .map(lote => {
        return { name: `Lote ${lote.numero}`, data: lote };
      });
  }

  onLotesChanged(event: any[]) {
    this.updateLotesSelecionados(event.map(tLote => tLote.data));
  }

  private cloneLotes(lotesProposta: LoteModel[]) {
    return lotesProposta.map(lote => {
      const submetas = lote.submetas.map(submeta => {
        return { ...submeta };
      });
      return { ...lote, submetas };
    });
  }
}
