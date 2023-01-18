import { Component  } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Select, Store } from '@ngxs/store';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { Navigate } from '@ngxs/router-plugin';
import { AnexoService } from 'src/app/model/anexo/anexo.service';
import { CurrencyHelperService } from 'src/app/maisbrasil/services/currency-helper.service';
import { DatePipe } from '@angular/common';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';
import { SituacaoTermoAditivo, TermoAditivoModel } from 'src/app/model/termo-aditivo/termo-aditivo.state.model';
import { AtualizarTermoAditivo, ConcluirNovoTermoAditivo, ConcluirTermoAditivo, LimparTermoAditivoSelecionado, LoadTermoAditivoSelecionado, SalvarTermoAditivo } from 'src/app/model/termo-aditivo/termo-aditivo.actions';
import { ContratoModel } from 'src/app/model/contrato/contrato.state.model';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { TermoAditivoState } from 'src/app/model/termo-aditivo/termo-aditivo.state';
import { LoadAnexosTermoAditivoPorConjuntoTipos } from 'src/app/model/anexo/anexo.actions';
import { AnexoModel, ContratoAnexoModel } from 'src/app/model/anexo/anexo.state.model';
import { TermoAditivoSelecionadoState } from 'src/app/model/termo-aditivo/termo-aditivo-selecionado.state';

@Component({
  selector: 'app-editar-termo-aditivo',
  templateUrl: './editar-termo-aditivo.component.html',
  styleUrls: ['./editar-termo-aditivo.component.css']
})
export class EditarTermoAditivoComponent extends BaseComponent {
  
  @Select(TermoAditivoSelecionadoState.anexosTermoAditivo) anexosObservable: Observable<AnexoModel[]>;
  
  termoAditivoEdicao: TermoAditivoModel;
  contratoEdicao: ContratoModel;
  
  novoTermoAditivo: boolean;

  termoAditivoForm: FormGroup;

  visualizar: boolean;
  submitted: boolean;

  exibirNovaVigencia: boolean;
  exibirAmpliacaoObjeto: boolean;
  justificativaObrigatoria: boolean;

  caracteresRestantesTxObjeto: number;
  caracteresRestantesTxJustificativa: number;
  MAX_TX_OBJETO = 5000;
  MAX_TX_JUSTIFICATIVA = 5000;

  mensagemSucesso: string;
  mensagemErroAoSalvar: string;

  tiposAnexo: string[] = ['TERMO_ADITIVO', 'PUBLICACAO_TERMO_ADITIVO', 'OUTROS'];
  private subscription: Subscription = new Subscription();
  contratoAnexos: ContratoAnexoModel[] = new Array();
  DEZ_MEGAS_BYTES = 10485760;
  
  constructor(
    readonly store: Store,
    private alertMessageService: AlertMessageService,
    private readonly route: ActivatedRoute,
    private fb: FormBuilder,
    public datepipe: DatePipe,
    public currencyHelper: CurrencyHelperService,
    private readonly authService: UserAuthorizerService,
    private readonly anexoService: AnexoService
   
  ) { super(store); }


