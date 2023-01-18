import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SiconvModule } from 'src/app/siconv.module';
import { SiconvSubFieldsetModule, SiconvAlertMessagesModule } from '@serpro/ngx-siconv';
import { DetalharTermoAditivoRoutingModule } from './detalhar-termo-aditivo-routing.module';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { MaisBrasilModule } from 'src/app/maisbrasil/mais-brasil.module';
import { AnexoModule } from 'src/app/anexo/anexo.module';
import { ShowChecklistTabService } from 'src/app/maisbrasil/services/checklist.service';
import { DetalharTermoAditivoComponent } from './detalhar-termo-aditivo.component';
import { EditarTermoAditivoComponent } from './editar-termo-aditivo/editar-termo-aditivo.component';
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
    DetalharTermoAditivoRoutingModule,
    AnexoModule,
    BsDatepickerModule.forRoot()
  ],
  declarations: [
    DetalharTermoAditivoComponent,
    EditarTermoAditivoComponent,
  ],
  providers: [
    ShowChecklistTabService,
    ShowTermoAditivoTabService
  ]
})
export class DetalharTermoAditivoModule { }
