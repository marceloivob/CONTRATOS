import { Component, Input } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Store } from "@ngxs/store";
import { BaseComponent } from "src/app/maisbrasil/util/base.component";
import { EmissaoAIORequestModel } from "src/app/model/contrato/contrato.state.model";

@Component({
  selector: "app-form-emissao-aio",
  templateUrl: "./form-emissao-aio.component.html",
  styleUrls: []
})
export class FormEmissaoAioComponent extends BaseComponent {
  @Input()
  public requestEmissaoAIO: EmissaoAIORequestModel;

  MAX_TX_JUSTIFICATIVA = 1500;

  formEmissaoAIO: FormGroup;
  caracteresRestantesTxJustificativa: number;  
  submitted: false;

  constructor(protected store: Store, private formBuilder: FormBuilder) {
    super(store);
  }

  init(): void {    
    this.requestEmissaoAIO.dataEmissaoAIO = new Date();

    // 772989: SICONV-InstrumentosContratuais-RN-FormularioEmissaoAIO
    this.formEmissaoAIO = this.formBuilder.group({
      dataEmissaoAIO: [
        { value: Date.now(), disabled: false },
        [Validators.required]
      ],
      justificativa: [
        { value: "", disabled: false },
        [Validators.required, Validators.maxLength(this.MAX_TX_JUSTIFICATIVA)]
      ]
    });

    this.calcularCaracteresRestantesTxJustificativa();
  }

  showError(fieldName: string) {
    const field = this.formEmissaoAIO.get(fieldName);
    return !field.valid && (!field.pristine || this.submitted);
  }

  calcularCaracteresRestantesTxJustificativa() {
    const formValues = this.formEmissaoAIO.value;
    this.caracteresRestantesTxJustificativa =
      this.MAX_TX_JUSTIFICATIVA -
      (formValues.justificativa ? formValues.justificativa.length : 0);
  }
}
