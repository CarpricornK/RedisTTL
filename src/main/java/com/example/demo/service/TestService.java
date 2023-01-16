package com.example.demo.service;

import com.example.demo.util.JsonConverter;
import com.example.demo.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
@Slf4j
public class TestService {

    private RedisTemplate<String, Object> redisTemplate;
    private JsonConverter jsonConverter = new JsonConverter();

    public TestVo getTestSvc(String id){

        Map<String, Object> map = new HashMap()
        {
            {
                put("key: " + id, "value : 2023-03-01 ,삼일절");
            }
        };


        JSONObject json =  new JSONObject(map);

        TestVo tvo = new TestVo();
        tvo.setKey(id);
        //tvo.setText("2023-03-01 " + ",삼일절");
        tvo.setValue(json.toString());

        System.out.println("[key:" + id + "] Service S");
        log.info("jsonString:" + json.toString() + "," + "json:" + json);
        log.info("map:" + map);

        return tvo;
    }
}