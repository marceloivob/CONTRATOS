import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContratosComponent } from './contratos.component';
import { SecurityGuard } from '../core/guards/security.guard';
import { CheckVRPLAceitoGuard } from '../core/guards/check-vrpl-aceito.guard';
import { HistoricoComponent } from './historico/historico.component';

const routes: Routes = [
    { path: '', redirectTo: '/contratos/instrumentos-contratuais', pathMatch: 'full' },
    {
        path: '',
        component: ContratosComponent,
        canActivate: [SecurityGuard, CheckVRPLAceitoGuard],
        children: [
            {
                path: 'instrumentos-contratuais',
                loadChildren: './instrumentos-contratuais/instrumentos-contratuais.module#InstrumentosContratuaisModule'
            },
            {
              path: 'historico',
              component: HistoricoComponent, data: { breadcrumb: 'Histórico' }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ContratosRoutingModule { }
