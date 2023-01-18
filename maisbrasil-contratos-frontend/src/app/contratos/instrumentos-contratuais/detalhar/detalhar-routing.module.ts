import { Routes, RouterModule } from '@angular/router';
import { DetalharComponent } from './detalhar.component';
import { NgModule } from '@angular/core';
import { ChecklistComponent } from './checklist/checklist.component';
import { EditarInstrumentosContratuaisComponent } from './editar-instrumentos-contratuais/editar-instrumentos-contratuais.component';

const routes: Routes = [
    { path: '', redirectTo: 'cadastrar', pathMatch: 'full' },
    {
      path: '',
      component: DetalharComponent,
      children: [
        { path: 'cadastrar',
          component: EditarInstrumentosContratuaisComponent, data: { breadcrumb: 'Instrumentos Contratuais' }
        },
        { path: 'detalhar/:id',
          component: EditarInstrumentosContratuaisComponent, data: { breadcrumb: 'Instrumentos Contratuais' }
        },
        { path: 'editar/:id',
          component: EditarInstrumentosContratuaisComponent, data: { breadcrumb: 'Instrumentos Contratuais' }
        },
        {
          path: 'checklist/:modo',
          component: ChecklistComponent, data: { breadcrumb: 'Checklist do Contrato' }
        },
        {
          path: 'termos-aditivos/:modo',
          loadChildren: './termos-aditivos/termos-aditivos.module#TermosAditivosModule'
        }
      ]
    }
  ];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DetalharRoutingModule { }