  init() {
    this.alertMessageService.dismissAll();
    if (this.route.snapshot.url[0].path === 'detalhar') {
      this.visualizar = true;
    }
    this.contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);
    this.submitted = false;
    this.exibirNovaVigencia = false;
    this.exibirAmpliacaoObjeto = false;
    this.justificativaObrigatoria = false;
    this.carregarTermoAditivo();
  }
    
  carregarTermoAditivo() {
    
    const idTermoAditivo = this.route.snapshot.paramMap.get('id');
    this.novoTermoAditivo = false;
    if (idTermoAditivo) {
      this.store.dispatch(new LoadTermoAditivoSelecionado(Number(idTermoAditivo))).subscribe(
        () => {
          this.termoAditivoEdicao = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);

          this.initForm();
          this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(idTermoAditivo), this.tiposAnexo));
        }
      );
    } else {
      this.store.dispatch(new LimparTermoAditivoSelecionado());
      this.novoTermoAditivo = true;
      this.termoAditivoEdicao = ({
        numero: '',
        inAlteracaoClausula: false,
        inAlteracaoObjeto: false,
        inAlteracaoVigencia: false,
        dataAssinatura: null,
        dataPublicacao: null,
        inSituacao: SituacaoTermoAditivo.EM_RASCUNHO,
        id: null,
        contratoId: this.contratoEdicao.id,
        versao: 1

      });
      this.initForm();  
    }
    
  }

  initForm() {

    this.exibirNovaVigencia = this.termoAditivoEdicao.inAlteracaoVigencia;
    this.exibirAmpliacaoObjeto = this.termoAditivoEdicao.inAlteracaoObjeto;
    this.justificativaObrigatoria = this.termoAditivoEdicao.inAlteracaoClausula;

    this.termoAditivoForm = this.fb.group({
      inAlteracaoVigencia: [{ value: this.termoAditivoEdicao.inAlteracaoVigencia, disabled: this.visualizar }],
      inAlteracaoClausula: [{ value: this.termoAditivoEdicao.inAlteracaoClausula, disabled: this.visualizar }],
      inAlteracaoObjeto: [{ value: this.termoAditivoEdicao.inAlteracaoObjeto, disabled: this.visualizar }],
      numero: [{ value: this.termoAditivoEdicao.numero, disabled: this.visualizar }, [Validators.required, Validators.maxLength(20)]],
      objeto: [{ value: this.termoAditivoEdicao.descricaoAmpliacaoObjeto, disabled: this.visualizar },
        [ Validators.maxLength(this.MAX_TX_OBJETO)]],
      justificativa: [{ value: this.termoAditivoEdicao.justificativa, disabled: this.visualizar },
        [ Validators.maxLength(this.MAX_TX_JUSTIFICATIVA)]],
      dataAssinatura: [{ value: this.toDate(this.termoAditivoEdicao.dataAssinatura), disabled: this.visualizar }],
      dataPublicacao: [{ value: this.toDate(this.termoAditivoEdicao.dataPublicacao), disabled: this.visualizar }],
      dataFimVigencia: [{ value: this.toDate(this.termoAditivoEdicao.novaDataFimVigencia), disabled: this.visualizar }]
    });
    this.calcularCaracteresRestantesTxObjeto();
    this.calcularCaracteresRestantesTxJustificativa();
    this.setVigenciaValidators();
    this.setJustificativaValidators();
    this.setObjetoValidators();
  }

  field(fieldName: string) {
    return this.termoAditivoForm.get(fieldName);
  }
  
  showError(fieldName: string) {
    const field = this.field(fieldName);
    return !field.valid && (!field.pristine || this.submitted);
  }

  voltar() {
    this.store.dispatch(this.voltarListagemAction());
  }

  isProponente() {
    return this.authService.isProponente;
  }

  toDate(data: any): Date{
    
    let retorno: Date = null;

    if (data) {
      retorno = new Date(data);
      retorno = new Date(retorno.toISOString().slice(0, -1)); // ajustar timezone
    }

    return retorno;
  }

  salvar() {
    
    this.mensagemSucesso = 'Termo Aditivo salvo com sucesso! As informações de vigência só serão refletidas no instrumento contratual após conclusão.';
    
    this.submit();
  }

  submit() {
    this.submitted = true;

    if (this.validarAoSubmeter()){

      const termoAditivo = this.recuperarTermoAditivoPreenchido();

      if (this.novoTermoAditivo) {
        this.store.dispatch(new SalvarTermoAditivo(termoAditivo))
          .subscribe(() => {
            this.alertMessageService.success(this.mensagemSucesso);
            this.novoTermoAditivo = false;
            this.contratoAnexos = new Array();
            this.termoAditivoEdicao = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);
            this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(this.termoAditivoEdicao.id), this.tiposAnexo));
          });
      } else {
        this.store.dispatch(new AtualizarTermoAditivo(termoAditivo))
          .subscribe(() => {
            this.alertMessageService.success(this.mensagemSucesso);
            this.termoAditivoEdicao = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);
            this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(this.termoAditivoEdicao.id), this.tiposAnexo));
          });
        }
    }
  }

  validarAoSubmeter(){

    this.setAssinaturaValidators(false);
    this.setPublicacaoValidators(false);

    if (!this.termoAditivoForm.valid) {
      this.alertMessageService.error('Verifique erros nos campos!');
      return false;
    }

    if (this.isNenhumTipoSelecionado() ||
      (this.exibirNovaVigencia && 
        (this.isDataInicioMaiorQueFim() ||
        this.isNovaDataFimMenorQueDataFimContrato())
        )     ||
      (this.novoTermoAditivo && this.isSomaTamanhoAnexosMaiorQueOPermitido())
    ) {

      this.alertMessageService.error(this.mensagemErroAoSalvar);

      return false;
    }

    return true;
  }

  validarAoConcluir(){
    
    this.setAssinaturaValidators(true);
    this.setPublicacaoValidators(true);
    
    if (!this.termoAditivoForm.valid) {
      this.alertMessageService.error('Verifique erros nos campos!');
      return false;
    }

    if (this.isNenhumTipoSelecionado() ||
      this.isDataAssinaturaMaiorQueAtual() ||
      this.isDataPublicacaoMaiorQueAtual() ||
      this.isDataPublicacaoMenorQueAssinatura() ||
      (this.exibirNovaVigencia && 
        (this.isDataInicioMaiorQueFim() ||
        this.isDataFimMenorQueAssinatura() ||
        this.isDataFimMenorQuePublicacao() ||
        this.isNovaDataFimMenorQueDataFimContrato())
        )     ||
      (this.novoTermoAditivo && this.isSomaTamanhoAnexosMaiorQueOPermitido())
    ) {

      this.alertMessageService.error(this.mensagemErroAoSalvar);

      return false;
    }

    return true;
  }

  recuperarTermoAditivoPreenchido(): TermoAditivoModel{
    const formValues = this.termoAditivoForm.value;

    const termoAditivo: TermoAditivoModel = {
      id: this.termoAditivoEdicao.id,
      inAlteracaoClausula: formValues.inAlteracaoClausula,
      inAlteracaoObjeto: formValues.inAlteracaoObjeto,
      inAlteracaoVigencia: formValues.inAlteracaoVigencia,
      numero: formValues.numero,
      dataAssinatura: formValues.dataAssinatura,
      dataPublicacao: formValues.dataPublicacao,
      novaDataFimVigencia: formValues.dataFimVigencia,
      descricaoAmpliacaoObjeto: formValues.objeto,
      justificativa: formValues.justificativa,
      inSituacao: this.termoAditivoEdicao.inSituacao,
      contratoId: this.contratoEdicao.id,
      versao: this.termoAditivoEdicao.versao,
      contratoAnexos: this.contratoAnexos
    };

    if (!termoAditivo.inAlteracaoVigencia){
      termoAditivo.novaDataFimVigencia = null;
    }

    if (!termoAditivo.inAlteracaoObjeto){
      termoAditivo.descricaoAmpliacaoObjeto = null;
    }

    return termoAditivo;
  }

  concluir() {
    
    this.mensagemSucesso = 'Termo aditivo concluído com sucesso!';

    this.submitted = true;

    if (this.validarAoConcluir()) {

      const termoAditivo = this.recuperarTermoAditivoPreenchido();

      if (termoAditivo.inAlteracaoVigencia){
        this.mensagemSucesso += ' A nova data de vigência do instrumento contratual é '
          +  this.datepipe.transform(termoAditivo.novaDataFimVigencia, 'dd/MM/yyyy');
      }

      if (this.novoTermoAditivo) {
        this.store.dispatch(new ConcluirNovoTermoAditivo(termoAditivo))
          .subscribe(() => {
            this.alertMessageService.success(this.mensagemSucesso);
            this.novoTermoAditivo = false;
            this.contratoAnexos = new Array();
            this.termoAditivoEdicao = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);
            this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(this.termoAditivoEdicao.id), this.tiposAnexo));
          });
      } else {
        this.store.dispatch(new ConcluirTermoAditivo(termoAditivo))
          .subscribe(() => {
            this.alertMessageService.success(this.mensagemSucesso);
            this.termoAditivoEdicao = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);
            this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(this.termoAditivoEdicao.id), this.tiposAnexo));
          });
      }
    }
  }

  onCheckVigenciaChange(e) {
    this.exibirNovaVigencia = e.target.checked;
    this.setVigenciaValidators();
  }

  setVigenciaValidators(){
    
    let controlVigencia = this.termoAditivoForm.get('dataFimVigencia');
    
    if (this.exibirNovaVigencia) {
      controlVigencia.setValidators(Validators.required);
    } else {
      controlVigencia.clearValidators();
    }

    controlVigencia.updateValueAndValidity();
  }

  setAssinaturaValidators(required: boolean){
    
    let control = this.termoAditivoForm.get('dataAssinatura');
    
    if (required) {
      control.setValidators(Validators.required);
    } else {
      control.clearValidators();
    }

    control.updateValueAndValidity();
  }

  setPublicacaoValidators(required: boolean){
    
    let control = this.termoAditivoForm.get('dataPublicacao');
    
    if (required) {
      control.setValidators(Validators.required);
    } else {
      control.clearValidators();
    }

    control.updateValueAndValidity();
  }
  

  onCheckObjetoChange(e) {
    this.exibirAmpliacaoObjeto = e.target.checked;
    this.setObjetoValidators();
  }

  setObjetoValidators(){
    
    let controlObjeto = this.termoAditivoForm.get('objeto');
    
    if (this.exibirAmpliacaoObjeto) {
      controlObjeto.setValidators([Validators.required, Validators.maxLength(this.MAX_TX_OBJETO)]);
    } else {
      controlObjeto.setValidators([Validators.maxLength(this.MAX_TX_OBJETO)]);
    }

    controlObjeto.updateValueAndValidity();
  }

  onCheckClausulaChange(e) {
    this.justificativaObrigatoria = e.target.checked;
    this.setJustificativaValidators();
  }

  setJustificativaValidators(){
    
    let controlJustificativa = this.termoAditivoForm.get('justificativa');
    
    if (this.justificativaObrigatoria) {
      controlJustificativa.setValidators([Validators.required, Validators.maxLength(this.MAX_TX_JUSTIFICATIVA)]);
    } else {
      controlJustificativa.setValidators([Validators.maxLength(this.MAX_TX_JUSTIFICATIVA)]);
    }
    
    controlJustificativa.updateValueAndValidity();
  }

  calcularCaracteresRestantesTxObjeto() {
    const formValues = this.termoAditivoForm.value;
    this.caracteresRestantesTxObjeto = this.MAX_TX_OBJETO -
      (formValues.objeto ? formValues.objeto.length : 0);
  }

  calcularCaracteresRestantesTxJustificativa() {
    const formValues = this.termoAditivoForm.value;
    this.caracteresRestantesTxJustificativa = this.MAX_TX_JUSTIFICATIVA -
      (formValues.justificativa ? formValues.justificativa.length : 0);
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


  isNenhumTipoSelecionado(){
    const isVigencia: boolean = this.termoAditivoForm.get('inAlteracaoVigencia').value;
    const isObjeto: boolean = this.termoAditivoForm.get('inAlteracaoObjeto').value;
    const isClausula: boolean = this.termoAditivoForm.get('inAlteracaoClausula').value;

    let nenhumTipoSelecionado: boolean = !(isClausula || isObjeto || isVigencia);

    if (nenhumTipoSelecionado){
      this.mensagemErroAoSalvar = 'É preciso selecionar ao menos um tipo de Termo Aditivo.';
    }

    return nenhumTipoSelecionado;
  }

  isDataAssinaturaMaiorQueAtual() {
    let retorno: boolean;

    const dataAssinatura = this.termoAditivoForm.get('dataAssinatura').value;
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
    
    const dataPublicacao = this.termoAditivoForm.get('dataPublicacao').value;
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

    const dataAssinatura = this.termoAditivoForm.get('dataAssinatura').value;
    const dataPublicacao = this.termoAditivoForm.get('dataPublicacao').value;

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

    const dataInicioVigencia = this.toDate(this.contratoEdicao.inicioVigencia);
    const dataFimVigencia = this.termoAditivoForm.get('dataFimVigencia').value;

    retorno = !dataInicioVigencia || !dataFimVigencia || dataInicioVigencia > dataFimVigencia;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de início de vigência do instrumento (' +
        this.datepipe.transform(dataInicioVigencia, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataFimMenorQueAssinatura() {
    let retorno: boolean;

    const dataAssinatura = this.termoAditivoForm.get('dataAssinatura').value;
    const dataFimVigencia = this.termoAditivoForm.get('dataFimVigencia').value;

    retorno = !dataAssinatura || !dataFimVigencia || dataFimVigencia < dataAssinatura;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de assinatura (' +
        this.datepipe.transform(dataAssinatura, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isDataFimMenorQuePublicacao() {
    let retorno: boolean;

    const dataPublicacao = this.termoAditivoForm.get('dataPublicacao').value;
    const dataFimVigencia = this.termoAditivoForm.get('dataFimVigencia').value;

    retorno = !dataPublicacao || !dataFimVigencia || dataFimVigencia < dataPublicacao;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(dataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de publicação (' +
        this.datepipe.transform(dataPublicacao, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }

  isNovaDataFimMenorQueDataFimContrato() {
    let retorno: boolean;

    const dataFimContrato = this.toDate(this.contratoEdicao.fimVigencia);
    const novaDataFimVigencia = this.termoAditivoForm.get('dataFimVigencia').value;

    retorno = !novaDataFimVigencia || novaDataFimVigencia < dataFimContrato;

    if (retorno) {
      this.mensagemErroAoSalvar = 'A data de fim de vigência informada (' +
        this.datepipe.transform(novaDataFimVigencia, 'dd/MM/yyyy') + ') não pode ser inferior à data de fim de vigência atual do instrumento contratual (' +
        this.datepipe.transform(dataFimContrato, 'dd/MM/yyyy') + ')';
    }

    return retorno;
  }


  get exibirAnexos() {
    return (this.termoAditivoEdicao && this.termoAditivoEdicao.id);
  }

  onAnexosChanged() {
    const id = this.termoAditivoEdicao.id;
    if (id) {
      this.subscription.add(this.store.dispatch(new LoadAnexosTermoAditivoPorConjuntoTipos(Number(id), this.tiposAnexo)).subscribe());
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

  get exibirSalvarRascunho(){
    return this.termoAditivoEdicao && this.termoAditivoEdicao.inSituacao == 'RAS';
  }

  destroy() {
    this.subscription.unsubscribe();
  }


}
