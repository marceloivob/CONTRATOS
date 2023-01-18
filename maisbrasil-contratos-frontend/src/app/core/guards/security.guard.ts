import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate, ActivatedRoute } from '@angular/router';
import { Store } from '@ngxs/store';
import { UserState } from 'src/app/model/user/user.state';
import { IDPLogin } from 'src/app/model/user/user.actions';

@Injectable({
  providedIn: 'root'
})
export class SecurityGuard implements CanActivate {

  constructor(private readonly store: Store, private readonly route: ActivatedRoute) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const loggedIn = this.store.selectSnapshot(UserState.isLoggedin);

    if (!loggedIn) {
      this.store.dispatch(new IDPLogin(state.url));
    }
    return loggedIn;
  }

}
