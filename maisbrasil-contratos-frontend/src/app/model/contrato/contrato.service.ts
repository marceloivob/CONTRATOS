import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContratoModel, LicitacaoModel, LoteModel, DadosChecklistModel, MensagemModel, EmissaoAIORequestModel } from './contrato.state.model';
import { AppConfig } from 'src/app/core/app.config';
import { ContratoMock } from './contrato.mock';

@Injectable({
    providedIn: 'root'
})
export class ContratoService {

    contratoMock = new ContratoMock();

    constructor(private http: HttpClient) { }

    loadContratosPorProposta(idProposta: number): Observable<ContratoModel[]> {
        return this.http.get<ContratoModel[]>(`${AppConfig.endpoint}/proposta/${idProposta}/contratos`);
    }

    deleteContrato(id: number): Observable<any> {
        const url = `${AppConfig.endpoint}/contratos/${id}`;
        return this.http.delete<any>(url);
    }

    salvarContrato(contrato: ContratoModel): Observable<ContratoModel> {
        const url = `${AppConfig.endpoint}/contratoanexos`;
        return this.http.post<ContratoModel>(url, contrato);
    }

    // salvarContrato(contrato: ContratoModel): Observable<ContratoModel> {
    //     const url = `${AppConfig.endpoint}/contratos`;
    //     return this.http.post<ContratoModel>(url, contrato);
    // }

    atualizarContrato(contrato: ContratoModel): Observable<ContratoModel> {
        const url = `${AppConfig.endpoint}/contratos`;
        return this.http.put<ContratoModel>(url, contrato);
    }

    loadContratoPorId(id: number): Observable<ContratoModel> {
        const url = `${AppConfig.endpoint}/contratos/${id}`;
        return this.http.get<ContratoModel>(url);
    }

    loadLicitacoesPorIdFornecedor(id: number): Observable<LicitacaoModel[]> {
        const url = `${AppConfig.endpoint}/licitacoes/fornecedor/${id}`;
        return this.http.get<LicitacaoModel[]>(url);
    }

    loadLotesPorIdFornecedorEIdLicitacao(idFornecedor: number, idLicitacao: number): Observable<LoteModel[]> {
        const url = `${AppConfig.endpoint}/lotes/fornecedor/${idFornecedor}/licitacao/${idLicitacao}`;
        return this.http.get<LoteModel[]>(url);
    }

    checklistContrato(idContrato: number): Observable<DadosChecklistModel> {
        const url = `${AppConfig.endpoint}/contratos/${idContrato}/checklist`;
        return this.http.get<DadosChecklistModel>(url);
    }

    concluirContrato(contrato: ContratoModel): Observable<MensagemModel[]> {
        const url = `${AppConfig.endpoint}/contratos/concluir/${contrato.versao}`;
        return this.http.put<MensagemModel[]>(url, contrato.id);
    }

    loadLicitacoesDaProposta(): Observable<LicitacaoModel[]> {
        const url = `${AppConfig.endpoint}/licitacoes`;
        return this.http.get<LicitacaoModel[]>(url);
    }

    loadLotesDaProposta(): Observable<LoteModel[]> {
        const url = `${AppConfig.endpoint}/lotes`;
        return this.http.get<LoteModel[]>(url);
    }

    verificarEmissaoAIO(request: EmissaoAIORequestModel): Observable<MensagemModel[]> {
        const url = `${AppConfig.endpoint}/contratos/emissaoaio`;
        return this.http.put<MensagemModel[]>(url, request);
    }

    verificarCancelarEmissaoAIO(idProposta: number): Observable<MensagemModel[]> {
        const url = `${AppConfig.endpoint}/proposta/${idProposta}/verifica-cancelamento-aio`;
        return this.http.get<MensagemModel[]>(url);
    }
    
    cancelarEmissaoAIO(idProposta: number): Observable<MensagemModel[]> {
        const url = `${AppConfig.endpoint}/proposta/${idProposta}/cancela-aio`;
        return this.http.put<MensagemModel[]>(url,idProposta);
    }
    
}
