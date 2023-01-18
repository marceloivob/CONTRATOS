// Classe para realizar o Mock do Usuarios nos testes
// NAO utilizar em producao
export class UserMockerService {
  // Header compativel com o https://jwt.io
  private header = {
    alg: "HS256",
    typ: "JWT"
  };

  private assinatura = ""; // Assinatura nao eh validada por isso pode ser vazia

  private idProposta: number;

  constructor(idProposta: number) {
    this.idProposta = idProposta;
  }  

  public mockTokenGuest(): string {
    let body = {
      nome: "Guest",
      cpf: "guest",
      idProposta: this.idProposta,
      tipoEnte: "GUEST", // Perfil
      roles: ["GUEST"]
    };

    return this.createJwtToken(body);
  }

  public mockTokenAdministrador(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "CONCEDENTE", // Perfil
      roles: ["ADMINISTRADOR_SISTEMA", "ADMINISTRADOR_SISTEMA_ORGAO_EXTERNO"]
    };

    return this.createJwtToken(body);
  }

  public mockTokenConcedente(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "CONCEDENTE", // Perfil
      roles: []
    };

    return this.createJwtToken(body);
  }

  public mockTokenConcedenteGCC(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "CONCEDENTE", // Perfil
      roles: [
        "GESTOR_CONVENIO_CONCEDENTE"
      ]
    };

    return this.createJwtToken(body);
  }

  public mockTokenProponente(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "PROPONENTE", // Perfil
      roles: []
    };

    return this.createJwtToken(body);
  }

  public mockTokenProponenteEditorContrato(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "PROPONENTE", // Perfil
      roles: [
        "FISCAL_CONVENENTE",
        "GESTOR_CONVENIO_CONVENENTE",
        "GESTOR_FINANCEIRO_CONVENENTE",
        "OPERADOR_FINANCEIRO_CONVENENTE"
      ]
    };

    return this.createJwtToken(body);
  }

  public mockTokenMandataria(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "MANDATARIA", // Perfil
      roles: []
    };

    return this.createJwtToken(body);
  }

  public mockTokenMandatariaGCIM(): string {
    let body = {
      nome: "Teste",
      cpf: "11111111111",
      idProposta: this.idProposta,
      tipoEnte: "MANDATARIA", // Perfil
      roles: [
        "GESTOR_CONVENIO_INSTITUICAO_MANDATARIA"
      ]
    };

    return this.createJwtToken(body);
  }

  private createJwtToken(body: any): string {
    return (
      btoa(JSON.stringify(this.header)) +
      "." +
      btoa(JSON.stringify(body)) +
      "." +
      this.assinatura
    );
  }
}
