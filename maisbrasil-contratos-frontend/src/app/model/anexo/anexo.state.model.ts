export interface AnexoModel {
    id: number;
    contratoId?: number;
    nmArquivo: string;
    dtUpload: Date;
    txDescricao?: string;
    inTipoAnexo: string;
    tipoDoAnexoAsString?: string;
    nomeEnviadoPor?: string;
    inPerfilUsuario?: string;
    linkToDownload?: string;
    arquivo?: any;
    bucket?: string ;
    idCpfEnviadoPor?: string;
    versao: number;
    caminho?: string;
    termoAditivoId?: number;
    tamanhoArquivo?: number;
}

export interface TiposDeAnexos {
    key: string;
    value: string;
}

export interface ContratoAnexoModel {
    arquivo: string;
    nomeArquivo: string;
    descricao: string;
    tipoAnexo: string;
    tamanhoArquivo: number;
    versao?: number;
}