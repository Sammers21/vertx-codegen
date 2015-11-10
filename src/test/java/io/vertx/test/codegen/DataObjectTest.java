package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.PropertyKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.TypeReflectionFactory;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.doc.Doc;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.DataObjectInterface;
import io.vertx.test.codegen.testapi.DataObjectWithNoCopyConstructor;
import io.vertx.test.codegen.testapi.DataObjectWithNoDefaultConstructor;
import io.vertx.test.codegen.testapi.DataObjectWithNoJsonObjectConstructor;
import io.vertx.test.codegen.testdataobject.Abstract;
import io.vertx.test.codegen.testdataobject.AbstractCommentedProperty;
import io.vertx.test.codegen.testdataobject.AbstractInheritsAbstract;
import io.vertx.test.codegen.testdataobject.AbstractUncommentedProperty;
import io.vertx.test.codegen.testdataobject.AdderNormalizationRules;
import io.vertx.test.codegen.testdataobject.AdderWithNestedDataObject;
import io.vertx.test.codegen.testdataobject.ApiObject;
import io.vertx.test.codegen.testdataobject.ConverterDataObject;
import io.vertx.test.codegen.testdataobject.DataObjectWithObjectProperty;
import io.vertx.test.codegen.testdataobject.Enumerated;
import io.vertx.test.codegen.testdataobject.InheritingConverterDataObject;
import io.vertx.test.codegen.testdataobject.NoConverterDataObject;
import io.vertx.test.codegen.testdataobject.PropertyAdders;
import io.vertx.test.codegen.testdataobject.PropertyGettersAdders;
import io.vertx.test.codegen.testdataobject.PropertyGettersSetters;
import io.vertx.test.codegen.testdataobject.PropertyListGettersSetters;
import io.vertx.test.codegen.testdataobject.PropertyMapGettersSetters;
import io.vertx.test.codegen.testdataobject.PropertyMapSetters;
import io.vertx.test.codegen.testdataobject.PropertySetters;
import io.vertx.test.codegen.testdataobject.CommentedDataObject;
import io.vertx.test.codegen.testdataobject.CommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyInheritedFromCommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyOverridesCommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyOverridesUncommentedProperty;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromAbstractDataObject;
import io.vertx.test.codegen.testdataobject.DataObjectInterfaceWithIgnoredProperty;
import io.vertx.test.codegen.testdataobject.ToJsonDataObject;
import io.vertx.test.codegen.testdataobject.UncommentedPropertyOverridesSuperCommentedProperty;
import io.vertx.test.codegen.testdataobject.Concrete;
import io.vertx.test.codegen.testdataobject.ConcreteInheritsAbstract;
import io.vertx.test.codegen.testdataobject.ConcreteExtendsConcrete;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsFromNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsFromDataObject;
import io.vertx.test.codegen.testdataobject.EmptyDataObject;
import io.vertx.test.codegen.testdataobject.IgnoreMethods;
import io.vertx.test.codegen.testdataobject.ImportedNested;
import io.vertx.test.codegen.testdataobject.ImportedSubinterface;
import io.vertx.test.codegen.testdataobject.JsonObjectAdder;
import io.vertx.test.codegen.testdataobject.JsonObjectSetter;
import io.vertx.test.codegen.testdataobject.PropertyListSetters;
import io.vertx.test.codegen.testdataobject.UncommentedProperty;
import io.vertx.test.codegen.testdataobject.Parameterized;
import io.vertx.test.codegen.testdataobject.SetterNormalizationRules;
import io.vertx.test.codegen.testdataobject.SetterWithNestedDataObject;
import io.vertx.test.codegen.testdataobject.SetterWithNonFluentReturnType;
import io.vertx.test.codegen.testdataobject.UncommentedPropertyOverridesAncestorSuperCommentedProperty;
import io.vertx.test.codegen.testdataobject.imported.Imported;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTest {

  @Test
  public void testDataObjectsWithNoDefaultConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoDefaultConstructor.class);
  }

  @Test
  public void testDataObjectWithNoCopyConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoCopyConstructor.class);
  }

  @Test
  public void testDataObjectWithNoJsonObjectConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoJsonObjectConstructor.class);
  }

  @Test
  public void testDataObjectInterface() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(DataObjectInterface.class);
    assertNotNull(model);
    assertFalse(model.isClass());
  }

  @Test
  public void testEmptyDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(EmptyDataObject.class);
    assertNotNull(model);
    assertTrue(model.isClass());
    assertFalse(model.getGenerateConverter());
    assertFalse(model.getInheritConverter());
  }

  @Test
  public void testParameterizedDataObject() throws Exception {
    assertInvalidDataObject(Parameterized.class);
  }

  @Test
  public void testSetterWithNonFluentReturnType() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterWithNonFluentReturnType.class);
    assertNotNull(model);
    assertEquals(2, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testObjectProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(DataObjectWithObjectProperty.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testPropertySetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertySetters.class);
    assertNotNull(model);
    assertEquals(13, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", null, TypeReflectionFactory.create(int.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", null, TypeReflectionFactory.create(long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonArray"), "jsonArray", "setJsonArray", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("enumerated"), "enumerated", "setEnumerated", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testSetterNormalizationRules() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("ha"), "ha", "setHA", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("haGroup"), "haGroup", "setHAGroup", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("group"), "group", "setGroup", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testPropertyListSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyListSetters.class);
    assertNotNull(model);
    assertEquals(11, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", "setExtraClassPath", null, TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", "setStrings", null, TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "setBoxedIntegers", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "setBoxedBooleans", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "setBoxedLongs", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "setApiObjects", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "setDataObjects", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "setToJsonDataObjects", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "setJsonObjects", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "setJsonArrays", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "setEnumerateds", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testPropertyListGettersSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyListGettersSetters.class);
    assertNotNull(model);
    assertEquals(11, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", "setExtraClassPath", "getExtraClassPath", TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", "setStrings", "getStrings", TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "setBoxedIntegers", "getBoxedIntegers", TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "setBoxedBooleans", "getBoxedBooleans", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "setBoxedLongs", "getBoxedLongs", TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "setApiObjects", "getApiObjects", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "setDataObjects", "getDataObjects", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "setToJsonDataObjects", "getToJsonDataObjects", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "setJsonObjects", "getJsonObjects", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "setJsonArrays", "getJsonArrays", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "setEnumerateds", "getEnumerateds", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testPropertyMapGettersSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyMapGettersSetters.class);
    assertNotNull(model);
    assertEquals(11, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("stringMap"), "stringMap", "setStringMap", "getStringMap", TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegerMap"), "boxedIntegerMap", "setBoxedIntegerMap", "getBoxedIntegerMap", TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleanMap"), "boxedBooleanMap", "setBoxedBooleanMap", "getBoxedBooleanMap", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongMap"), "boxedLongMap", "setBoxedLongMap", "getBoxedLongMap", TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjectMap"), "apiObjectMap", "setApiObjectMap", "getApiObjectMap", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjectMap"), "dataObjectMap", "setDataObjectMap", "getDataObjectMap", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("toJsonDataObjectMap"), "toJsonDataObjectMap", "setToJsonDataObjectMap", "getToJsonDataObjectMap", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjectMap"), "jsonObjectMap", "setJsonObjectMap", "getJsonObjectMap", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrayMap"), "jsonArrayMap", "setJsonArrayMap", "getJsonArrayMap", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumeratedMap"), "enumeratedMap", "setEnumeratedMap", "getEnumeratedMap", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objectMap"), "objectMap", "setObjectMap", "getObjectMap", TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyMapSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyMapSetters.class);
    assertNotNull(model);
    assertEquals(11, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("stringMap"), "stringMap", "setStringMap", null, TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegerMap"), "boxedIntegerMap", "setBoxedIntegerMap", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleanMap"), "boxedBooleanMap", "setBoxedBooleanMap", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongMap"), "boxedLongMap", "setBoxedLongMap", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjectMap"), "apiObjectMap", "setApiObjectMap", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjectMap"), "dataObjectMap", "setDataObjectMap", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("toJsonDataObjectMap"), "toJsonDataObjectMap", "setToJsonDataObjectMap", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjectMap"), "jsonObjectMap", "setJsonObjectMap", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrayMap"), "jsonArrayMap", "setJsonArrayMap", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumeratedMap"), "enumeratedMap", "setEnumeratedMap", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objectMap"), "objectMap", "setObjectMap", null, TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyGettersSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyGettersSetters.class);
    assertNotNull(model);
    assertEquals(13, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", "getString", TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", "getBoxedInteger", TypeReflectionFactory.create(Integer.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", "getPrimitiveInteger", TypeReflectionFactory.create(int.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", "isBoxedBoolean", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", "isPrimitiveBoolean", TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", "getBoxedLong", TypeReflectionFactory.create(Long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", "getPrimitiveLong", TypeReflectionFactory.create(long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", "getApiObject", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", "getDataObject", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", "getToJsonDataObject", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", "getJsonObject", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonArray"), "jsonArray", "setJsonArray", "getJsonArray", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("enumerated"), "enumerated", "setEnumerated", "getEnumerated", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testJsonObjectSetter() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(JsonObjectSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testPropertyAdders() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyAdders.class);
    assertNotNull(model);
    assertEquals(13, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", "addString", null, TypeReflectionFactory.create(String.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "addBoxedInteger", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", "addPrimitiveInteger", null, TypeReflectionFactory.create(int.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "addBoxedBoolean", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", "addPrimitiveBoolean", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "addBoxedLong", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", "addPrimitiveLong", null, TypeReflectionFactory.create(long.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "addApiObject", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "addDataObject", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST_ADD, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "addToJsonDataObject", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "addJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "addJsonArray", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "addEnumerated", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST_ADD, true);
  }

  @Test
  public void testPropertyGettersAdders() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyGettersAdders.class);
    assertNotNull(model);
    assertEquals(13, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", "addString", "getStrings", TypeReflectionFactory.create(String.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "addBoxedInteger", "getBoxedIntegers", TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", "addPrimitiveInteger", "getPrimitiveIntegers", TypeReflectionFactory.create(int.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "addBoxedBoolean", "getBoxedBooleans", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", "addPrimitiveBoolean", "getPrimitiveBooleans", TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "addBoxedLong", "getBoxedLongs", TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", "addPrimitiveLong", "getPrimitiveLongs", TypeReflectionFactory.create(long.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "addApiObject", "getApiObjects", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "addDataObject", "getDataObjects", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST_ADD, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "addToJsonDataObject", "getToJsonDataObjects", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "addJsonObject", "getJsonObjects", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "addJsonArray", "getJsonArrays", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "addEnumerated", "getEnumerateds", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST_ADD, true);
  }

  @Test
  public void testAdderNormalizationRules() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AdderNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("urls"), "urls", "addURL", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("urlLocators"), "urlLocators", "addURLLocator", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST_ADD, true);
    assertProperty(model.getPropertyMap().get("locators"), "locators", "addLocator", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST_ADD, true);
  }

  @Test
  public void testJsonObjectAdder() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(JsonObjectAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "addJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST_ADD, true);
  }

  @Test
  public void testNestedDataObjectSetter() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nested"), "nested", "setNested", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
  }

  @Test
  public void testNestedDataObjectAdder() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AdderWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nesteds"), "nesteds", "addNested", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST_ADD, false);
  }

  @Test
  public void testIgnoreMethods() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(IgnoreMethods.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteInheritsConcrete() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteExtendsConcrete.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Concrete.class)), model.getSuperTypes());
    assertEquals(TypeReflectionFactory.create(Concrete.class), model.getSuperType());
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getAbstractSuperTypes());
  }

  @Test
  public void testConcreteImplementsAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteInheritsAbstract.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getSuperTypes());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getAbstractSuperTypes());
    assertNull(model.getSuperType());
  }

  @Test
  public void testConcreteImplementsNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteImplementsFromNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteImplementsFromDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testConcreteOverridesFromDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
  }

  @Test
  public void testConcreteOverridesFromNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteOverridesFromAbstractDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromAbstractDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("inheritedProperty"), "inheritedProperty", "setInheritedProperty", null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("overriddenProperty"), "overriddenProperty", "setOverriddenProperty", null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("abstractProperty"), "abstractProperty", "setAbstractProperty", null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testAbstractInheritsConcrete() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstractInheritsAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AbstractInheritsAbstract.class);
    assertNotNull(model);
    assertFalse(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getSuperTypes());
  }

  @Test
  public void testImportedSubinterface() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ImportedSubinterface.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testImportedNested() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ImportedNested.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testCommentedDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedDataObject.class);
    Doc doc = model.getDoc();
    assertEquals(" The data object comment.\n", doc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    assertNull(propertyInfo.getDoc());
  }

  @Test
  public void testCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyInheritedFromCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyInheritedFromCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedPropertyOverridesSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesAncestorCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedPropertyOverridesAncestorSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyOverridesCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesUncommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyOverridesUncommentedProperty.class, AbstractUncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testDataObjectWithIgnoredProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(DataObjectInterfaceWithIgnoredProperty.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConverterDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConverterDataObject.class);
    assertTrue(model.getGenerateConverter());
  }

  @Test
  public void testNoConverterDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(NoConverterDataObject.class);
    assertFalse(model.getGenerateConverter());
  }

  @Test
  public void testInheritedConverterDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(InheritingConverterDataObject.class);
    assertTrue(model.getInheritConverter());
  }

  @Test
  public void testToJson() throws Exception {
    assertTrue(new Generator().generateDataObject(ToJsonDataObject.class).isJsonifiable());
    assertFalse(new Generator().generateDataObject(EmptyDataObject.class).isJsonifiable());
  }

  private static void assertProperty(
      PropertyInfo property,
      String expectedName,
      String expectedMutator,
      String expectedReader,
      TypeInfo expectedType,
      boolean expectedDeclared,
      PropertyKind expectedKind,
      boolean expectedJsonifiable) {
    assertNotNull(property);
    assertEquals("Was expecting property to have be declared=" + expectedDeclared, expectedDeclared, property.isDeclared());
    assertEquals(expectedMutator, property.getWriterMethod());
    assertEquals(expectedReader, property.getReaderMethod());
    assertEquals(expectedName, property.getName());
    assertEquals(expectedType, property.getType());
    assertEquals(expectedKind, property.getKind());
    assertEquals(expectedJsonifiable, property.isJsonifiable());
  }

  private void assertInvalidDataObject(Class<?> dataObjectClass) throws Exception {
    try {
      new Generator().generateDataObject(dataObjectClass);
      fail("Was expecting " + dataObjectClass.getName() + " to fail");
    } catch (GenException ignore) {
    }
  }
}
