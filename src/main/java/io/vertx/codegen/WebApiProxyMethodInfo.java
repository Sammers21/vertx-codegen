package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="https://github.com/slinkydeveloper">Francesco Guardiani</a>
 */
public class WebApiProxyMethodInfo extends ProxyMethodInfo {

  final List<ParamInfo> paramsToExtract;
  final String requestContextName;

  public WebApiProxyMethodInfo(Set<ClassTypeInfo> ownerTypes, String name, MethodKind kind, TypeInfo returnType, Text returnDescription, boolean fluent, boolean cacheReturn, List<ParamInfo> params, String comment, Doc doc, boolean staticMethod, boolean defaultMethod, List<TypeParamInfo.Method> typeParams, boolean proxyIgnore, boolean proxyClose, boolean deprecated) {
    super(ownerTypes, name, kind, returnType, returnDescription, fluent, cacheReturn, params, comment, doc, staticMethod, defaultMethod, typeParams, proxyIgnore, proxyClose, deprecated);
    paramsToExtract = params.subList(0, params.size() - 2);
    requestContextName = params.get(params.size() - 2).name;
  }

  public WebApiProxyMethodInfo(ProxyMethodInfo info) {
    this(
      info.ownerTypes,
      info.name,
      info.kind,
      info.returnType,
      info.returnDescription,
      info.fluent,
      info.cacheReturn,
      info.params,
      info.comment,
      info.doc,
      info.staticMethod,
      info.defaultMethod,
      info.typeParams,
      info.isProxyIgnore(),
      info.isProxyClose(),
      info.deprecated
    );
  }

  public List<ParamInfo> getParamsToExtract() {
    return paramsToExtract;
  }

  public String getRequestContextName() {
    return requestContextName;
  }
}
