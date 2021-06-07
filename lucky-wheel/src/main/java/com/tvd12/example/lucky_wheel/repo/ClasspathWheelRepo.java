package com.tvd12.example.lucky_wheel.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.example.lucky_wheel.entity.Wheel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;

import java.io.IOException;
import java.io.InputStream;

@EzySingleton
public class ClasspathWheelRepo implements WheelRepo {
    @Override
    public Wheel findById(long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new EzyAnywayInputStreamLoader().load("wheel.json");
        try {
            return objectMapper.readValue(inputStream, Wheel.class);
        }
        catch (IOException e) {
            throw new IllegalStateException("can not load wheel", e);
        }
    }
}
