package io.vertx.ext.web.api;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = false)
public class OperationResult {

  public OperationResult() { }

  public OperationResult(JsonObject object) { }

  public JsonObject toJson() { return new JsonObject(); }
}
