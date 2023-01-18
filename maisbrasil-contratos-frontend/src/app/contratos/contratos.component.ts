import { Component, TemplateRef, ViewChild } from '@angular/core';
import { SiconvLegacyService } from '../model/siconv/siconv-legacy.service';
import { UserAuthorizerService } from '../model/user/user-authorizer.service';
import { BaseComponent } from '../maisbrasil/util/base.component';
import { Store, Select } from '@ngxs/store';
import { AppConfig } from '../core/app.config';
import { RefreshToken } from '../model/user/user.actions';
import { User, AlertMessageService } from '@serpro/ngx-siconv';
import { LoadProposta } from '../model/proposta/proposta.actions';
import { BsModalService, BsModalRef } from 'ngx-bootstrap';
import { PropostaState } from '../model/proposta/proposta.state';
import { Observable } from 'rxjs';
import { PropostaStateModel } from '../model/proposta/proposta.state.model';
import { MenuComponent } from '../maisbrasil/menu/menu.component';

@Component({
  selector: 'app-contratos',
  templateUrl: './contratos.component.html',
  styleUrls: ['./contratos.component.scss']
})
export class ContratosComponent extends BaseComponent {

  @ViewChild('templateSessao') elementTemplate: TemplateRef<any>;
  @Select(PropostaState) propostaObservable: Observable<PropostaStateModel>;

  @ViewChild(MenuComponent)
  private menuComponent: MenuComponent;
  
  menu: any;
  exibeMenu = true;
  menuLoaded = false;

  usuario: User;

  modalRef: BsModalRef;

  tempoTotalSessao = 30;
  warningTime = 5;


  constructor(
    protected store: Store,
    private service: SiconvLegacyService,
    private authorizer: UserAuthorizerService,
    private alertMessageService: AlertMessageService,
    private readonly modalService: BsModalService
  ) {
    super(store);
  }

  init() {
    this.getUsuario();
    this.recuperarMenu();
  }

  getUsuario() {
    const nome = this.authorizer.user.name;

    this.usuario = new User(nome);
  }

  recuperarMenu() {
    this.service.getMenu().subscribe(
      (values: any) => {
        this.menuLoaded = true;
        this.menu = values;
      }
    );
  }

  getPasswordUrl() {
    return AppConfig.urlToSICONVService + '/siconv/secure/TrocaDeSenhaObrigatoriaProcessar.do?menu=true';
  }

  getProfileUrl() {
    return AppConfig.urlToSICONVService + '/siconv/participe/AtualizarDadosUsuario/AtualizarDadosUsuario.do';
  }

  logoutFeedback(logout) {
    window.location.href = AppConfig.urlToSICONVService + '?LLO=true';
  }

  sessionTimeFeedback(sessionTime) {
    if (sessionTime === 'expired') {
      
      this.modalRef.hide();
      this.alertMessageService.warn('Sessão Expirada');
      
      window.location.href = AppConfig.urlToSICONVService + '?LLO=true';
        
    }
    if (sessionTime === 'warning') {
      this.alertMessageService.warn('Sessão expirará');
      this.modalRef = this.modalService.show(this.elementTemplate);
    }
  }


  loadActions() {
    return new LoadProposta();
  }

  confirmaReinicio() {
    this.modalRef.hide();
    this.store.dispatch(new RefreshToken());
    this.menuComponent.minutos = this.tempoTotalSessao - 1;
    this.menuComponent.segundos = 60;
    this.exibeMenu = false;
    setTimeout(() => this.exibeMenu = true, 0);
  }

  cancelaReinicio() {
    this.modalRef.hide();
  }

}
