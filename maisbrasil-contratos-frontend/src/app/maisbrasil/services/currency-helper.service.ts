import { Injectable } from '@angular/core';

@Injectable()
export class CurrencyHelperService {

  prefix = 'R$ ';

  DECIMAL_SEPARATOR = ',';
  THOUSANDS_SEPARATOR = '.';

  constructor() { }
  transform(value: number | string): string {
    value = value ? (+value).toString() : '0';

    let retorno: string;

    if (value.length > 2) {
      const inteiro = value.substr(0, value.length - 2);
      const fracao = this.DECIMAL_SEPARATOR + value.substr(value.length - 2, value.length);
      retorno = (inteiro + fracao).replace(/\B(?=(\d{3})+(?!\d))/g, this.THOUSANDS_SEPARATOR);
    } else if (value.length === 2) {
      const inteiro = 0;
      const fracao = this.DECIMAL_SEPARATOR + value;
      retorno = (inteiro + fracao);
    } else if (value.length === 1) {
      const inteiro = 0;
      const fracao = this.DECIMAL_SEPARATOR + 0 + value.substr(value.length - 1, value.length);
      retorno = (inteiro + fracao);
    } else {
      retorno = value;
    }

    return this.prefix + retorno;
  }

  parse(value: string): string {

    const TAM_MAX = 18;
    if (value.length > TAM_MAX) {
      value = value.substr(0, TAM_MAX);
    }

    value = value.replace(this.prefix, '');
    value = value.replace(/\./gi, '');
    value = value.replace(/\,/gi, '');
    value = value ? (+value).toString() : '';

    return value;
  }

  rawValue(value: number): number {
    if (!value) {
      return 0;
    }
    let valorTransformado = this.parse(value.toString());
    valorTransformado = this.transform(valorTransformado);
    valorTransformado = valorTransformado.replace(this.prefix, '');
    valorTransformado = valorTransformado.replace(/\./gi, '');
    valorTransformado = valorTransformado.replace(/\,/gi, '.');
    return Number(valorTransformado);
  }
}
