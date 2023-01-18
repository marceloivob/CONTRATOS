import { Component } from '@angular/core';
import { AppConfig } from 'src/app/core/app.config';

@Component({
  selector: 'app-proposta-nao-informada',
  templateUrl: './proposta-nao-informada.component.html'
})
export class PropostaNaoInformadaComponent {

  public link = AppConfig.urlToSICONVService;

}
