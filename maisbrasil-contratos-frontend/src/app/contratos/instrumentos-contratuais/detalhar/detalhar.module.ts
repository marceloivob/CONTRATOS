import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChecklistComponent } from './checklist/checklist.component';
import { DetalharComponent } from './detalhar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SiconvModule } from 'src/app/siconv.module';
import { SiconvSubFieldsetModule, SiconvAlertMessagesModule } from '@serpro/ngx-siconv';
import { DetalharRoutingModule } from './detalhar-routing.module';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { MaisBrasilModule } from 'src/app/maisbrasil/mais-brasil.module';
import { EditarInstrumentosContratuaisComponent } from './editar-instrumentos-contratuais/editar-instrumentos-contratuais.component';
import { AnexoModule } from 'src/app/anexo/anexo.module';
import { ShowChecklistTabService } from 'src/app/maisbrasil/services/checklist.service';
import { FormEmissaoAioComponent } from './checklist/form-emissao-aio/form-emissao-aio.component';
import { TermosAditivosModule } from './termos-aditivos/termos-aditivos.module';
import { ShowTermoAditivoTabService } from 'src/app/maisbrasil/services/termoaditivo.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SiconvModule,
    SiconvSubFieldsetModule,
    SiconvAlertMessagesModule,
    MaisBrasilModule,
    DetalharRoutingModule,
    AnexoModule,
    TermosAditivosModule,
    BsDatepickerModule.forRoot()
  ],
  declarations: [
    DetalharComponent,
    EditarInstrumentosContratuaisComponent,
    ChecklistComponent,
    FormEmissaoAioComponent
  ],
  providers: [
    ShowChecklistTabService,
    ShowTermoAditivoTabService
  ]
})
export class DetalharModule { }
