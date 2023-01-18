import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER, LOCALE_ID } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxsModule } from '@ngxs/store';
import { NgxsRouterPluginModule } from '@ngxs/router-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { HttpClientModule } from '@angular/common/http';
import { ModelModule } from './model/model.module';
import { SecurityGuard } from './core/guards/security.guard';
import { SimpleTimer } from 'ng2-simple-timer';
import { AppConfig } from './core/app.config';
import { IntegrationGuard } from './core/guards/integration.guard';
import { httpInterceptorProviders } from './core/interceptors';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { registerLocaleData, DatePipe } from '@angular/common';

import localeptBR from '@angular/common/locales/br';
import localeptBRExtra from '@angular/common/locales/extra/br';
import { SpinnerComponent } from './shared/components/spinner/spinner.component';
import { NgHttpLoaderModule } from 'ng-http-loader';
import { SiconvModule } from './siconv.module';
import { PropostaNaoInformadaComponent } from './shared/components/proposta-nao-informada/proposta-nao-informada.component';

export function loadSettings() {
  return () => AppConfig.loadSettings();
}

import localePt from '@angular/common/locales/pt';
registerLocaleData(localePt, localeptBR, localeptBRExtra);
import { defineLocale } from 'ngx-bootstrap/chronos';
import { ptBrLocale } from 'ngx-bootstrap/locale';
import { ErroCarregamentoPropostaComponent } from './shared/components/erro-carregamento-proposta/erro-carregamento-proposta.component';
import { CheckVRPLAceitoGuard } from './core/guards/check-vrpl-aceito.guard';
import { NaoExisteVRPLAceitoComponent } from './shared/components/nao-existe-vrpl-aceito-error/nao-existe-vrpl-aceito-error.component';
import { PropostaNaoEncontradaComponent } from './shared/components/proposta-nao-encontrada/proposta-nao-encontrada.component';
import { PropostaNaoEncontradaVrplComponent } from './shared/components/proposta-nao-encontrada-vrpl/proposta-nao-encontrada-vrpl.component';
import { MaisBrasilModule } from './maisbrasil/mais-brasil.module';
defineLocale('pt-br', ptBrLocale);

@NgModule({
  declarations: [
    AppComponent,
    PropostaNaoInformadaComponent,
    PropostaNaoEncontradaComponent,
    PropostaNaoEncontradaVrplComponent,
    ErroCarregamentoPropostaComponent,
    NaoExisteVRPLAceitoComponent,
    SpinnerComponent
  ],
  imports: [
    BrowserModule,
    MaisBrasilModule,
    HttpClientModule,
    AppRoutingModule,
    SiconvModule,
    FormsModule,
    ReactiveFormsModule,
    NgHttpLoaderModule,
    NgxsModule.forRoot([], { developmentMode: false }),
    NgxsRouterPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot({
      disabled: false
    }),
    ModelModule
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: loadSettings, deps: [], multi: true },
    IntegrationGuard,
    SecurityGuard,
    CheckVRPLAceitoGuard,
    httpInterceptorProviders,
    DatePipe,
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    SimpleTimer
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    SpinnerComponent
  ]
})
export class AppModule { }
