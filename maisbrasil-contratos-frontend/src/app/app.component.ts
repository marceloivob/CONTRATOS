import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserAuthorizerService } from './model/user/user-authorizer.service';
import { SpinnerComponent } from './shared/components/spinner/spinner.component';
import { AppConfig } from './core/app.config';
import { User } from '@serpro/ngx-siconv';
import { Store, Select } from '@ngxs/store';
import { SiconvLegacyService } from './model/siconv/siconv-legacy.service';
import { Observable } from 'rxjs';
import { PropostaState } from './model/proposta/proposta.state';
import { PropostaStateModel } from './model/proposta/proposta.state.model';
import { Navigate } from '@ngxs/router-plugin';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  @Select(PropostaState) propostaObservable: Observable<PropostaStateModel>;

  menuLoaded = false;
  menu: any;
  usuario: User;
  exibeMenu = true;

  ehPrimeiraVezQueEntra = true;
  isLogin = false;

  public spinnerComponent = SpinnerComponent;

  constructor(
    protected store: Store,
    private readonly route: ActivatedRoute,
    private service: SiconvLegacyService,
    private authorizer: UserAuthorizerService) {

    this.ehPrimeiraVezQueEntra = true;
  }

  ngOnInit() {
    this.store.select(PropostaState)
      // .pipe(this.takeUntilDestroyed())
      .subscribe(proposta => {

        if (this.ehPrimeiraVezQueEntra) {
        }

        this.ehPrimeiraVezQueEntra = false;
      });

  }

  // sessionTimeFeedback(sessionTime) {
  //   if (sessionTime === 'expired') {
  //     console.log('A sessão encerrou. Será preciso logar novamente');
  //   }
  //   if (sessionTime === 'warning') {
  //     console.log('Esta sessão irá se encerrar em menos 2 minutos');
  //   }
  // }
  // 
  // logoutFeedback(logout) {
  //   if (logout === true) {
  //     this.auth.logout(true);
  //     // tslint:disable-next-line:max-line-length
  //     // window.location.href = `https://sso.staging.acesso.gov.br/logout?post_logout_redirect_uri=${window.location.protocol}//${window.location.host}/maisbrasil-portal-frontend`;
  // 
  //   }
  // }
  // 
  // loginFeedback(login) {
  //   if (login === true) {
  //     this.router.navigate(['/login']);
  // 
  //   }
  // }
  // 
  // carregarDadosUsuarioCorrente() {
  // if (!this.usuarioService.getUsuarioCarregado()) {
  //   this.usuarioService.getUsuarioSessao().subscribe(
  //     (response: UsuarioSessao) => {
  //       this.usuarioService.setUsuarioCarregado(response);
  //     }
  //   );
  // }
  // }

  // getNomeExibicaoUsuario(nome: string, quantidadeSolicitada: number): string {
  //   let nomeExibicao = '';
  //   if (nome) {
  //     let nomes: string[];
  //     nomes = nome.split(' ');
  //     if (nomes.length >= quantidadeSolicitada) {
  //       for (let i = 0; i < quantidadeSolicitada; i++) {
  //         nomeExibicao += nomes[i];
  //         if (i < quantidadeSolicitada) {
  //           nomeExibicao += ' ';
  //         }
  //       }
  //     } else {
  //       nomeExibicao = nomes[0];
  //     }
  //   }
  //   return nomeExibicao;
  // }

  // ngOnInit() {

  // if (this.auth.isTokenValid()) {
  //   this.usuario = {
  //     name: this.auth.getUsuario().nome,
  //     displayName: this.auth.getUsuario().nome,
  //     roles: [],
  //     profiles: [],
  //     imageUrl: '',
  //   };

  //   this.carregarDadosUsuarioCorrente();
  // }

  // }
}
