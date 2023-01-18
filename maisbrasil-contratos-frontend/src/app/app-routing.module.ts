import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IntegrationGuard } from './core/guards/integration.guard';
import { ErroCarregamentoPropostaComponent } from './shared/components/erro-carregamento-proposta/erro-carregamento-proposta.component';
import { NaoExisteVRPLAceitoComponent } from './shared/components/nao-existe-vrpl-aceito-error/nao-existe-vrpl-aceito-error.component';
import { PropostaNaoInformadaComponent } from './shared/components/proposta-nao-informada/proposta-nao-informada.component';
import { PropostaNaoEncontradaComponent } from './shared/components/proposta-nao-encontrada/proposta-nao-encontrada.component';
import { PropostaNaoEncontradaVrplComponent } from './shared/components/proposta-nao-encontrada-vrpl/proposta-nao-encontrada-vrpl.component';

const routes: Routes = [
  { path: '', canActivate: [IntegrationGuard], redirectTo: '/contratos/instrumentos-contratuais', pathMatch: 'full' },
  { path: 'contratos', loadChildren: './contratos/contratos.module#ContratosModule' },
  { path: 'proposta-nao-informada', component: PropostaNaoInformadaComponent },
  { path: 'erro-carregamento-proposta', component: ErroCarregamentoPropostaComponent },
  { path: 'nao-existe-vrpl-aceito', component: NaoExisteVRPLAceitoComponent },
  { path: 'proposta-nao-encontrada', component: PropostaNaoEncontradaComponent },
  { path: 'proposta-nao-encontrada-vrpl', component: PropostaNaoEncontradaVrplComponent },
  { path: '**', redirectTo: '/contratos/instrumentos-contratuais', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
