import { State, Selector, Action, StateContext, NgxsOnInit, Store } from '@ngxs/store';
import { IdpService } from './idp.service';
import { IDPLogin, LoggedUser, RefreshToken } from './user.actions';
import { UserStateModel } from './user.state.model';
import { HTTPRequest } from '../app/app.actions';
import { SessionService } from './session.service';
import { Navigate } from '@ngxs/router-plugin';


interface IDPJwt {
  id: number;
  cpf: string;
  nome: string;
  idProposta: number;
  ente: string;
  tipoEnte: string;
  roles?: string[];
  exp: number;
  iss: string;
  aud: string;
  iat: number;
}

@State<UserStateModel>({
  name: 'user',
  defaults: null
})
export class UserState {

  constructor(
    private idpService: IdpService,
    private store: Store,
    private sessionService: SessionService
  ) { }

  @Selector()
  static token(state: UserStateModel) { return state.token; }

  @Selector()
  static isLoggedin(state: UserStateModel) {
    if (state && state.token) {
      return true;
    }
    return false;
  }

  @Selector()
  static getProposta(state: UserStateModel) {
    if (state && state.idProp) {
      return state.idProp;
    }

    // this.store.dispatch(new Navigate(['proposta-nao-informada']));
  }

  @Action(IDPLogin)
  login(ctx: StateContext<UserStateModel>, action: IDPLogin) {
    this.idpService.login(action.url);
  }

  @Action(LoggedUser)
  logged({ patchState, dispatch }: StateContext<UserStateModel>, { token }: LoggedUser) {
    const jwt = this.parseJwt(token);
    patchState({
      name: jwt.nome,
      cpf: jwt.cpf,
      idProp: jwt.idProposta,
      profile: jwt.tipoEnte,
      roles: jwt.roles,
      token
    });
  }

  @Action(HTTPRequest)
  httpRequest(context: StateContext<UserStateModel>) {
    this.refresh(context);
  }

  @Action(RefreshToken)
  refresh({ getState }: StateContext<UserStateModel>) {
    const user = getState();
    if (user) {
      this.sessionService.refresh(user.token);
    }
  }

  parseJwt(token): IDPJwt {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64)) as IDPJwt;
  }

}
