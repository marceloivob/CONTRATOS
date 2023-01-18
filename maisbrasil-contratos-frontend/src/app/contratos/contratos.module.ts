import { NgModule } from '@angular/core';
import { ContratosRoutingModule } from './contratos-routing.module';
import { CommonModule } from '@angular/common';
import { ContratosComponent } from './contratos.component';
import { MaisBrasilModule } from '../maisbrasil/mais-brasil.module';
import { ModalModule, AlertModule } from 'ngx-bootstrap';
import { SiconvModule } from '../siconv.module';
import { AnexoComponent } from '../anexo/anexo/anexo.component';
import { AppModule } from '../app.module';
import { AnexoModule } from '../anexo/anexo.module';
import { HistoricoComponent } from './historico/historico.component';




@NgModule({
    imports: [
        AnexoModule,
        CommonModule,
        ContratosRoutingModule,
        MaisBrasilModule,
        SiconvModule,
        AlertModule.forRoot(),
        ModalModule.forRoot()
    ],
    declarations: [
        ContratosComponent,
        HistoricoComponent
    ]
})
export class ContratosModule {}
