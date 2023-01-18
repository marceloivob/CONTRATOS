import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListagemInstrumentosContratuaisComponent } from './listagem-instrumentos-contratuais/listagem-instrumentos-contratuais.component';
import { InstrumentosContratuaisRoutingModule } from './instrumentos-contratuais-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaisBrasilModule } from 'src/app/maisbrasil/mais-brasil.module';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { SiconvModule } from 'src/app/siconv.module';
import { AnexoModule } from 'src/app/anexo/anexo.module';


@NgModule({
  declarations: [ListagemInstrumentosContratuaisComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaisBrasilModule,
    SiconvModule,
    BsDatepickerModule.forRoot(),
    InstrumentosContratuaisRoutingModule
  ]
})
export class InstrumentosContratuaisModule { }
