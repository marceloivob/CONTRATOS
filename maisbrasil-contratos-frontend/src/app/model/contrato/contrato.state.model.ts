import { AnexoModel, ContratoAnexoModel } from '../anexo/anexo.state.model';

export interface ContratoStateModel {
    contratos?: ContratoModel[];
    contratoSelecionado?: ContratoModel;
    mensagens?: MensagemModel[];
}

export interface ContratoModel {
    numero: string;
    objeto: string;
    dataAssinatura: Date;
    dataPublicacao: Date;
    inicioVigencia: Date;
    fimVigencia: Date;
    fimVigenciaOriginal: Date;
    valorTotal: number;
    valorReferenteModalidade: number;
    inSituacao: string;
    inSituacaoDescricao: string;
    inSituacaoExibicao?: string;
    inSituacaoExibicaoDescricao?: string;

    propostaId: number;
    propostaIdSiconv: number;

    id: number;
    versao: number;

    permissaoAlteracao: PermissaoAlteracaoContrato;

    lotes?: LoteModel[];

    anexos?: AnexoModel[];
    contratoAnexos?: ContratoAnexoModel[];

    dadosChecklist?: DadosChecklistModel;

    mensagens?: MensagemModel[];
}



export interface LicitacaoModel {
    numeroAno: string;
    inSituacao: string;
    objeto: string;
    dataHomologacao: Date;
    valorProcesso: number;
    idLicitacaoFk: number;
    propostaId: number;
    processoDeExecucao: string;
    id: number;
    dataPublicacao: Date;
    regimeContratacao: string;
    statusProcesso: string;
    versao: number;
    modalidade: string;
    numeroProcesso: string;
}

export interface LoteModel {
    contratoId: number;
    numero: number;
    fornecedorId: number;
    id: number;
    versao: number;
    licitacaoId: number;
    somatorioSubmetas: number;

    submetas: SubmetaModel[];
}

export interface SubmetaModel {
    numero: number;
    metaId: number;
    inRegimeExecucao: string;
    inSituacao: string;
    vlContrapartida: number;
    vlTotalLicitado: number;
    propostaId: number;
    vlRepasse: number;
    vlOutros: number;
    loteId: number;
    id: number;
    versao: number;

    descricao: string;
    descricaoDetalhada: string;
    descricaoMeta: string;

    po: PoModel;
    meta: MetaModel;
}

export interface MetaModel {
    qtItens: number;
    numero: number;
    idMetaVrpl: number;
    txDescricao: string;
    id: number;
    inSocial: boolean;
    versao: number;
}

export interface PoModel {
    id: number;
    idPoVrpl: number;
    submetaId: number;
    dtPrevisaoInicioObra: Date;
    qtMesesDutacaoObra: number;
    inAcompanhamentoEventos: number;
    inDesonerado: boolean;
    sgLocalidade: string;
    dtBaseAnalise: Date;
    referencia: string;
    dtBaseVrpl: Date;
    dtPrevisaoInicioObraAnalise: Date;
    versao: number;
}

export enum SituacaoContrato {
    EM_RASCUNHO = 'RAS',
    CONCLUIDO = 'CON' ,
    EXCLUIDO = 'EXC',
    RASCUNHO_ADITIVACAO_EM_RASCUNHO = 'RAR',
	RASCUNHO_ADITIVACAO_CONCLUIDA = 'RAC',
	CONCLUIDO_ADITIVACAO_EM_RASCUNHO = 'ADR',
	CONCLUIDO_ADITIVACAO_CONCLUIDA = 'ADC'
}

export class CheckModel {
    indice: number;
    label: string;
    status: string;
    mensagemErro: string;
    itemDeVerificacaoPara: string;
}

export class DadosChecklistModel {
    situacaoAIO: string;
    checklist: CheckModel[];
    mensagem: string;
    tipoMensagem: string;
}

export enum StatusCheck {
    OK = 'OK',
    NOK = 'NOK',
    NA = 'NA',
    INDISPONIVEL = 'INDISPONIVEL'
}

export class MensagemModel {
    texto: string;
    severidade: string;
}

export enum SeveridadeMensagem {
    SUCESS = 'SUCESS',
    INFO =  'INFO',
    WARN = 'WARN',
    ERROR = 'ERROR'
}

export enum PermissaoAlteracaoContrato {
    TOTAL = 'TOTAL',
    PARCIAL = 'PARCIAL',
    NAO_PERMITIDA = 'NAO_PERMITIDA',
    INDISPONIVEL = 'INDISPONIVEL'
}

export enum SituacaoAIO {
    NAO_EMITIDA = 'Não Emitida.'
}

export class EmissaoAIORequestModel {
    idContrato: number;
    dataEmissaoAIO: Date;
    justificativa: string;
}
