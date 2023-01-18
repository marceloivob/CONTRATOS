import { ContratoModel, EmissaoAIORequestModel } from './contrato.state.model';

export class LoadContratosPorProposta {
    static readonly type = '[CONTRATOS]LOAD_CONTRATOS_POR_PROPOSTA';

    constructor(public idProposta: number) {}
}

export class DeleteContrato {
    static readonly type = '[CONTRATOS]DELETE_CONTRATO';

    constructor(public id: number) {}
}

export class SalvarContrato {
    static readonly type = '[CONTRATOS]SALVAR_CONTRATO';

    constructor(public contrato: ContratoModel) {}
}

export class AtualizarContrato {
    static readonly type = '[CONTRATOS]ATUALIZAR_CONTRATO';

    constructor(public contrato: ContratoModel) {}
}

export class ChecklistContrato {
    static readonly type = '[CONTRATOS]CHECKLIST_CONTRATO';

    constructor(public id: number) {}
}

export class ConcluirContrato {
    static readonly type = '[CONTRATOS]CONCLUIR_CONTRATO';

    constructor(public contrato: ContratoModel) {}
}

export class LimparContratoSelecionado {
    static readonly type = '[CONTRATOS]LIMPAR_CONTRATO_SELECIONADO';

    constructor() {}
}

export class VerificarEmissaoAIO {
    static readonly type = '[CONTRATOS]VERIFICAR_EMISSAO_AIO';

    constructor(public request: EmissaoAIORequestModel) {}
}

export class VerificarCancelarEmissaoAIO {
    static readonly type = '[CONTRATOS]VERIFICAR_CANCELAR_EMISSAO_AIO';
  
    constructor(public idProposta: number) {}
}
  
  export class CancelarEmissaoAIO {
    static readonly type = '[CONTRATOS]CANCELAR_EMISSAO_AIO';
  
    constructor(public idProposta: number) {}
}
  