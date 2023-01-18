import { HttpClientModule } from "@angular/common/http";
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { NgxsModule } from "@ngxs/store";
import { EmissaoAIORequestModel } from "src/app/model/contrato/contrato.state.model";
import { ModelModule } from "src/app/model/model.module";
import { DetalharModule } from "../../detalhar.module";
import { FormEmissaoAioComponent } from "./form-emissao-aio.component";

describe(FormEmissaoAioComponent.name, () => {
  let component: FormEmissaoAioComponent;
  let fixture: ComponentFixture<FormEmissaoAioComponent>;
  let requestEmissaoAIO: EmissaoAIORequestModel;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        NgxsModule.forRoot([], { developmentMode: false }),
        HttpClientModule,
        ModelModule,
        DetalharModule
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    requestEmissaoAIO = new EmissaoAIORequestModel();

    fixture = TestBed.createComponent(FormEmissaoAioComponent);
    component = fixture.componentInstance;
    component.requestEmissaoAIO = requestEmissaoAIO;
    fixture.detectChanges();
  });

  it("should create", () => {    
    expect(component).toBeTruthy();
  });
});
