package com.tvd12.example.lucky_wheel.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import lombok.Data;

import java.util.List;

@Data
@EzyCollection
public class Wheel {
    @EzyId
    private String id;
    private List<WheelFragment> fragments;
}
