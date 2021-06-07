package com.tvd12.example.lucky_wheel.entity;

import lombok.Data;

import java.util.List;

@Data
public class Wheel {
    private long id;
    private List<WheelFragment> fragments;
}
