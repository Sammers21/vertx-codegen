package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.test.codegen.proxytestapi.*;
import io.vertx.test.codegen.webapiproxytest.MissingContext;
import io.vertx.test.codegen.webapiproxytest.MissingHandler;
import io.vertx.test.codegen.webapiproxytest.ValidWebApiProxy;
import io.vertx.test.codegen.webapiproxytest.WrongHandler;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
public class WebApiProxyTest {

  @Test
  public void testValid() throws Exception {
    WebApiProxyModel model = new GeneratorHelper().generateWebApiProxyModel(ValidWebApiProxy.class);
    assertEquals(ValidWebApiProxy.class.getName(), model.getIfaceFQCN());
    assertEquals(ValidWebApiProxy.class.getSimpleName(), model.getIfaceSimpleName());
  }

  @Test
  public void testMissingContext() throws Exception {
    try {
      new GeneratorHelper().generateWebApiProxyModel(MissingContext.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testWrongHandler() throws Exception {
    try {
      new GeneratorHelper().generateWebApiProxyModel(WrongHandler.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testMissingHandler() throws Exception {
    try {
      new GeneratorHelper().generateWebApiProxyModel(MissingHandler.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }
}
