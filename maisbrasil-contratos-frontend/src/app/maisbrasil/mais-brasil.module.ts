import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { NgSelectModule } from '@ng-select/ng-select';
import { CurrencyDirective } from './directives/currency.directive';
import { CurrencyHelperService } from './services/currency-helper.service';
import { CnpjPipe } from './pipes/cnpj.pipe';
import { CpfPipe } from './pipes/cpf.pipe';
import { InscricaoGenericaPipe } from './pipes/inscricaogenerica.pipe';
import { FornecedorPipe } from './pipes/fornecedor.pipe';
import { LicitacaoPipe } from './pipes/licitacao.pipe';
import { ValueIdPipe } from './pipes/valueid.pipe';
import { MenuComponent } from './menu/menu.component';
import { SiconvPipesModule } from '@serpro/ngx-siconv';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
    SiconvPipesModule,
    NgxMaskModule.forRoot()
  ],
  declarations: [
    CurrencyDirective,
    CnpjPipe,
    CpfPipe,
    InscricaoGenericaPipe,
    FornecedorPipe,
    LicitacaoPipe,
    ValueIdPipe,
    MenuComponent
  ],
  providers: [CurrencyHelperService, CnpjPipe, CpfPipe, InscricaoGenericaPipe, FornecedorPipe, LicitacaoPipe, ValueIdPipe],
  exports: [
    NgxMaskModule,
    NgSelectModule,
    CurrencyDirective,
    CnpjPipe,
    FornecedorPipe,
    LicitacaoPipe,
    ValueIdPipe,
    MenuComponent
  ]
})
export class MaisBrasilModule { }
