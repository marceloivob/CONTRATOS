import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaisBrasilModule } from 'src/app/maisbrasil/mais-brasil.module';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { SiconvModule } from 'src/app/siconv.module';
import { ListagemTermosAditivosComponent } from './listagem-termos-aditivos/listagem-termos-aditivos.component';
import { TermosAditivosRoutingModule } from './termos-aditivos-routing.module';


@NgModule({
  declarations: [ListagemTermosAditivosComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaisBrasilModule,
    SiconvModule,
    BsDatepickerModule.forRoot(),
    TermosAditivosRoutingModule
  ]
})
export class TermosAditivosModule { }
