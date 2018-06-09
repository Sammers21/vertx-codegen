package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a Java interface type to be processed for generating a Java proxy that can be connected to the
 * original API via Vert.x event bus.
 *
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebApiProxyGen { }
