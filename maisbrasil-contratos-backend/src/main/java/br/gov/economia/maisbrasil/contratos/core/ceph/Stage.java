package br.gov.economia.maisbrasil.contratos.core.ceph;

public enum Stage {
    LOCAL, DEVELOPMENT, TEST, INTEGRATION_TEST, ACCEPTANCE, PRODUCTION;

    public static Stage fromSystemStage(String systemStage) {
        if ("desenv".equalsIgnoreCase(systemStage)) {
            return DEVELOPMENT;
        } else if ("homologacao".equalsIgnoreCase(systemStage)) {
            return ACCEPTANCE;
        } else {
            return Stage.valueOf(systemStage.toUpperCase());
        }
    }
}
