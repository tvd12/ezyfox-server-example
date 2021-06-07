package com.tvd12.example.lucky_wheel.repo;

import com.tvd12.example.lucky_wheel.entity.Wheel;

public interface WheelRepo {
    Wheel findById(long id);
}
