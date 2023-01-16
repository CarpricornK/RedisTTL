package com.example.demo.vo;

import lombok.Data;

@Data // Lombok 미적용 시, Getter/Setter 메서드 추가
public class TestVo {

    private String key;
    private String text;

    private String value;

}