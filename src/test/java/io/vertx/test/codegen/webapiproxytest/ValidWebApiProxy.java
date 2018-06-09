package io.vertx.test.codegen.webapiproxytest;

import io.vertx.codegen.annotations.WebApiProxyGen;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationResult;
import io.vertx.ext.web.api.RequestContext;
import io.vertx.ext.web.api.RequestParameter;
import io.vertx.test.codegen.proxytestapi.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
@WebApiProxyGen
public interface ValidWebApiProxy {

  void testA(RequestContext context, Handler<AsyncResult<OperationResult>> resultHandler);

  void testB(Integer id, JsonObject body, RequestContext context, Handler<AsyncResult<OperationResult>> resultHandler);

  void testC(Integer id, RequestParameter body, RequestContext context, Handler<AsyncResult<OperationResult>> resultHandler);

  @ProxyIgnore
  void ignored();

  @ProxyClose
  void closeIt();

}
