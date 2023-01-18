import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnexoComponent } from './anexo/anexo.component';
import { SiconvModule } from '../siconv.module';
import { MaisBrasilModule } from '../maisbrasil/mais-brasil.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContratoAnexoComponent } from './contrato-anexo/contrato-anexo.component';

@NgModule({
  imports: [
    CommonModule,
    MaisBrasilModule,
    SiconvModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [AnexoComponent, ContratoAnexoComponent],
  exports: [AnexoComponent, ContratoAnexoComponent]

})
export class AnexoModule { }
