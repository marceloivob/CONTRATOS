import { Role } from './role.enum';
import { Injectable, OnDestroy } from '@angular/core';
import { Store } from '@ngxs/store';
import { UserStateModel } from './user.state.model';
import { UserState } from './user.state';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserAuthorizerService implements OnDestroy {

  private user$: UserStateModel;

  private unsubscribe$ = new Subject();

  constructor(store: Store) {
    this.user$ = store.selectSnapshot(UserState);

    store.select(UserState)
      .pipe( takeUntil(this.unsubscribe$) )
      .subscribe( user => this.user$ = user );
  }

  get user() {
    return this.user$;
  }

  get isMandataria() {
    return this.user$ && this.user$.profile === 'MANDATARIA';
  }

  get isProponente() {

    return this.user$ && this.user$.profile === 'PROPONENTE' && this.hasAnyRole ([Role.FISCAL_CONVENENTE,
                                                                      Role.GESTOR_CONVENIO_CONVENENTE,
                                                                      Role.GESTOR_FINANCEIRO_CONVENENTE,
                                                                      Role.OPERADOR_FINANCEIRO_CONVENENTE
    ]);

  }

  get isConcedente() {
    return this.user$ && this.user$.profile === 'CONCEDENTE';
  }

  get isAdmin() {

    return this.user$ && this.user$.profile === 'CONCEDENTE' && this.hasAnyRole ([Role.ADMINISTRADOR_SISTEMA,
                                                                      Role.ADMINISTRADOR_SISTEMA_ORGAO_EXTERNO
  ]);
  
  }

  get isConcedenteGCC() {
    return this.user$ && this.user$.profile === 'CONCEDENTE' && this.hasAnyRole ([Role.GESTOR_CONVENIO_CONCEDENTE]);
  }

  get isMandatariaGCIM() {
    return this.user$ && this.user$.profile === 'MANDATARIA' && this.hasAnyRole ([Role.GESTOR_CONVENIO_INSTITUICAO_MANDATARIA]);
  }

  get podeEditarContrato(): boolean {
    return this.isProponente;
  }

  get podeEmitirAIO(): boolean {
    // RN 648598: SICONV-InstrumentosContratuais-ListarChecklist-RN-OpcaoVerificarEmissaoAIO
    return this.isProponente || this.isAdmin || this.isConcedente || this.isMandataria;
  }

  get podeEmitirAIOdurantePeriodoEleitoral(): boolean {
    // RN 648598: SICONV-InstrumentosContratuais-ListarChecklist-RN-OpcaoVerificarEmissaoAIO
    // Replicar essa RN no backend (PermissionController.usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral)
    return this.isAdmin || this.isConcedente || this.isMandataria;
  }

  get podeCancelarAIO(): boolean {
    return this.isAdmin || this.isConcedenteGCC || this.isMandatariaGCIM;
  }  

  get podeConcluirContrato(): boolean {
    return this.isProponente;
  }

  get precisaPreencherFormularioEmissaoAIO(): boolean {
    // 772989: SICONV-InstrumentosContratuais-RN-FormularioEmissaoAIO
    return this.isAdmin || this.isConcedente || this.isMandataria;
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  hasAnyRole(roles: Role[] ): boolean {

    return this.user.roles.map(myRole => roles.find(roleArray =>  Role.valueOf(myRole) === roleArray))
    .filter(roleFound => roleFound != null)
    .length > 0;

  }
}
