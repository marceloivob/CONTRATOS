import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnexoModel, TiposDeAnexos } from './anexo.state.model';
import { map } from 'rxjs/operators';
import { AppConfig } from 'src/app/core/app.config';


interface AnexoFromApi {

    id: number;
    contratoId?: number;
    termoAditivoId?: number;
    nmArquivo: string;
    dtUpload: Date;
    txDescricao: string;
    inTipoAnexo: string;
    tipoArquivoString: string;
    nomeEnviadoPor: string;
    inPerfilUsuario: string;
    caminho: string;
    versao: number;
    linkToDownload: string;
  }

@Injectable({
   providedIn: 'root'
})
export class AnexoService {

   constructor(private readonly http: HttpClient) { }

   recuperarDadosDaListagem(idContrato: number): Observable<AnexoModel[]> {
      return this.http
        .get<AnexoFromApi[]>(`${AppConfig.endpoint}/anexos/{idContrato}/anexos`)
        .pipe(
          map((anexosApi) => {
            const anexos: AnexoModel[] = anexosApi.map(anexoApi => {
              return {
                id: anexoApi.id,
                contratoId: anexoApi.contratoId,
                nmArquivo: anexoApi.nmArquivo,
                dtUpload: anexoApi.dtUpload,
                txDescricao: anexoApi.txDescricao,
                inTipoAnexo: anexoApi.inTipoAnexo,
                tipoDoAnexoAsString: anexoApi.tipoArquivoString,
                nomeEnviadoPor: anexoApi.nomeEnviadoPor,
                inPerfilUsuario: anexoApi.inPerfilUsuario,
                linkToDownload: anexoApi.linkToDownload,
                versao: anexoApi.versao
              };
            });
            return anexos;
          })
        );
    }

    salvarAnexo(idContrato: number, anexo: AnexoModel) {
      const formData: FormData = new FormData();
      formData.append('_charset_', 'utf-8');
      if (anexo.arquivo) {
        formData.append('arquivo', anexo.arquivo, anexo.nmArquivo);
        formData.append('tamanhoArquivo', anexo.arquivo.size);
      }

      formData.append('nomeArquivo', anexo.nmArquivo);
      formData.append('descricao', anexo.txDescricao);
      formData.append('tipoAnexo', anexo.inTipoAnexo);
      formData.append('versao', anexo.versao.toString());

      if (anexo.id) {
        const url = `${AppConfig.endpoint}/anexos/${anexo.id}`;
        return this.http.put<{ status: string }>(url, formData);
      } else {
        const url = `${AppConfig.endpoint}/anexos/${idContrato}`;
        return this.http.post<{ status: string }>(url, formData);
      }
    }

    salvarAnexoTermoAditivo(idTermoAditivo: number, anexo: AnexoModel) {
      const formData: FormData = new FormData();
      formData.append('_charset_', 'utf-8');
      if (anexo.arquivo) {
        formData.append('arquivo', anexo.arquivo, anexo.nmArquivo);
        formData.append('tamanhoArquivo', anexo.arquivo.size);
      }

      formData.append('nomeArquivo', anexo.nmArquivo);
      formData.append('descricao', anexo.txDescricao);
      formData.append('tipoAnexo', anexo.inTipoAnexo);
      formData.append('versao', anexo.versao.toString());

      if (anexo.id) {
        const url = `${AppConfig.endpoint}/anexos/termo-aditivo/${anexo.id}`;
        return this.http.put<{ status: string }>(url, formData);
      } else {
        const url = `${AppConfig.endpoint}/anexos/termo-aditivo/${idTermoAditivo}`;
        return this.http.post<{ status: string }>(url, formData);
      }
    }

    deleteAnexo(anexo: AnexoModel) {
      return this.http
        .delete<{ status: string }>(`${AppConfig.endpoint}/anexos/${anexo.id}`);
    }

