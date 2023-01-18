
export class LoadProposta {
  static readonly type = '[CONTRATOS]LOAD_PROPOSTA';

  constructor() {}
}

export class LoadHistoricoContratosDaProposta {
  static readonly type = '[CONTRATOS]LOAD_HISTORICO_CONTRATOS_DA_PROPOSTA';

  constructor(public idProposta: number) {}
}

export class LoadHistoricoContratosExcluidosDaProposta {
  static readonly type = '[CONTRATOS]LOAD_HISTORICO_CONTRATOS_EXCLUIDOS_DA_PROPOSTA';

  constructor(public idProposta: number) {}
}