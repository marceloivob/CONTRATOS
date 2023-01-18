import { MensagemModel } from '../contrato/contrato.state.model';


export interface PropostaStateModel {
  idProposta: number;
  numeroProposta: string;
  anoProposta: string;
  valorGlobal: number;
  valorRepasse: number;
  valorContrapartida: number;
  modalidade: number;
  descricaoModalidade: string;
  objeto: string;
  numeroConvenio?: string;
  anoConvenio?: string;
  dataAssinaturaConvenio?: Date;
  codigoPrograma: string;
  nomePrograma: string;
  identificacaoProponente: string;
  nomeProponente: string;
  uf: string;
  percentualMinimoContrapartida: number;
  nomeMandataria?: string;
  categoria: string;
  nivelContrato?: string;
  apelidoDoEmpreendimento: string;
  situacaoAcffo: string;
  spaHomologado: boolean;
  urlRetornoSiconv?: string;
  infoConvenio?: string;
  versaoAtual: boolean; // Indicador se essa versão da Proposta é a atual (que pode ser editada)
  propostaAtual: number; // Número da Versão (campo Versão Nr)
  versao: number; // Número da Versão do Controle de Concorrência
  versaoSelecionada?: number; // Número da Versão Selecionada no Combo de Versionamento
  versoes: number[]; // Versões possíveis de serem selecionadas no Combo de Versionamento
  fornecedores: FornecedorModel[];
  idContratoEmissorAio: number;
  dataEmissaoAio: Date;
  termoCompromissoTemMandatar?: boolean;
  mensagens?: MensagemModel[];
  historicoContratos?: HistoricoContratoModel[];
  historicoContratosExcluidos?: HistoricoContratoModel[];
}

export interface FornecedorModel {
  identificacao: string;
  tipoIdentificacao: string;
  razaoSocial: string;
  id: number;
  versao: number;
}

export enum Modalidade {
  CONVENIO = 1,
  CONTRATO_REPASSE = 2,
  CONVENIO_CONTRATO_REPASSE = 6,
  TERMO_DE_COMPROMISSO = 11
}

export interface RegistroHistoricoContratoModel {
  idHistorico: number;
  numeroContratoTA: string;
  evento: string;
  situacao: string;
  dataHora: string;
  responsavel: string;
}

export interface HistoricoContratoModel {
  idContrato: number;
  numeroContrato: string;
  listaRegistros: RegistroHistoricoContratoModel[];
}
