import { AnexoModel, ContratoAnexoModel } from '../anexo/anexo.state.model';
import { MensagemModel } from '../contrato/contrato.state.model';

export interface TermoAditivoStateModel {
    termosAditivos?: TermoAditivoModel[];
    termoAditivoSelecionado?: TermoAditivoModel;
    mensagens?: MensagemModel[];
}

export class TermoAditivoModel {
    
    id: number;
    versao: number;
    inAlteracaoVigencia: boolean;
    inAlteracaoClausula: boolean;
    inAlteracaoObjeto: boolean;
    numero: string;
    dataAssinatura?: Date;
    dataPublicacao?: Date;
    contratoId: number;
    inAlteracaoSupressaoAcrescimo?: string;
    valorSupressaoAcrescimo?: number;
    novaDataFimVigencia?: Date;
    justificativa?: string;
    descricaoAmpliacaoObjeto?: string;
    inSituacao: string;
    tipoTermoAditivo?: string;
    anexos?: AnexoModel[];
    contratoAnexos?: ContratoAnexoModel[];

}

export enum SituacaoTermoAditivo {
    EM_RASCUNHO = 'RAS',
    CONCLUIDO = 'CON' ,
    EXCLUIDO = 'EXC'
}

    