    deleteAnexoTermoAditivo(anexo: AnexoModel) {
      return this.http
        .delete<{ status: string }>(`${AppConfig.endpoint}/anexos/termo-aditivo/${anexo.id}`);
    }


    recuperarDominioDoTipoDeAnexo(): Observable<TiposDeAnexos[]> {
      return this.http
        .get<TiposDeAnexos[]>(`${AppConfig.endpoint}/anexos/tipos/`);
    }

    recuperarAnexosPorTipo(idContrato: number, tipoAnexo: string): Observable<AnexoModel[]> {
      return this.http
        .get<AnexoFromApi[]>(`${AppConfig.endpoint}/anexos/${idContrato}/${tipoAnexo}`)
        .pipe(
          map((anexosApi) => {
            const anexos: AnexoModel[] = anexosApi.map(anexoApi => {
              return {
                id: anexoApi.id,
                contratoId: anexoApi.contratoId,
                nmArquivo: anexoApi.nmArquivo,
                dtUpload: anexoApi.dtUpload,
                txDescricao: anexoApi.txDescricao,
                inTipoAnexo: anexoApi.inTipoAnexo,
                tipoDoAnexoAsString: anexoApi.tipoArquivoString,
                nomeEnviadoPor: anexoApi.nomeEnviadoPor,
                inPerfilUsuario: anexoApi.inPerfilUsuario,
                linkToDownload: anexoApi.linkToDownload,
                versao: anexoApi.versao
              };
            });
            return anexos;
          })
        );
      }

    recuperarAnexosPorConjuntoTipos(idContrato: number, tiposAnexo: string[]): Observable<AnexoModel[]> {
      let params = new HttpParams();
      params = params.append('tipos', tiposAnexo.join(', '));
      return this.http
        .get<AnexoFromApi[]>(`${AppConfig.endpoint}/anexos/${idContrato}/portipos`, {params})
        .pipe(
          map((anexosApi) => {
            const anexos: AnexoModel[] = anexosApi.map(anexoApi => {
              return {
                id: anexoApi.id,
                contratoId: anexoApi.contratoId,
                nmArquivo: anexoApi.nmArquivo,
                dtUpload: anexoApi.dtUpload,
                txDescricao: anexoApi.txDescricao,
                inTipoAnexo: anexoApi.inTipoAnexo,
                tipoDoAnexoAsString: anexoApi.tipoArquivoString,
                nomeEnviadoPor: anexoApi.nomeEnviadoPor,
                inPerfilUsuario: anexoApi.inPerfilUsuario,
                linkToDownload: anexoApi.linkToDownload,
                versao: anexoApi.versao
              };
            });
            return anexos;
          })
        );
      }

      recuperarAnexosTermoAditivoPorConjuntoTipos(idTermoAditivo: number, tiposAnexo: string[]): Observable<AnexoModel[]> {
        let params = new HttpParams();
        params = params.append('tipos', tiposAnexo.join(', '));
        return this.http
          .get<AnexoFromApi[]>(`${AppConfig.endpoint}/anexos/termo-aditivo/${idTermoAditivo}/portipos`, {params})
          .pipe(
            map((anexosApi) => {
              const anexos: AnexoModel[] = anexosApi.map(anexoApi => {
                return {
                  id: anexoApi.id,
                  termoAditivoId: anexoApi.termoAditivoId,
                  nmArquivo: anexoApi.nmArquivo,
                  dtUpload: anexoApi.dtUpload,
                  txDescricao: anexoApi.txDescricao,
                  inTipoAnexo: anexoApi.inTipoAnexo,
                  tipoDoAnexoAsString: anexoApi.tipoArquivoString,
                  nomeEnviadoPor: anexoApi.nomeEnviadoPor,
                  inPerfilUsuario: anexoApi.inPerfilUsuario,
                  linkToDownload: anexoApi.linkToDownload,
                  versao: anexoApi.versao
                };
              });
              return anexos;
            })
          );
        }

  }
