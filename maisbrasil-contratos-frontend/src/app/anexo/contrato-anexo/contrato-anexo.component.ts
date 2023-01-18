import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { AnexoModel, ContratoAnexoModel } from 'src/app/model/anexo/anexo.state.model';
import { DataExport } from 'src/app/model/data-export';
import { UserStateModel } from 'src/app/model/user/user.state.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { Store } from '@ngxs/store';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { UserState } from 'src/app/model/user/user.state';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';

@Component({
  selector: 'app-contrato-anexo',
  templateUrl: './contrato-anexo.component.html',
  styleUrls: ['./contrato-anexo.component.scss']
})
export class ContratoAnexoComponent implements OnInit {

  @Input() tiposDoAnexo: string;
  @Input() nomesTiposAnexo: string;
  @Input() isTermoAditivo: boolean;
  @ViewChild('anexoInput') anexoInput;
  @Output() setAnexos = new EventEmitter();
  anexos: AnexoModel[] = new Array();
  contratoAnexos: ContratoAnexoModel[] = new Array();

  anexoASerExcluido: AnexoModel;
  modalRef: BsModalRef;
  usuario: UserStateModel;

  idAnexo: number;
  nomeArquivo = '';
  descricao = '';
  tamanhoArquivo: number;
  anexo: string;
  versao = 0;
  idPai: number;
  tipoSelecionadoAnexo: string;
  submetido = false;
  erroDescricao = false;
  erroTipoSelecionadoAnexo = false;
  erroArquivoAnexo = false;

  showCadastro = false;
  showAnexoInput = true;
  lista: any[];
  export: DataExport;

  tiposBD: string[];
  tiposAsString: string[];

  constructor(
    private readonly store: Store,
    private alertMessageService: AlertMessageService,
    private readonly authService: UserAuthorizerService,
    private readonly modalService: BsModalService
  ) { }

  ngOnInit() {
    this.usuario = this.store.selectSnapshot(UserState);
    this.submetido = false;
    this.tipoSelecionadoAnexo = '';
    this.tiposBD = this.tiposDoAnexo.split(';');
    this.tiposAsString = this.nomesTiposAnexo.split(';');
  }

  changedEvent() {
    this.contratoAnexos = new Array();

    this.anexos.forEach(anexo => {
      const contratoAnexo: ContratoAnexoModel = {
        arquivo: anexo.arquivo,
        nomeArquivo: anexo.nmArquivo,
        descricao: anexo.txDescricao,
        tipoAnexo: anexo.inTipoAnexo,
        tamanhoArquivo: anexo.tamanhoArquivo
      };

      this.contratoAnexos.push(contratoAnexo);
    });

    this.setAnexos.emit(this.contratoAnexos);
  }

  get podeEditar() {
    return this.authService.isProponente;
  }

  deleteAnexo(anexo: AnexoModel, template: TemplateRef<any>) {
    this.anexoASerExcluido = anexo;
    this.modalRef = this.modalService.show(template);
  }

  confirmaExclusao() {
    this.anexos.splice(this.anexoASerExcluido.id, 1);
    this.normalizaIds();

    this.changedEvent();
    this.modalRef.hide();
  }

  cancelaExclusao() {
    this.modalRef.hide();
  }

  addAnexo() {
    this.anexoInput.nativeElement.click();
  }

  onFileChange(event) {
    const reader = new FileReader();
    reader.onloadend = () => {
      if (event.target.files.length > 0) {
        const readerResult: any = reader.result;
        this.anexo = readerResult.split(',')[1];
        this.nomeArquivo = file.name;
        this.tamanhoArquivo = file.size;
      }
    };
    const [file] = event.target.files;
    reader.readAsDataURL(file);
  }

  onSubmit() {
    this.salvarAnexo();
  }

