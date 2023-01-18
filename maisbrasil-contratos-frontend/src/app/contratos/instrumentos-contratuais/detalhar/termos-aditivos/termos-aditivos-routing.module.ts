import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ListagemTermosAditivosComponent } from './listagem-termos-aditivos/listagem-termos-aditivos.component';

const routes: Routes = [
    { path: '', redirectTo: 'listagem', pathMatch: 'full' },
    { path: 'listagem', component: ListagemTermosAditivosComponent, data: { breadcrumb: 'Termos Aditivos' } },
    { path: 'detalhar',
      loadChildren: './detalhar-termo-aditivo/detalhar-termo-aditivo.module#DetalharTermoAditivoModule'
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TermosAditivosRoutingModule { }
