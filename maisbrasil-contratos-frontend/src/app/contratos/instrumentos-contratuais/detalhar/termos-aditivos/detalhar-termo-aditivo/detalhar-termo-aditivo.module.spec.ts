import { DetalharTermoAditivoModule } from './detalhar-termo-aditivo.module';

describe('DetalharTermoAditivoModule', () => {
  let detalharTermoAditivoModule: DetalharTermoAditivoModule;

  beforeEach(() => {
    detalharTermoAditivoModule = new DetalharTermoAditivoModule();
  });

  it('should create an instance', () => {
    expect(detalharTermoAditivoModule).toBeTruthy();
  });
});
