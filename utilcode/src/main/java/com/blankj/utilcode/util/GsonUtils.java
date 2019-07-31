package com.blankj.utilcode.util;

import android.support.v4.util.SimpleArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/05
 *     desc  : utils about gson
 * </pre>
 */
public final class GsonUtils {

    private static final Gson GSON = createGson(true);

//    private static final Gson GSON_NO_NULLS = createGson(false);

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Gets pre-configured {@link Gson} instance.
     *
     * @return {@link Gson} instance.
     */
    public static Gson getGson() {
        return getGson(true);
    }

    /**
     * Gets pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    public static Gson getGson(final boolean serializeNulls) {
        // return serializeNulls ? GSON_NO_NULLS : GSON;
        return GSON;
    }

    /**
     * Serializes an object into json.
     *
     * @param object the object to serialize.
     * @return object serialized into json.
     */
    public static String toJson(final Object object) {
        return toJson(object, true);
    }

    /**
     * Serializes an object into json.
     *
     * @param object       the object to serialize.
     * @param includeNulls determines if nulls will be included.
     * @return object serialized into json.
     */
    public static String toJson(final Object object, final boolean includeNulls) {
        // return includeNulls ? GSON.toJson(object) : GSON_NO_NULLS.toJson(object);
        return GSON.toJson(object);
    }


    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Class<T> type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Type type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Return the type of {@link List} with the {@code type}.
     *
     * @param type The type.
     * @return the type of {@link List} with the {@code type}
     */
    public static Type getListType(final Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    /**
     * Return the type of {@link Set} with the {@code type}.
     *
     * @param type The type.
     * @return the type of {@link Set} with the {@code type}
     */
    public static Type getSetType(final Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    /**
     * Return the type of map with the {@code keyType} and {@code valueType}.
     *
     * @param keyType   The type of key.
     * @param valueType The type of value.
     * @return the type of map with the {@code keyType} and {@code valueType}
     */
    public static Type getMapType(final Type keyType, final Type valueType) {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    /**
     * Return the type of array with the {@code type}.
     *
     * @param type The type.
     * @return the type of map with the {@code type}
     */
    public static Type getArrayType(final Type type) {
        return TypeToken.getArray(type).getType();
    }

    /**
     * Return the type of {@code rawType} with the {@code typeArguments}.
     *
     * @param rawType       The raw type.
     * @param typeArguments The type of arguments.
     * @return the type of map with the {@code type}
     */
    public static Type getType(final Type rawType, final Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }

    /**
     * 根据json转成list列表
     */
    public static <T> ArrayList<T> getListFromJson(String json, Class<T> cls) {
        return GSON.fromJson(json, getListType(cls));
    }

    /**
     * 根据list列表 转成 json
     */
    public static String getJsonForList(List<Object> dataList) {
        return GSON.toJson(dataList, new TypeToken<List>() {
        }.getType());
    }

    public static String getJsonForMap(SimpleArrayMap<String, Object> params) {
        JsonObject jsonObj = new JsonObject();
        for (int i = 0; i < params.size(); i++) {
            String key = params.keyAt(i);
            Object value = params.get(key);
            if (value != null) {
                jsonObj.addProperty(key, value.toString());
            }
        }
        return GSON.toJson(jsonObj);
    }


    /**
     * 如果map 里包含Array,通过该方法转换成json
     */
    public static String getJsonForArrayMap(SimpleArrayMap<String, Object> params) {
        JsonObject jsonObj = new JsonObject();
        for (int i = 0; i < params.size(); i++) {
            String key = params.keyAt(i);
            Object value = params.get(key);
            if (value instanceof List) {
                JsonArray ja = new JsonArray();
                for (Object item : ((List) value)) {
                    ja.add(item.toString());
                }
                jsonObj.add(key, ja);
            } else {
                if (value != null) {
                    jsonObj.addProperty(key, value.toString());
                }
            }
        }
        return GSON.toJson(jsonObj);
    }


    /**
     * Create a pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    private static Gson createGson(final boolean serializeNulls) {
        final GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder.create();
    }


}
