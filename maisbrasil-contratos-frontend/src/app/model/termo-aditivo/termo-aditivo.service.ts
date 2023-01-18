import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfig } from 'src/app/core/app.config';
import { TermoAditivoModel } from './termo-aditivo.state.model';

@Injectable({
    providedIn: 'root'
})
export class TermoAditivoService {

    constructor(private http: HttpClient) { }

    loadTermosAditivosPorContrato(idContrato: number): Observable<TermoAditivoModel[]> {
        return this.http.get<TermoAditivoModel[]>(`${AppConfig.endpoint}/contratos/${idContrato}/termosaditivos`);
    }

    deleteTermoAditivo(id: number): Observable<any> {
        const url = `${AppConfig.endpoint}/termosaditivos/${id}`;
        return this.http.delete<any>(url);
    }

    // salvarTermoAditivo(termoAditivo: TermoAditivoModel): Observable<TermoAditivoModel> {
    //     const url = `${AppConfig.endpoint}/termosaditivos`;
    //     return this.http.post<TermoAditivoModel>(url, termoAditivo);
    // }

    salvarTermoAditivo(termoAditivo: TermoAditivoModel): Observable<TermoAditivoModel> {
        const url = `${AppConfig.endpoint}/termosaditivosanexos`;
        return this.http.post<TermoAditivoModel>(url, termoAditivo);
    }

    atualizarTermoAditivo(termoAditivo: TermoAditivoModel): Observable<TermoAditivoModel>  {
        const url = `${AppConfig.endpoint}/termosaditivos`;
        return this.http.put<TermoAditivoModel>(url, termoAditivo);
    }

    loadTermoAditivoPorId(id: number): Observable<TermoAditivoModel> {
        const url = `${AppConfig.endpoint}/termosaditivos/${id}`;
        return this.http.get<TermoAditivoModel>(url);
    }

    concluirNovoTermoAditivo(termoAditivo: TermoAditivoModel): Observable<TermoAditivoModel> {
        const url = `${AppConfig.endpoint}/termosaditivosanexos/concluir`;
        return this.http.post<TermoAditivoModel>(url, termoAditivo);
    }

    concluirTermoAditivo(termoAditivo: TermoAditivoModel): Observable<TermoAditivoModel>  {
        const url = `${AppConfig.endpoint}/termosaditivos/concluir`;
        return this.http.put<TermoAditivoModel>(url, termoAditivo);
    }

}
