import { AnexoModel } from './anexo.state.model';

export class LoadAnexosLicitacao {
    static readonly type = '[CONTRATOS]LOAD_ANEXO_LICITACAO';
    constructor() { }
}

export class SaveAnexo {
    static readonly type = '[CONTRATOS]SAVE_ANEXO';

    constructor(public idContrato: number, public anexo: AnexoModel) { }
}

export class SaveAnexoTermoAditivo {
    static readonly type = '[CONTRATOS]SAVE_ANEXO_TERMO_ADITIVO';

    constructor(public idTermoAditivo: number, public anexo: AnexoModel) { }
}

export class LoadTipoDeAnexo {
    static readonly type = '[CONTRATOS]LOAD_TIPO_DE_ANEXO';

    constructor() { }
}

export class DeleteAnexo {
  static readonly type = '[CONTRATOS]DELETE_ANEXO';

  constructor(public anexo: AnexoModel) { }
}

export class DeleteAnexoTermoAditivo {
    static readonly type = '[CONTRATOS]DELETE_ANEXO_TERMO_ADITIVO';
  
    constructor(public anexo: AnexoModel) { }
  }
  
export class LoadAnexosPorTipo {
    static readonly type = '[CONTRATOS]LOAD_ANEXO_POR_TIPO';
    constructor(public idContrato: number, public tipoAnexo: string) { }
}

export class LoadContratoSelecionado {
    static readonly type = '[CONTRATOS]LOAD_CONTRATO_SELECIONADO';
    constructor(public idContrato: number) { }
}


export class LoadAnexosPorConjuntoTipos {
    static readonly type = '[CONTRATOS]LOAD_ANEXO_POR_CONJUNTO_TIPOS';
    constructor(public idContrato: number, public tiposAnexo: string[]) { }
}

export class LoadAnexosTermoAditivoPorConjuntoTipos {
    static readonly type = '[CONTRATOS]LOAD_ANEXO_TERMO_ADITIVO_POR_CONJUNTO_TIPOS';
    constructor(public idTermoAditivo: number, public tiposAnexo: string[]) { }
}

