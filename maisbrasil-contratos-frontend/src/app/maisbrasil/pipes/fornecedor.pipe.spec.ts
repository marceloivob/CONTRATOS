import { FornecedorPipe } from "./fornecedor.pipe";
import { CnpjPipe } from './cnpj.pipe';
import { CpfPipe } from './cpf.pipe';
import { InscricaoGenericaPipe } from './inscricaogenerica.pipe';


describe('FornecedorPipe', () => {
  it('create an instance', () => {
    const pipe = new FornecedorPipe(new CnpjPipe, new CpfPipe, new InscricaoGenericaPipe);
    expect(pipe).toBeTruthy();
  });
});
