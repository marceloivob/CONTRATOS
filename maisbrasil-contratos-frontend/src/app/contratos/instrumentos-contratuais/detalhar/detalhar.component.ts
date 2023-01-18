import { Component } from '@angular/core';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Store } from '@ngxs/store';
import { ActivatedRoute } from '@angular/router';
import { ShowChecklistTabService } from 'src/app/maisbrasil/services/checklist.service';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { filter } from 'rxjs/operators';
import { ShowTermoAditivoTabService } from 'src/app/maisbrasil/services/termoaditivo.service';

@Component({
  selector: 'app-detalhar',
  templateUrl: './detalhar.component.html',
  styleUrls: ['./detalhar.component.scss']
})
export class DetalharComponent extends BaseComponent {

  url = '';
  urlChecklist = '';
  showChecklist = false;
  urlTermosAditivos = '';
  showTermosAditivos = false;

  constructor(store: Store,
              private readonly route: ActivatedRoute,
              private showChecklistTab: ShowChecklistTabService,
              private showTermoAditivoTab: ShowTermoAditivoTabService
              ) {
    super(store);

    showChecklistTab.changeEmitted$.subscribe(show => {

      const contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);

      const id = contratoEdicao.id;
      const modo = 'e';
      this.url = `editar/${id}`;
      this.urlChecklist = `checklist/${modo}`; 
      this.showChecklist = true;     
    });

    showTermoAditivoTab.changeEmitted$.subscribe(show => {

       const contratoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);
      
      const id = contratoEdicao.id;
      const modo = 'e';
      this.url = `editar/${id}`;
      this.urlTermosAditivos = `termos-aditivos/${modo}`; 
      this.showTermosAditivos = this.showTermoAditivoTab.exibeTermoAditivo(contratoEdicao);
    });

  }

  init() {

    this.showChecklist = false;
    var possuiIdContrato = false;
    

    if (this.route.firstChild.snapshot.url[0].path === 'editar') {
      const id = this.route.firstChild.snapshot.url[1].path;
      const modo = 'e';
      this.url = `editar/${id}`;
      this.urlChecklist = `checklist/${modo}`;
      this.urlTermosAditivos = `termos-aditivos/${modo}`; 
      possuiIdContrato = true;
      
    } else if (this.route.firstChild.snapshot.url[0].path === 'detalhar') {
      const id = this.route.firstChild.snapshot.url[1].path;
      const modo = 'd';
      this.url = `detalhar/${id}`;
      this.urlChecklist = `checklist/${modo}`;
      this.urlTermosAditivos = `termos-aditivos/${modo}`; 
      possuiIdContrato = true;
      
    } else {
      this.url = `cadastrar`;      
    }

    if (possuiIdContrato) {
      this.store.select(ContratoState)
        .pipe(        
          filter(contrato => contrato != null),        
        ).subscribe(contrato => {        
          const contratoSelecionado = contrato.contratoSelecionado;
            
          if (contratoSelecionado && contratoSelecionado.id) {
            this.showChecklist = true;
            this.showTermosAditivos = this.showTermoAditivoTab.exibeTermoAditivo(contratoSelecionado);
          }           
        });
    }
  }

  destroy() {
    // precisa destruir o serviço? showChecklistTab
    // e o observable dele?

  }

}
