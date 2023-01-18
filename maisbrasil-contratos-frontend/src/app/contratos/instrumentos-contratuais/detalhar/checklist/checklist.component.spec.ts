import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { DetalharModule } from "../detalhar.module";
import { ChecklistComponent } from "./checklist.component";
import { NgxsModule, Store } from "@ngxs/store";
import { ModelModule } from "src/app/model/model.module";
import { HttpClientModule } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { LoggedUser } from "src/app/model/user/user.actions";
import {
  ContratoModel,
  DadosChecklistModel,
  SituacaoAIO
} from "src/app/model/contrato/contrato.state.model";
import { UserMockerService } from "src/app/shared/components/user-mocker/user-mocker.service";
import { AppConfig } from "src/app/core/app.config";

describe(ChecklistComponent.name, () => {
  let component: ChecklistComponent;
  let fixture: ComponentFixture<ChecklistComponent>;
  let store: Store;
  let userMockerService: UserMockerService;

  let idProposta = 1;

  let contratoSelecionadoNaoRascunho = {
    inSituacao: "AAA" // Diferente de RAS (rascunho)
  } as ContratoModel;

  let contratoSelecionadoRascunho = {
    inSituacao: "RAS"
  } as ContratoModel;

  let dadosCheckListNaoEmitida = {
    situacaoAIO: SituacaoAIO.NAO_EMITIDA
  } as DadosChecklistModel;

  let dadosCheckListEmitida = {
    situacaoAIO: "AAA" // Diferente de NAO EMITIDA
  } as DadosChecklistModel;

  const activatedRouteParentEdicao = {
    parent: {
      firstChild: {
        snapshot: {
          url: [
            "",
            {
              path: "e"
            }
          ]
        }
      }
    }
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        NgxsModule.forRoot([], { developmentMode: false }),
        HttpClientModule,
        ModelModule,
        DetalharModule
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: activatedRouteParentEdicao
        }
      ]
    }).compileComponents();

    store = TestBed.get(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChecklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("#exibeBotaoEmitirAIO deve retornar true nos perfis permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenProponenteEditorContrato(),
      userMockerService.mockTokenAdministrador(),
      userMockerService.mockTokenConcedente(),
      userMockerService.mockTokenMandataria(),
      userMockerService.mockTokenConcedenteGCC(),
      userMockerService.mockTokenMandatariaGCIM()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoEmitirAIO).toBe(true);
    });
  });

  it("#exibeBotaoEmitirAIO deve retornar false nos perfis não permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenGuest(),
      userMockerService.mockTokenProponente(),
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoEmitirAIO).toBe(false);
    });
  });

  it("#desabilitarBotaoEmissaoAIO deve retornar true nos perfis não permitidos no período eleitoral", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    AppConfig.setInativarEmissaoAIOperiodoEleitoralUnitTest(true);

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenGuest(),
      userMockerService.mockTokenProponente(),
      userMockerService.mockTokenProponenteEditorContrato()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.desabilitarBotaoEmissaoAIO).toBe(true);
    });
  });

  it("#desabilitarBotaoEmissaoAIO deve retornar null nos perfis permitidos no período eleitoral", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    AppConfig.setInativarEmissaoAIOperiodoEleitoralUnitTest(true);

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenAdministrador(),
      userMockerService.mockTokenConcedente(),
      userMockerService.mockTokenMandataria(),
      userMockerService.mockTokenConcedenteGCC(),
      userMockerService.mockTokenMandatariaGCIM()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.desabilitarBotaoEmissaoAIO).toBe(null);
    });
  });

  it("#exibeBotaoCancelarAIO deve retornar true nos perfis permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenAdministrador(),
      userMockerService.mockTokenConcedenteGCC(),
      userMockerService.mockTokenMandatariaGCIM()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoCancelarAIO).toBe(true);
    });
  });

  it("#exibeBotaoCancelarAIO deve retornar false nos perfis não permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenProponenteEditorContrato(),
      userMockerService.mockTokenConcedente(),
      userMockerService.mockTokenGuest(),
      userMockerService.mockTokenMandataria(),
      userMockerService.mockTokenProponente()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoCancelarAIO).toBe(false);
    });
  });

  it("#exibeBotaoConcluir deve retornar true nos perfis permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenProponenteEditorContrato()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoConcluir).toBe(true);
    });
  });

  it("#exibeBotaoConcluir deve retornar false nos perfis não permitidos", () => {
    component.contratoSelecionado = contratoSelecionadoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenAdministrador(),
      userMockerService.mockTokenConcedenteGCC(),
      userMockerService.mockTokenMandatariaGCIM(),
      userMockerService.mockTokenConcedente(),
      userMockerService.mockTokenGuest(),
      userMockerService.mockTokenMandataria(),
      userMockerService.mockTokenProponente()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibeBotaoConcluir).toBe(false);
    });
  });

  it("#desabilitarBotaoEmissaoAIO deve retornar null fora do período eleitoral", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    AppConfig.setInativarEmissaoAIOperiodoEleitoralUnitTest(false);

    expect(component.desabilitarBotaoEmissaoAIO).toBe(null);
  });
  
  it("#posFixoDescricaoEmissaoAIO deve retornar string vazia fora do período eleitoral", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    AppConfig.setInativarEmissaoAIOperiodoEleitoralUnitTest(false);

    expect(component.posFixoDescricaoEmissaoAIO).toBe("");
  });

  it("#posFixoDescricaoEmissaoAIO deve retornar aviso sobre inativação da emissão do AIO durante período eleitoral", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    AppConfig.setInativarEmissaoAIOperiodoEleitoralUnitTest(true);

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [      
      userMockerService.mockTokenProponenteEditorContrato()
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.posFixoDescricaoEmissaoAIO.trim()).toBe("(Desabilitada: Período Eleitoral)");
    });
  });

  it("#exibirFormularioEmissaoAIO deve retornar true nos perfis obrigatorios", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [     
      userMockerService.mockTokenAdministrador(),
      userMockerService.mockTokenMandataria(),
      userMockerService.mockTokenConcedente(),
      userMockerService.mockTokenConcedenteGCC(),
      userMockerService.mockTokenMandatariaGCIM(),
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibirFormularioEmissaoAIO).toBe(true);
    });
  });

  it("#exibirFormularioEmissaoAIO deve retornar false nos perfis não obrigatorios", () => {
    component.contratoSelecionado = contratoSelecionadoNaoRascunho;
    component.dadosChecklist = dadosCheckListNaoEmitida;

    userMockerService = new UserMockerService(idProposta);

    let tokensUsuarios = [
      userMockerService.mockTokenProponenteEditorContrato(),
      userMockerService.mockTokenProponente(),
      userMockerService.mockTokenGuest(),
    ];

    tokensUsuarios.forEach(token => {
      store.dispatch(new LoggedUser(token));

      expect(component.exibirFormularioEmissaoAIO).toBe(false);
    });
  });
});
