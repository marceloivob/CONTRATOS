
export class IDPLogin {
  static readonly type = '[CONTRATOS]LOGIN';

  constructor(public url: string) { }
}

export class IDPLogout {
  static readonly type = '[CONTRATOS]LOGOUT';
}

export class RefreshToken {
  static readonly type = '[CONTRATOS]REFRESHTOKEN';
}

export class LoggedUser {
  static readonly type = '[CONTRATOS]LOGGEDUSER';

  constructor(public token: string) { }
}
