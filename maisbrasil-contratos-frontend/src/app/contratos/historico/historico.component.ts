import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { LoadHistoricoContratosDaProposta, LoadHistoricoContratosExcluidosDaProposta } from 'src/app/model/proposta/proposta.actions';
import { PropostaState } from 'src/app/model/proposta/proposta.state';
import { HistoricoContratoModel } from 'src/app/model/proposta/proposta.state.model';
import { UserState } from 'src/app/model/user/user.state';
import { DataExport } from 'src/app/model/data-export';


@Component({
  selector: 'app-historico',
  templateUrl: './historico.component.html',
  styleUrls: ['./historico.component.css']
})
export class HistoricoComponent extends BaseComponent {
    
  @Select(PropostaState.getHistoricoContratos) historicoContratosObservable: Observable<HistoricoContratoModel[]>;
  @Select(PropostaState.getHistoricoContratosExcluidos) historicoContratosExcluidosObservable: Observable<HistoricoContratoModel[]>;
  
  historicoContratos: HistoricoContratoModel[];
  historicoContratosExcluidos: HistoricoContratoModel[];

  idProposta: number;

  exports: Map <Number, DataExport> = new Map<Number, DataExport>();
  // Grids
  listas: Map <Number, any[]> = new Map<Number, any[]>();
  
  constructor(
    protected readonly store: Store
  ) {
    super(store);
   }
  
  init() {
    
  }

  loadActions() {
    this.idProposta = this.store.selectSnapshot(UserState.getProposta);

    this.dispatch(new LoadHistoricoContratosDaProposta(this.idProposta));
    return new LoadHistoricoContratosExcluidosDaProposta(this.idProposta);
  }

  onLoad() {
    
    this.historicoContratosObservable.subscribe(historico =>{
      this.historicoContratos = historico;

      for (const historico of this.historicoContratos ) {
        this.adicionarExport(historico);
      }
    });

    this.historicoContratosExcluidosObservable.subscribe(historico =>{
      this.historicoContratosExcluidos = historico;

      for (const historico of this.historicoContratosExcluidos ) {
        this.adicionarExport(historico);
      }

    });
  }

  get historicoContratosCarregado() {
    return this.historicoContratos;
  }

  get historicoContratosExcluidosCarregado() {
    return this.historicoContratosExcluidos && this.historicoContratosExcluidos.length > 0;
  }

  adicionarExport(historicoContrato: HistoricoContratoModel) {
    const data = [];
    const columns = [
      'Instrumento Contratual / Termo Aditivo', 'Evento', 'Situação', 
      '	Data / Hora', 'Responsável'
    ];

    if (historicoContrato) {
      historicoContrato.listaRegistros.forEach(reg => {
        const linha = [];
        linha.push(reg.numeroContratoTA);
        linha.push(reg.evento);
        linha.push(reg.situacao);
        linha.push(reg.dataHora);
        linha.push(reg.responsavel);
        data.push(linha);
      });
    }

    this.exports.set(historicoContrato.idContrato, new DataExport(columns, data));
  }

  recuperarExport(idContrato: number): DataExport {
    return this.exports.get(idContrato);
  }

  recuperarLista(idContrato: number): any[] {
    return this.listas.get(idContrato);
  }
  
  getListaPaginada(idContrato: number, listap) {
    this.listas.set(idContrato, listap);
  }

  get historicoContratosSemRegistros() {
    return this.historicoContratos.length === 0;
  }

}
