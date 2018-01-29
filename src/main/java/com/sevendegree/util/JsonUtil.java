package com.sevendegree.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        //所有日期格式统一成一下样式（标准） 即 "yyyy-MM-dd HH:mm:ss"
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略在json字符串中存在，但是在java对象中不存在对应属性的错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("Parse string to object error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse string to object error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObj(String str, Class<?> collectionClass, Class<?>... elementClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse string to object error", e);
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        User u1 = new User();
//        u1.setId(1);
//        u1.setEmail("hello@test.com");
//
//        String u1Json = JsonUtil.objToString(u1);
//
//        String u1JsonPretty = JsonUtil.objToStringPretty(u1);
//
//        log.info("u1Json:{}", u1Json);
//        log.info("u1JsonPretty:{}", u1JsonPretty);
//
//        u1.setPhone("123");
//        User u2 = JsonUtil.stringToObj(u1Json, User.class);
//        List<User> list = Lists.newArrayList();
//        list.add(u1);
//        list.add(u2);
//        String listJson = JsonUtil.objToStringPretty(list);
//        List<User> list1 = JsonUtil.stringToObj(listJson, new TypeReference<List<User>>(){});
//        List<User> list1_0 = JsonUtil.stringToObj(listJson, list.getClass());
//
//        List<User> list2 = JsonUtil.stringToObj(listJson, list.getClass(), User.class);
//    }
}