  salvarAnexo() {

    this.erroTipoSelecionadoAnexo = !this.tipoSelecionadoAnexo || this.tipoSelecionadoAnexo === '';
    this.erroArquivoAnexo = !this.nomeArquivo;
    this.erroDescricao = !this.descricao;

    if (this.erroTipoSelecionadoAnexo ) {
      this.alertMessageService.error('O tipo do anexo é obrigatorio!');
    }

    if (this.erroArquivoAnexo) {
      this.alertMessageService.error('Arquivo anexo não foi informado.');
    }

    if (this.erroDescricao) {
      this.alertMessageService.error('Descricao do anexo é obrigatória.');
    }

    if (this.erroTipoSelecionadoAnexo || this.erroArquivoAnexo || this.erroDescricao) {
      return;
    }

    if (this.nomeArquivo.length > 100) {
      this.alertMessageService.error('Tamanho do nome do arquivo anexado não pode exceder o limite de 100 caracteres.');
      return;
    }

    if (this.descricao.length > 100) {
      this.alertMessageService.error('A descrição não deve ultrapassar 100 caracteres.');
      return;
    }

    const DEZ_MEGAS_BYTES = 10485760;
    if (this.anexo && this.tamanhoArquivo > DEZ_MEGAS_BYTES) {
      this.alertMessageService.error('Tamanho máximo do arquivo: 10MB!');

      return;
    }

    if(this.idAnexo == null) {
      this.idAnexo = this.anexos.length;
    }

    let anexo: AnexoModel = {
      id: this.idAnexo,
      nmArquivo: this.nomeArquivo,
      txDescricao: this.descricao,
      inTipoAnexo: this.traduzirParaformatoBD(this.tipoSelecionadoAnexo),
      tipoDoAnexoAsString: this.tipoSelecionadoAnexo,
      nomeEnviadoPor: this.usuario.name,
      inPerfilUsuario: this.usuario.profile,
      arquivo: this.anexo,
      tamanhoArquivo: this.tamanhoArquivo,
      versao: this.versao,
      contratoId: this.idPai,
      dtUpload: new Date()
    };

    if (this.isTermoAditivo){
      anexo.contratoId = null;
      anexo.termoAditivoId = this.idPai;
    }

    if(anexo.id >= this.anexos.length) {
      this.anexos.push(anexo);
    } else {
      //caso o arquivo anexo não tenha sido mudado, pega o que já estava
      if(anexo.arquivo == null) {
        anexo.arquivo = this.anexos[ anexo.id ].arquivo;
      }
      this.anexos[ anexo.id ] = anexo;
    }
    
    this.limparCampos();
    this.showCadastro = false;
    this.submetido = true;
    this.changedEvent();
  }

  cancelar() {
    this.limparCampos();
    this.showCadastro = false;
  }

  editar(anexo: AnexoModel) {
    this.idAnexo = anexo.id;
    this.descricao = anexo.txDescricao;
    this.nomeArquivo = anexo.nmArquivo;
    this.versao = anexo.versao;
    this.showCadastro = true;
    this.tipoSelecionadoAnexo = this.traduzirTipoAnexoBD(anexo.inTipoAnexo);
  }

  limparCampos() {
    this.nomeArquivo = '';
    this.showAnexoInput = false;
    this.tipoSelecionadoAnexo = '';
    this.erroArquivoAnexo = false;
    this.erroDescricao = false;
    this.erroTipoSelecionadoAnexo = false;

    setTimeout(() => {
      this.anexo = null;
      this.nomeArquivo = '';
      this.idAnexo = null;
      this.descricao = '';
      this.showAnexoInput = true;
      this.versao = 0;
    });
    this.submetido = false;
  }

  cadastrar() {
    this.showCadastro = true;
  }

  getListaPaginada(listap) {
    this.lista = listap;
  }

  showError(fieldName: string) {
    const field = this[fieldName];
    if (field) {
      return (!field.valid && !field.pristine && !this.submetido);
    }
  }

  getExport(anexos: AnexoModel[]): AnexoModel[] {
    const data = [];
    const columns = [
      'Descrição', 'Tipo', 'Data de Envio',
      'Enviado por', 'Perfil'
    ];

    const datepipe = new DatePipe('pt-BR');

    if (anexos) {
      anexos.forEach(anexo => {
        const linha = [];

        linha.push(anexo.txDescricao);
        linha.push(anexo.inTipoAnexo);
        linha.push(datepipe.transform(anexo.dtUpload, 'dd/MM/yyyy'));
        linha.push(anexo.nomeEnviadoPor);
        linha.push(anexo.inPerfilUsuario);

        data.push(linha);
      });
    }

    this.export = new DataExport(columns, data);
    return anexos;
  }

   tipoAnexoSelecionado(tipoAnexo: string) {
      this.tipoSelecionadoAnexo = tipoAnexo;
   }

  traduzirTipoAnexoBD(tipoBD: string): string {
    const indice = this.tiposBD.indexOf(tipoBD);
    return this.tiposAsString[indice];
  }

  traduzirParaformatoBD(tipoString: string): string {
    const indice = this.tiposAsString.indexOf(tipoString);
    return this.tiposBD[indice];
  }

  focusOutFunction(fieldName: string, errorControl: string) {
    const field = this[fieldName];
    if (!this.submetido) {
      if (field === null || field === '' || field === undefined) {
        this[errorControl] = true;
      } else {
        this[errorControl] = false;
      }
    } else {
      this[errorControl] = false;
    }
  }

  private normalizaIds() {
    let cont = 0;
    this.anexos.forEach( anexo => {
      anexo.id = cont++;
    });
  }

}
