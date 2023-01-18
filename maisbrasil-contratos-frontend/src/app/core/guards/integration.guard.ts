import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivate } from '@angular/router';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { AppConfig } from '../app.config';
import { Navigate } from '@ngxs/router-plugin';

@Injectable({
  providedIn: 'root'
})
export class IntegrationGuard implements CanActivate {

  constructor(private readonly store: Store) { }

  canActivate() {
    const loaded = AppConfig.loaded;
    if (!loaded) {
      this.store.dispatch(new Navigate(['erro-conexao-servidor']));
    }

    return AppConfig.loaded;
  }
}
