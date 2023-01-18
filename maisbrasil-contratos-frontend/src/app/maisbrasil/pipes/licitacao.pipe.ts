import { Pipe, PipeTransform } from '@angular/core';
import { LicitacaoModel } from 'src/app/model/contrato/contrato.state.model';

@Pipe({
    name: 'licitacao'
  })
  export class LicitacaoPipe implements PipeTransform {
    transform(licitacao: LicitacaoModel[]): string[] {
      if (licitacao) {
        console.log('licitacao pipe');
        console.log(licitacao);
        return licitacao.map(l => l.numeroAno);
      } else {
        return [];
      }
    }
  }