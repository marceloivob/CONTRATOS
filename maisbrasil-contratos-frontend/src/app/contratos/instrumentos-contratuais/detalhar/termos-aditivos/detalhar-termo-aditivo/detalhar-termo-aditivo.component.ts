import { Component, OnInit } from '@angular/core';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { Store } from '@ngxs/store';
import { ActivatedRoute } from '@angular/router';
import { ContratoState } from 'src/app/model/contrato/contrato.state';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-detalhar-termo-aditivo',
  templateUrl: './detalhar-termo-aditivo.component.html',
  styleUrls: ['./detalhar-termo-aditivo.component.css']
})
export class DetalharTermoAditivoComponent extends BaseComponent {

  url = '';
  urlChecklist = '';
  showChecklist = false;
  urlTermosAditivos = '';

  constructor(store: Store,
              private readonly route: ActivatedRoute) {
    super(store);

    
    //const termoAditivoEdicao = this.store.selectSnapshot(ContratoState.contratoSelecionado);
//    const id = termoAditivoEdicao.id;
    const modo = 'e';
    //this.url = `editar/${id}`;
  }

  init() {

    var possuiIdTermoAditivo = false;

    if (this.route.firstChild.snapshot.url[0].path === 'editar') {
      const id = this.route.firstChild.snapshot.url[1].path;
      const modo = 'e';
      this.url = `editar/${id}`;
      possuiIdTermoAditivo = true;
      
    } else if (this.route.firstChild.snapshot.url[0].path === 'detalhar') {
      const id = this.route.firstChild.snapshot.url[1].path;
      const modo = 'd';
      this.url = `detalhar/${id}`;
      possuiIdTermoAditivo = true;
      
    } else {
      this.url = `cadastrar`;      
    }

    /* // pegar termo aditivo selecionado
    if (possuiIdTermoAditivo) {
      this.store.select(ContratoState)
        .pipe(        
          filter(termoAditivo => termoAditivo != null),        
        ).subscribe(termoAditivo => {        
          const termoAditivoSelecionado = 
            
        });
    }*/
  }

  destroy() {
    // precisa destruir o serviço? showChecklistTab
    // e o observable dele?

  }


}
