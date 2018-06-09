package io.vertx.test.codegen.webapiproxytest;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.WebApiProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.OperationResult;
import io.vertx.ext.web.api.RequestContext;

/**
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
@WebApiProxyGen
public interface MissingContext {

  void someMethod(Integer id, Handler<AsyncResult<OperationResult>> resultHandler);
}
