package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 객체를 JSON으로, JSON 문자열을 객체로 변환하는 Utility Class
 *
 * @since	2022-12-07
 * @author 	ywkim
 */
@Slf4j
public class JsonConverter {

	/**
	 * Map을 JSON String으로 변환한다.
	 *
	 * @param	map		JSON String으로 변환할 Map
	 * @return	String	JSON String
	 */
    public String convertToJsonStr(Map<String, Object> map) {
	    JSONObject jsonObject = new JSONObject();
		try {
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		} catch(Exception e) {
			log.error("error; map={}, message={}", map, e.getMessage());
		}
		return jsonObject.toString();
	}

	/**
	 * List를 JSON String으로 변환한다.
	 *
	 * @param	list	JSON String으로 변환할 List
	 * @return	String	JSON String
	 */
	public String convertToJsonStr(List<Map<String, Object>> list) {
        JSONArray jsonArray = new JSONArray();
		for (Map<String, Object> map : list) {
			jsonArray.put(convertToJsonStr(map));
		}
		return jsonArray.toString();
	}

	/**
	 * JSON String을 Map으로 변환한다.
	 *
	 * @param	jsonStr		Map으로 변환할 JSON String
	 * @return	Map			Map
	 */
	public Map<String, Object> convertToMap(String jsonStr) {
		if (! StringUtils.hasLength(jsonStr))
			return new HashMap<String, Object>();

		jsonStr = getRemovedSlashes(jsonStr);

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(jsonStr, Map.class);
		} catch(Exception e) {
			log.error("error; jsonStr={}, message={}", jsonStr, e.getMessage());
		}
		return map;
	}

	/**
	 * JSON String을 List로 변환한다.
	 *
	 * @param	jsonStr		List로 변환할 JSON String
	 * @return	List		List
	 */
	public List<Map<String, Object>> convertToList(String jsonStr) {
		if (! StringUtils.hasLength(jsonStr))
			return new ArrayList<Map<String, Object>>();

		jsonStr = getRemovedSlashes(jsonStr);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			list = objectMapper.readValue(jsonStr, List.class);
		} catch(Exception e) {
			log.error("error; jsonStr={}, message={}", jsonStr, e.getMessage());
		}
		return list;
	}

	/**
	 * JSON String에서 JSON format이 아닌 문자열("\{, }\, \", "\ 을 변환한다.
	 *
	 * @param	jsonStr		List로 변환할 JSON String
	 * @return	List		List
	 */
	public String getRemovedSlashes(String jsonStr) {
		if (! StringUtils.hasLength(jsonStr))
			return "";

		return jsonStr
				.replaceAll("\"\\{", "{")
				.replaceAll("\\}\"", "}")
				.replaceAll("\\\\\"", "\"")
				.replaceAll("\"\\\\", "\"");
	}

	/**
	 * Map<String, Object>를 Map으로 변환한다.
	 *
 	 * @param	typedMap	Map<String, Object> 형식의 Map
	 * @return	Map			Map
	 */
	public Map convertToUntypedMap(Map<String, Object> typedMap) {
		Map result = new HashMap();
		if ((typedMap == null) || typedMap.isEmpty())
			return result;

		for(String key : typedMap.keySet()) {
			result.put(key, typedMap.get(key));
		}

		return result;
	}

	/**
	 * List<Map<String, Object>>를 List<Map>으로 변환한다.
	 *
 	 * @param	typedMapList	List<Map<String, Object>> 형식의 List
	 * @return	List<Map>		List<Map>
	 */
	public List<Map> convertToUntypedMapList(List<Map<String, Object>> typedMapList) {
		List<Map> list = new ArrayList<Map>();
		if (typedMapList == null)
			return list;

		int count = typedMapList.size();
		if (count < 1)
			return list;

		Map<String, Object> typedMap = null;
		Map oneRow = null;
		for(int i=0; i<count; i++) {
			typedMap = typedMapList.get(i);
			oneRow = new HashMap();
			if (typedMap != null) {
				for (String key : typedMap.keySet()) {
					oneRow.put(key, typedMap.get(key));
				}
			}
			list.add(oneRow);
		}

		return list;
	}

	/**
	 * Map<String, Object>를 Map으로 변환한다.
	 *
 	 * @param	untypedMap				Map<String, Object> 형식의 Map
	 * @return	Map<String, Object>		Map<String, Object>
	 */
	public Map<String, Object> convertToTypedMap(Map untypedMap) {
		Map result = new HashMap();
		if ((untypedMap == null) || untypedMap.isEmpty())
			return result;

		for(Object key : untypedMap.keySet()) {
			result.put(String.valueOf(key), untypedMap.get(key));
		}

		return result;
	}

	/**
	 * List<Map>를 List<Map<String, Object>>으로 변환한다.
	 *
 	 * @param	untypedMapList				List<Map> 형식의 List
	 * @return	List<Map<String, Object>>	List<Map<String, Object>>
	 */
	public List<Map<String, Object>> convertToTypedMapList(List<Map> untypedMapList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (untypedMapList == null)
			return list;

		int count = untypedMapList.size();
		if (count < 1)
			return list;

		Map untypedMap = null;
		Map<String, Object> oneRow = null;
		for(int i=0; i<count; i++) {
			untypedMap = untypedMapList.get(i);
			oneRow = new HashMap();
			if (untypedMap != null) {
				for (Object key : untypedMap.keySet()) {
					oneRow.put(String.valueOf(key), untypedMap.get(key));
				}
			}
			list.add(oneRow);
		}

		return list;
	}

}
