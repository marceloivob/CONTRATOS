import { Component, OnInit, Input, Output, ViewChild, TemplateRef } from '@angular/core';
import { Observable } from 'rxjs';
import { AnexoModel } from 'src/app/model/anexo/anexo.state.model';
import { EventEmitter } from '@angular/core';
import { UserStateModel } from 'src/app/model/user/user.state.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { Store } from '@ngxs/store';
import { AlertMessageService } from '@serpro/ngx-siconv';
import { UserState } from 'src/app/model/user/user.state';
import { DeleteAnexo, DeleteAnexoTermoAditivo, SaveAnexo, SaveAnexoTermoAditivo } from 'src/app/model/anexo/anexo.actions';
import { ContratoModel } from 'src/app/model/contrato/contrato.state.model';
import { take, filter } from 'rxjs/operators';
import { ContratoState } from 'src/app/model/contrato/contrato.state';

import { DataExport } from 'src/app/model/data-export';
import { DatePipe } from '@angular/common';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';
import { TermoAditivoModel } from 'src/app/model/termo-aditivo/termo-aditivo.state.model';
import { TermoAditivoState } from 'src/app/model/termo-aditivo/termo-aditivo.state';

@Component({
  selector: 'app-anexo',
  templateUrl: './anexo.component.html',
  styleUrls: ['./anexo.component.scss']
})
export class AnexoComponent implements OnInit {

  @Input() tiposDoAnexo: string;
  @Input() anexos: Observable<AnexoModel[]>;
  @Input() visualizar: boolean;
  @Input() contrato: ContratoModel;
  @Input() termoAditivo: TermoAditivoModel;
  @Input() nomesTiposAnexo: string;
  @Input() isTermoAditivo: boolean;

  @Output() changed = new EventEmitter<any>();

  anexoASerExcluido: AnexoModel;
  export: DataExport;

  @ViewChild('anexoInput') anexoInput;

  idAnexo: number;
  nomeArquivo = '';
  descricao = '';
  anexo: any;
  versao = 0;
  idPai: number;
  tipoSelecionadoAnexo: string;
  submetido = false;
  erroDescricao = false;
  erroTipoSelecionadoAnexo = false;
  erroArquivoAnexo = false;

  tiposBD: string[];
  tiposAsString: string[];
  usuario: UserStateModel;

  modalRef: BsModalRef;
  showAnexoInput = true;
  showCadastro = false;
  // Grid
  lista: any[];

  constructor(
    private readonly store: Store,
    private alertMessageService: AlertMessageService,
    private readonly authService: UserAuthorizerService,
    private readonly modalService: BsModalService
  ) { }

  ngOnInit() {
    this.submetido = false;
    this.usuario = this.store.selectSnapshot(UserState);

   /* this.store.select(ContratoState)
      .pipe(
        filter(contrato => contrato != null),
        take(1)
      ).subscribe(contrato => {
        this.contrato = contrato;
        this.idPai = contrato.id;
      });*/

    this.contrato = this.store.selectSnapshot(ContratoState.contratoSelecionado);
    this.idPai = this.contrato.id;

    if (this.isTermoAditivo){
      this.termoAditivo = this.store.selectSnapshot(TermoAditivoState.termoAditivoSelecionado);
      this.idPai = this.termoAditivo.id;
    }

    this.anexos.pipe(filter(anexosP => anexosP != null), take(1)).subscribe(anexosP => {
        this.getExport(anexosP);
      });
    this.tipoSelecionadoAnexo = '';
    this.tiposBD = this.tiposDoAnexo.split(';');
    this.tiposAsString = this.nomesTiposAnexo.split(';');
  }

  changedEvent() {
    this.changed.emit('');
  }

  get podeEditar() {
    return this.authService.isProponente && !this.visualizar;
  }

  deleteAnexo(anexo: AnexoModel, template: TemplateRef<any>) {
    this.anexoASerExcluido = anexo;
    this.modalRef = this.modalService.show(template);
  }

  confirmaExclusao() {

    if (this.isTermoAditivo){
      this.store.dispatch(new DeleteAnexoTermoAditivo(this.anexoASerExcluido))
      .subscribe(() => {
        this.changedEvent();
      });

    } else {
      this.store.dispatch(new DeleteAnexo(this.anexoASerExcluido))
      .subscribe(() => {
        this.changedEvent();
      });

    }
    this.modalRef.hide();
  }

  cancelaExclusao() {
    this.modalRef.hide();
    this.submetido = false;
  }

  downloadFile(linkToDownload: string) {
    window.open(linkToDownload);
  }

  addAnexo() {
    this.anexoInput.nativeElement.click();
    this.submetido = false;
    this.erroArquivoAnexo = false;
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.anexo = file;
      this.nomeArquivo = file.name;
    }
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
    if (this.anexo && this.anexo.size > DEZ_MEGAS_BYTES) {
      this.alertMessageService.error('Tamanho máximo do arquivo: 10MB!');

      return;
    }

    if (this.isTermoAditivo){
      const anexo: AnexoModel = {
        id: this.idAnexo,
        nmArquivo: this.nomeArquivo,
        txDescricao: this.descricao,
        inTipoAnexo: this.traduzirParaformatoBD(this.tipoSelecionadoAnexo),
        arquivo: this.anexo,
        tamanhoArquivo: this.anexo ? this.anexo.size : 0,
        versao: this.versao,
        termoAditivoId: this.idPai,
        dtUpload: new Date()
      };
  
      this.store.dispatch(new SaveAnexoTermoAditivo(this.termoAditivo.id, anexo))
        .subscribe(() => {
          this.changedEvent();
          this.limparCampos();
          this.showCadastro = false;
        });

    } else {
      const anexo: AnexoModel = {
        id: this.idAnexo,
        nmArquivo: this.nomeArquivo,
        txDescricao: this.descricao,
        inTipoAnexo: this.traduzirParaformatoBD(this.tipoSelecionadoAnexo),
        arquivo: this.anexo,
        tamanhoArquivo: this.anexo ? this.anexo.size : 0,
        versao: this.versao,
        contratoId: this.idPai,
        dtUpload: new Date()
      };
  
      this.store.dispatch(new SaveAnexo(this.contrato.id, anexo))
        .subscribe(() => {
          this.changedEvent();
          this.limparCampos();
          this.showCadastro = false;
        });
    }
    this.submetido = true;
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


}

