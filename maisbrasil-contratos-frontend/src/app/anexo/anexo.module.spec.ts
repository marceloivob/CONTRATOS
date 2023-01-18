import { AnexoModule } from './anexo.module';

describe('AnexoModule', () => {
  let anexoModule: AnexoModule;

  beforeEach(() => {
    anexoModule = new AnexoModule();
  });

  it('should create an instance', () => {
    expect(anexoModule).toBeTruthy();
  });
});
