import { MaisBrasilModule } from './mais-brasil.module';

describe('SiconvModule', () => {
  let siconvModule: MaisBrasilModule;

  beforeEach(() => {
    siconvModule = new MaisBrasilModule();
  });

  it('should create an instance', () => {
    expect(siconvModule).toBeTruthy();
  });
});
