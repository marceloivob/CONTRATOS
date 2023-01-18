import { ContratoModel, PermissaoAlteracaoContrato } from './contrato.state.model';

export class ContratoMock {

    constructor() {}

    createContrato(num: number): ContratoModel {
        const c: ContratoModel = {
            id: num,
            numero: num.toString(),
            objeto: `Objeto ${num}`,
            inSituacao: `Situação ${num}`,
            inSituacaoDescricao: '',
            dataAssinatura: new Date('10/10/2019'),
            dataPublicacao: new Date('10/10/2019'),
            fimVigencia: new Date('10/10/2020'),
            fimVigenciaOriginal: new Date('10/10/2020'),
            inicioVigencia: new Date('10/10/2019'),
            valorTotal: 1000.00,
            valorReferenteModalidade: 475454.00,
            permissaoAlteracao: PermissaoAlteracaoContrato.TOTAL,
            propostaId: 1,
            propostaIdSiconv: 1,
            versao: 1
        };

        return c;
    }

    listContratos(): ContratoModel[] {
        const contratos: ContratoModel[] = [];
        for (let i = 1; i <= 10; i += 1) {
            contratos.push(this.createContrato(i));
        }

        return contratos;
    }

    deleteContrato(id: number): string {
        return 'OK';
    }
}
