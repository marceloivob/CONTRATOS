import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { PropostaStateModel, FornecedorModel, HistoricoContratoModel } from './proposta.state.model';
import { Observable, of } from 'rxjs';
import { AppConfig } from 'src/app/core/app.config';

@Injectable({
  providedIn: 'root'
})
export class PropostaService {


  verificaInicioContratos(): Observable<boolean> {

    return this.http.get<boolean>(`${AppConfig.endpoint}/proposta/verifica-inicio-contratos`);
  }

  constructor(private readonly http: HttpClient) { }

  loadProposta(): Observable<PropostaStateModel> {
    return this.http.get<PropostaStateModel>(`${AppConfig.endpoint}/propostas/`);
  }

  loadFornecedores(): Observable<FornecedorModel[]> {
    const url = `${AppConfig.endpoint}/fornecedores`;
    return this.http.get<FornecedorModel[]>(url);
  }

  loadHistoricoContratosDaProposta(idProposta: number): Observable<HistoricoContratoModel[]> {
    return this.http.get<HistoricoContratoModel[]>(`${AppConfig.endpoint}/proposta/${idProposta}/historico-contratos`);
  }

  loadHistoricoContratosExcluidosDaProposta(idProposta: number): Observable<HistoricoContratoModel[]> {
    return this.http.get<HistoricoContratoModel[]>(`${AppConfig.endpoint}/proposta/${idProposta}/historico-contratos-excluidos`);
  }


}
