package io.vertx.test.codegen.webapiproxytest;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.annotations.WebApiProxyGen;
import io.vertx.ext.web.api.RequestContext;

/**
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
@WebApiProxyGen
public interface MissingHandler {

  void someMethod(Integer id, RequestContext context);
}
