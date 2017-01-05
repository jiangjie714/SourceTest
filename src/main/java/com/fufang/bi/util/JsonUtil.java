package com.fufang.bi.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/***
 * Create date:2015-01-23
 * <p>
 * JSON转换工具类
 * </p>
 * <hr>
 * Edit List:
 * <hr>
 * 
 * @author 马跃
 * 
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 封装返回消息
	 * 
	 * @param state
	 *            消息状态码
	 * @param msg
	 *            消息内容
	 * @param obj
	 *            对象数据
	 * @param list
	 *            集合数据
	 * @return
	 */
	public static String getJsonBeanStr(Integer state, String msg, Object obj,
			List<?> list) {
		JsonBean json = new JsonBean();
		json.setState(state);
		json.setMsg(msg);
		json.setObj(obj);
		json.setList(list);
		return JsonUtil.toJson(json);
	}
	/**
	 * 传入list 返回String []  方便在返回集时 添加公用信息 公用信息在rst 中map添加
	 * rst : { 
        "logo": "/pub/yx/yflogo/logo.png", //公用信息
        "pjcs": "6",//公用信息
		 list": [ 
            { 
                "url": "/pub/yx/yfzz/yfzz5.png"
            }, 
			{ 
                "url": "/pub/yx/yfzz/yfzz5.png"
            },....
           ]}
	 * @param list
	 * @return
	 */
	public static String [] getStringArray(List<?> list){
		
		String str =getJsonBeanBody(list);
		//System.out.println("getJsonBeanBody str "+str);
		String res[] =JsonUtil.getStringArray(JSONObject.fromObject(str), "list");
		System.out.println("getStringArray res "+res);
		
		return res;
		 
	}
	/**
	 * 可直接将list转换成 json的String形式
	 * 为String [] getStringArray(List<?> list) 的基础方法
	 * 
	 * @param list
	 * @return
	 */
	public static String getJsonBeanBody(List<?> list) {
		Body json = new Body();
		json.setList(list);
		String str =JsonUtil.toJson(json);
		 System.out.println("getJsonBeanBody:"+str);
		
		return str;
				
	}
	/**
	 * map 的数据 数据里有 '\'做分割 将其代替
	 * @param data
	 * @return
	 */
	public static String MapJsonFormat(String data){
		String news="";
		String json =data.replaceAll("\\\\", news);
		return json;
	}
	/**
	 * 封装返回消息 去除了对象形式的返回值
	 * 
	 * @param state
	 *            消息状态码
	 * @param msg
	 *            消息内容
	 * @param obj
	 *            对象数据
	 * @param list
	 *            集合数据
	 * @return
	 */
	public static String getJsonBeanStr1(Integer state, String msg, 
			List<?> list) {
		JsonBeans json = new JsonBeans();
		json.setState(state);
		json.setMsg(msg);
		json.setList(list);
		return JsonUtil.toJson(json);
	}
	
	/**
	 * 封装返回消息 去除了对象形式的返回值
	 * 
	 * @param state
	 *            消息状态码
	 * @param msg
	 *            消息内容
	 * @param map
	 *            集合数据
	 * @return
	 */
	public static String getJsonBeanStrByMap(Integer state, String msg, 
			Map<Object, Object> map) {
		JsonBeans json = new JsonBeans();
		json.setState(state);
		json.setMsg(msg);
		json.setMap(map);
		return JsonUtil.toJson(json);
	}
	
	

	/**
	 * 将Collection转换为Json
	 * 
	 * @param collection
	 * @return
	 */
	public static String toJson(Collection<Object> collection) {
		String json = "[]";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(collection);
		} catch (JsonProcessingException e) {
			logger.error(
					"------toJson(Collection<Object> collection) JsonProcessingException error:\n",
					e);
		}
		return json;
	}

	/**
	 * 将Object转换为Json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		String json = "[]";
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(
					"------toJson(Object obj) JsonProcessingException error:\n",
					e);
		}
		return json;
	}

	/**
	 * 将MAP转为Json Map参数
	 * 
	 * @param map
	 * @return
	 */
	public static String toJson(Map<String, Object> map) {
		String json = "[]";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.error(
					"------toJson(Map<String, Object> map) JsonProcessingException error:\n",
					e);
		}
		return json;
	}

	/**
	 * 将JSON字符串 转换为对象
	 * 
	 * @param jsonStr
	 *            JSON字符串
	 * @param beanClass
	 *            泛型对象
	 * @param field
	 *            对象中需要忽略的属性
	 * @return
	 */
	public static Object jsonToObject(String jsonStr, Class<?> beanClass)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return objectMapper.readValue(jsonStr.getBytes(), beanClass);
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param jsonStr
	 * @param encoding
	 * @param beanClass
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object jsonToObject(String jsonStr, String encoding,
			Class<?> beanClass) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonStr.getBytes(encoding), beanClass);
	}

	/**
	 * 将JSON字符串转换为Map
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonStr, Map.class);
	}

	/**
	 * @param object
	 *            需要进行json序列化的类 可以是任何复杂的类
	 * @param args
	 *            需要进行过滤的属性名称 类对象名称或者一些属性名称都可以进行过滤
	 * @param filterName
	 * @return
	 */
	public static String toJson(Object object, String[] args, String filterName) {
		// SimpleBeanPropertyFilter.filterOutAllExcept("");//这里需要填需要留下的属性
		// SimpleBeanPropertyFilter.serializeAllExcept("");//这里需要真需要排除的属性
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
				filterName, SimpleBeanPropertyFilter.serializeAllExcept(args));
		mapper.setFilters(filterProvider);
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error("----toJson(Object object, String[] args, String filterName) "
					+ e);
		}
		return json;
	}

	/**
	 * 获取cookie信息 返回JSONObject对象
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject getCurrentUserJSON(HttpServletRequest request) {
		Cookie[] cookies2 = request.getCookies();
		if (null != cookies2) {
			for (Cookie cookie : cookies2) {
				String name = cookie.getName();
				if (name.equals("user")) {
					// 先转码，后解密
					String cookieStr = "";
					String dec = "";
					try {
						cookieStr = URLDecoder.decode(cookie.getValue(),
								"UTF-8");
						try {
							dec = StringCoder.decoder(cookieStr);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // get the cookie value
					return JSONObject.fromObject(dec);
				}
			}
		}
		return null;
	}

	/**
	 * 从json对象中获取字符串
	 * 
	 * @param obj
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @param defaultValue
	 *            属性值
	 * @return
	 */
	public static String getString(JSONObject obj, String propertyName,
			String defaultValue) {

		if (defaultValue != null) {
			String propertyValue = null == obj.get(propertyName) ? defaultValue
					: obj.get(propertyName).toString();
			return propertyValue;
		} else {
			String propertyValue = null == obj.get(propertyName) ? null : obj
					.get(propertyName).toString();
			return propertyValue;
		}
	}

	/**
	 * 从json对象中获取字符串数组
	 * 
	 * @param obj
	 * @param propertyName
	 * @param defaultValue
	 * @return
	 */
	public static String[] getStringArray(JSONObject obj, String propertyName) {

		JSONArray updateFiels = obj.getJSONArray(propertyName);

		Object[] array = updateFiels.toArray();

		if (array == null || array.length == 0) {

			return null;
		} else {
			String[] strArray= new String[array.length];
			for(int i=0;i<array.length;i++){
				strArray[i]=array[i].toString();
			}
			return strArray;
		}
	}

	/**
	 * 从json对象中获取Integer
	 * 
	 * @param obj
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @param defaultValue
	 *            属性值
	 * @return
	 */
	public static Integer getInteger(JSONObject obj, String propertyName,
			Integer defaultValue) {

		if (defaultValue != null) {
			Integer propertyValue = null == obj.get(propertyName) ? defaultValue
					: Integer.valueOf(obj.get(propertyName).toString());
			return propertyValue;
		} else {
			Integer propertyValue = null == obj.get(propertyName) ? null
					: Integer.valueOf(obj.get(propertyName).toString());
			return propertyValue;
		}
	}

	/**
	 * 从json对象中获取double
	 * 
	 * @param obj
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @param defaultValue
	 *            属性值
	 * @return
	 */
	public static Double getDouble(JSONObject obj, String propertyName,
			Double defaultValue) {

		if (defaultValue != null) {
			Double propertyValue = null == obj.get(propertyName) ? defaultValue
					: Double.valueOf(obj.get(propertyName).toString());
			return propertyValue;
		} else {
			Double propertyValue = null == obj.get(propertyName) ? null
					: Double.valueOf(obj.get(propertyName).toString());
			return propertyValue;
		}
	}
	
    
	public static void main(String[] args) {
		/*
		 * String info="{\"mobile\":\"18510030639\",\"password\":\"123456\"}";
		 * try { Map<String,Object> aa=parseJSON2Map(info);
		 * System.out.println("--"+aa.get("mobile"));
		 * System.out.println("--"+aa.get("password")); } catch
		 * (JsonParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (JsonMappingException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		// 注意:过滤的属性 需要在bean中加注解@JsonFilter("XXXX")
		/*
		 * String[] strs = { "uid", "userName", "password" }; UserTest user =
		 * new UserTest(); user.setAddress("北京"); user.setFullName("测试人员");
		 * user.setApplyDate(new Date()); user.setUid(100);
		 * user.setPassword("12345678"); user.setUserName("aaaaa"); String
		 * result = toJson(user, strs, "myFilters"); System.out.println(result);
		 */
		/*
		 * List list = new ArrayList(); list.add(0,"name");
		 * list.add(1,"favorite"); String li = toJson(list);
		 * System.out.println(li);
		 * 
		 * Map<String,String> map = new HashMap<String,String>();
		 * map.put("password", "123"); map.put("name", "clark"); map.put("call",
		 * "wowo"); String mr = toJson(map); System.out.println(mr);
		 * 
		 * Date d = new Date(); String jsonStr =
		 * "{\"uid\":23,\"address\":\"北京市\"}"; try { UserTest u =
		 * (UserTest)jsonToObject(jsonStr, UserTest.class);
		 * System.out.println(u.getAddress());
		 * 
		 * 
		 * Map<String, Object> mapResult = parseJSON2Map(jsonStr); Set<String>
		 * key = mapResult.keySet(); Iterator iter = key.iterator();
		 * while(iter.hasNext()){ String k = (String) iter.next();
		 * System.out.println(mapResult.get(k)); }
		 * 
		 * } catch (JsonParseException e) { e.printStackTrace(); } catch
		 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
	}

}
