import { TermoAditivoModel } from "./termo-aditivo.state.model";

export class LoadTermosAditivosPorContrato {
    static readonly type = '[CONTRATOS]LOAD_TERMOS_ADITIVOS_POR_CONTRATO';

    constructor(public idContrato: number) {}
}

export class DeleteTermoAditivo {
    static readonly type = '[CONTRATOS]DELETE_TERMO_ADITIVO';

    constructor(public id: number) {}
}

export class SalvarTermoAditivo {
    static readonly type = '[CONTRATOS]SALVAR_TERMO_ADITIVO';

    constructor(public termoAditivo: TermoAditivoModel) {}
}

export class AtualizarTermoAditivo {
    static readonly type = '[CONTRATOS]ATUALIZAR_TERMO_ADITIVO';

    constructor(public termoAditivo: TermoAditivoModel) {}
}

export class ConcluirNovoTermoAditivo {
    static readonly type = '[CONTRATOS]CONCLUIR_NOVO_TERMO_ADITIVO';

    constructor(public termoAditivo: TermoAditivoModel) {}
}

export class ConcluirTermoAditivo {
    static readonly type = '[CONTRATOS]CONCLUIR_TERMO_ADITIVO';

    constructor(public termoAditivo: TermoAditivoModel) {}
}

export class LoadTermoAditivoSelecionado {
    static readonly type = '[CONTRATOS]LOAD_TERMO_ADITIVO_SELECIONADO';
    constructor(public idTermoAditivo: number) { }
}

export class LimparTermoAditivoSelecionado {
    static readonly type = '[CONTRATOS]LIMPAR_TERMO_ADITIVO_SELECIONADO';

    constructor() {}
}
