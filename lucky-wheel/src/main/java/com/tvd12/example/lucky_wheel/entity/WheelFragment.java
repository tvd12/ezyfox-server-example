package com.tvd12.example.lucky_wheel.entity;

import lombok.Data;

@Data
public class WheelFragment {
    private int index;
    private WheelPrizeType prizeType;
    private int prizeValue;
    private int quantity;
    private int ratio;
}
