import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxsModule } from '@ngxs/store';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { UserState } from './user/user.state';
import { AppState } from './app/app.state';
import { AppHttpInterceptor } from './app/app.http.interceptor';
import { ContratoState } from './contrato/contrato.state';
import { PropostaState } from './proposta/proposta.state';
import { ContratoSelecionadoState } from './contrato/contratoSelecionado.state';
import { TermoAditivoState } from './termo-aditivo/termo-aditivo.state';
import { TermoAditivoSelecionadoState } from './termo-aditivo/termo-aditivo-selecionado.state';



@NgModule({
  imports: [
    CommonModule,
    NgxsModule.forFeature([
      AppState, UserState, ContratoState, PropostaState, ContratoSelecionadoState, TermoAditivoState, TermoAditivoSelecionadoState
    ]),
  ],
  declarations: [],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
  ]
})
export class ModelModule { }
