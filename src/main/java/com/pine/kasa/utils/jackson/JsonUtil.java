package com.pine.kasa.utils.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.pine.kasa.entity.primary.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: pine
 * @CreateDate: 2018-09-15 15:26
 */
@SuppressWarnings("unchecked")
public class JsonUtil {
    private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    static{
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //允许对象忽略json中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

//        SerializerFeature[] features = {SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullNumberAsZero/*,SerializerFeature.WriteNullListAsEmpty 空list-> []*/};
//        FastJsonSerializerFeatureCompatibleForJackson jacksonConfig = new FastJsonSerializerFeatureCompatibleForJackson(features);
//        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(jacksonConfig));

    }


    /**
     * javaBean、列表数组转换为json字符串
     */
    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }

    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     */
    public static String obj2jsonIgnoreNull(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (obj instanceof String) {
                return (String) obj;
            } else {
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                return objectMapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }


    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }

    /**
     * json 转JavaBean
     */
    public static <T> T string2Obj(String str, Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }



    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, Object> json2map(String jsonString) {
        if (jsonString == null) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            log.warn("Parse Object to Map error", e);
            return null;
        }
    }

    /**
     * 深度转换json成map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> json2mapDeeply(String json) throws Exception {
        return json2MapRecursion(json, objectMapper);
    }

    /**
     * 把json解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param json
     * @param mapper 解析工具
     * @return
     * @throws Exception
     */
    private static List<Object> json2ListRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        List<Object> list = mapper.readValue(json, List.class);

        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = json2ListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = json2MapRecursion(str, mapper);
                }
            }
        }

        return list;
    }

    /**
     * 把json解析成map，如果map内部的value存在jsonString，继续解析
     *
     * @param json
     * @param mapper
     * @return
     * @throws Exception
     */
    private static Map<String, Object> json2MapRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        Map<String, Object> map = mapper.readValue(json, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);

                if (str.startsWith("[")) {
                    List<?> list = json2ListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = json2MapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }

        return map;
    }


    /**
     * 与javaBean json数组字符串转换为列表
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws Exception {

        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
        return lst;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     * map  转JavaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * map 转json
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * map  转JavaBean
     */
    public static <T> T obj2pojo(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }


    public static void main(String[] args) throws Exception{
        List<User> stringList = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            User user = new User();
            user.setId(a);
            user.setPassword("passwd" + a);
            user.setNickname("username" + a);
            user.setBirthday(new Date());
            user.setCreateTime(new Date());
            stringList.add(user);

        }

        String str = "[{\"id\":0,\"nickname\":\"username0\",\"password\":\"passwd0\"},{\"id\":1,\"nickname\":\"username1\",\"password\":\"passwd1\"},{\"id\":2,\"nickname\":\"username2\",\"password\":\"passwd2\"},{\"id\":3,\"nickname\":\"username3\",\"password\":\"passwd3\"},{\"id\":4,\"nickname\":\"username4\",\"password\":\"passwd4\"},{\"id\":5,\"nickname\":\"username5\",\"password\":\"passwd5\"},{\"id\":6,\"nickname\":\"username6\",\"password\":\"passwd6\"},{\"id\":7,\"nickname\":\"username7\",\"password\":\"passwd7\"},{\"id\":8,\"nickname\":\"username8\",\"password\":\"passwd8\"},{\"id\":9,\"nickname\":\"username9\",\"password\":\"passwd9\"}]";

        String mapStr = "{\"id\":0,\"nickname\":\"username0\",\"password\":\"passwd0\"}";

        System.out.println(obj2String(stringList));
        System.out.println("----------");
        System.out.println(obj2StringPretty(stringList));
        System.out.println(obj2jsonIgnoreNull(stringList));

        System.out.println("----------");
        System.out.println(json2map(mapStr));

        List<User> users = json2list(str,User.class);
        System.out.println(users);


        Map<String,Object> map = Maps.newHashMap();
        map.put("id",12);
        map.put("nickname","aa");
        map.put("password","vv");
        User user = map2pojo(map, User.class);

        System.out.println(user);
    }
}
