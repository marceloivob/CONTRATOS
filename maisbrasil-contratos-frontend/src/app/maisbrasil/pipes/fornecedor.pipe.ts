import { Pipe, PipeTransform } from '@angular/core';
import { FornecedorModel } from 'src/app/model/proposta/proposta.state.model';
import { CnpjPipe } from './cnpj.pipe';
import { CpfPipe } from './cpf.pipe';
import { InscricaoGenericaPipe } from './inscricaogenerica.pipe';

@Pipe({
  name: 'fornecedor'
})
export class FornecedorPipe implements PipeTransform {

    constructor(
        private readonly cnpjPipe: CnpjPipe,
        private readonly cpfPipe: CpfPipe,
        private readonly inscricaoGenericaPipe: InscricaoGenericaPipe,
      ) {  }
  
    transform(fornecedor: FornecedorModel[]): string[] {
        if (fornecedor) {
            return fornecedor.map(f => this.fornecedorLabel(f));
        } else {
            return [];
        }
    }
  
    fornecedorLabel(fornecedor: FornecedorModel) {
        
        if (fornecedor) {
            let tipoDaIdentificacao = fornecedor.tipoIdentificacao;
    
            let identificacao = fornecedor.identificacao;
        
            if (tipoDaIdentificacao === 'CNPJ') {
              identificacao = this.cnpjPipe.transform(fornecedor.identificacao);
            } else if (tipoDaIdentificacao === 'IG') {
              identificacao = this.inscricaoGenericaPipe.transform(fornecedor.identificacao);
            } else {
              tipoDaIdentificacao = 'CPF';
              identificacao = this.cpfPipe.transform(fornecedor.identificacao);
            }
        
           return `${fornecedor.tipoIdentificacao}: ${identificacao} - ${fornecedor.razaoSocial}`;
        }
        else {
          return '';
        }
        
    }
}
