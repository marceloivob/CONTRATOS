import { Routes, RouterModule } from '@angular/router';
import { DetalharTermoAditivoComponent } from './detalhar-termo-aditivo.component';
import { NgModule } from '@angular/core';
import { EditarTermoAditivoComponent } from './editar-termo-aditivo/editar-termo-aditivo.component';

const routes: Routes = [
    { path: '', redirectTo: 'cadastrar', pathMatch: 'full' },
    {
      path: '',
      component: DetalharTermoAditivoComponent,
      children: [
        { path: 'cadastrar',
          component: EditarTermoAditivoComponent, data: { breadcrumb: 'Termos Aditivos' }
        },
        { path: 'detalhar/:id',
          component: EditarTermoAditivoComponent, data: { breadcrumb: 'Termos Aditivos' }
        },
        { path: 'editar/:id',
          component: EditarTermoAditivoComponent, data: { breadcrumb: 'Termos Aditivos' }
        }
      ]
    }
  ];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DetalharTermoAditivoRoutingModule { }
