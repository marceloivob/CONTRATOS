import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ListagemInstrumentosContratuaisComponent } from './listagem-instrumentos-contratuais/listagem-instrumentos-contratuais.component';

const routes: Routes = [
    { path: '', redirectTo: 'listagem', pathMatch: 'full' },
    { path: 'listagem', component: ListagemInstrumentosContratuaisComponent, data: { breadcrumb: 'Instrumentos Contratuais' } },
    { path: 'detalhar',
      loadChildren: './detalhar/detalhar.module#DetalharModule'
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class InstrumentosContratuaisRoutingModule { }
