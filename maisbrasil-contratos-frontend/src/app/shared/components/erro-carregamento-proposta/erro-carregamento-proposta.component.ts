import { Component } from '@angular/core';
import { User } from '@serpro/ngx-siconv';
import { Store } from '@ngxs/store';
import { SiconvLegacyService } from 'src/app/model/siconv/siconv-legacy.service';
import { UserAuthorizerService } from 'src/app/model/user/user-authorizer.service';
import { BaseComponent } from 'src/app/maisbrasil/util/base.component';
import { AppConfig } from 'src/app/core/app.config';

@Component({
  selector: 'app-erro-carregamento-proposta',
  templateUrl: './erro-carregamento-proposta.component.html'
})
export class ErroCarregamentoPropostaComponent extends BaseComponent {

  menuLoaded = false;
  menu: any;
  usuario: User;

  public link = AppConfig.urlToSICONVService;

  constructor(
    protected store: Store,
    private service: SiconvLegacyService,
    private authorizer: UserAuthorizerService) {
    super(store);
  }

  init() {
    this.getUsuario();
    this.recuperarMenu();
  }

  getUsuario() {
    this.usuario = new User(this.authorizer.user.name);
  }

  getPasswordUrl() {
    return AppConfig.urlToSICONVService + '/siconv/secure/TrocaDeSenhaObrigatoriaProcessar.do?menu=true';
  }

  getProfileUrl() {
    return AppConfig.urlToSICONVService + '/siconv/participe/AtualizarDadosUsuario/AtualizarDadosUsuario.do';
  }

  sessionTimeFeedback(sessionTime) {
    if (sessionTime === 'expired') {
      const initialState = {
        msg: 'A sessão encerrou. Será preciso logar novamente.',
        logout: true
      };

    }
    if (sessionTime === 'warning') {
      const initialState = {
        msg: 'Esta sessão irá se encerrar em menos 3 minutos! Salve o seu trabalho para evitar perdas.',
        logout: false
      };
    }
  }

  logoutFeedback(logout) {
    const initialState = {
      msg: 'A sessão encerrou. Será preciso logar novamente.',
      logout: true
    };
    window.location.href = AppConfig.urlToSICONVService + '?LLO=true';
  }

  recuperarMenu() {
    this.service.getMenu().subscribe(
      (values: any) => {
        this.menuLoaded = true;
        this.menu = values;
      }
    );
  }

}