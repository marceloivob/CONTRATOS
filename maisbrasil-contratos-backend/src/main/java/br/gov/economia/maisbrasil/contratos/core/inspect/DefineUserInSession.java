package br.gov.economia.maisbrasil.contratos.core.inspect;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jdbi.v3.sqlobject.SqlMethodDecoratingAnnotation;

@SqlMethodDecoratingAnnotation(DefineUserInSessionDecorator.class)
@Retention(RUNTIME)
@Target(METHOD)
public @interface DefineUserInSession {

}
