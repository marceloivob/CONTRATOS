export enum Stage {
  LOCAL, DEVELOPMENT, TEST, INTEGRATION_TEST, ACCEPTANCE, PRODUCTION
}

interface ServerConfig {
  IDP: string;
  SICONV: string;
  INATIVAR_EMISSAO_AIO_PERIODO_ELEITORAL: boolean;
}

export class AppConfig {

    // tslint:disable-next-line:variable-name
    private static _stage = Stage.LOCAL;
    // tslint:disable-next-line:variable-name
    private static _sentryDsn =  '';

    // tslint:disable-next-line:variable-name
    private static _urlToIDPService: string;
    // tslint:disable-next-line:variable-name
    private static _urlToSICONVService: string;
    // tslint:disable-next-line:variable-name
    private static _idpAppName: string;
    // tslint:disable-next-line:variable-name
    private static _inativarEmissaoAIOperiodoEleitoral: boolean;

    // tslint:disable-next-line:variable-name
    private static _loaded = false;

    static readonly isLocalEnvironment = (window.location.hostname.search('localhost') >= 0);

    public static get endpoint(): string {
        if (AppConfig.isLocalEnvironment) {
            return '/api';
        } else {
            return window.location.origin + '/maisbrasil-contratos-backend/api';
        }
    }

    public static get stage() {
      return AppConfig._stage;
    }

    public static get sentryDsn() {
      return AppConfig._sentryDsn;
    }

    public static get urlToIDPService() {
      return AppConfig._urlToIDPService;
    }
    public static get urlToSICONVService() {
      return AppConfig._urlToSICONVService;
    }

    public static get inativarEmissaoAIOperiodoEleitoral() {
      return AppConfig._inativarEmissaoAIOperiodoEleitoral;
    }

    public static setInativarEmissaoAIOperiodoEleitoralUnitTest(value: boolean) {
      // Utilizar apenas em testes!
      AppConfig._inativarEmissaoAIOperiodoEleitoral = value;
    }

    public static get idpAppName() {
      return AppConfig._idpAppName;
    }

    public static get loaded() {
      return AppConfig._loaded;
    }

    public static loadSettings(): Promise<any> {
      return fetch(`${AppConfig.endpoint}/integrations`)
        .then( (response) => {
          return response.json();
        } )
        .then( (config: ServerConfig) => {
          AppConfig._urlToSICONVService = config.SICONV;
          AppConfig._urlToIDPService = config.IDP;
          AppConfig._inativarEmissaoAIOperiodoEleitoral = config.INATIVAR_EMISSAO_AIO_PERIODO_ELEITORAL;

          if (AppConfig.isLocalEnvironment) {
            AppConfig._idpAppName = 'CONTRATOSD';
          } else {
            AppConfig._idpAppName = 'CONTRATOS';
          }

          AppConfig._loaded = true;
        }).catch( (e) => {
          console.error('Não foi possível obter configuração do Servidor!', e);
        });
    }
}
