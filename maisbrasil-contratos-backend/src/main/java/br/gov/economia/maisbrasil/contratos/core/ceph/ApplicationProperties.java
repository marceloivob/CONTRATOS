package br.gov.economia.maisbrasil.contratos.core.ceph;

public class ApplicationProperties {

    private ApplicationProperties() { }

    public static final String PUBLIC_KEY_JWT = System.getProperty("publickey.jwt");
    public static final String LIQUIBASE_CONTEXTS = System.getProperty("liquibase.context");
    public static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String MONTH_YEAR_FORMAT = "MM/yyyy";

    public static final Stage STAGE = Stage
            .fromSystemStage(System.getProperty("thorntail.project.stage", "PRODUCTION"));

}
