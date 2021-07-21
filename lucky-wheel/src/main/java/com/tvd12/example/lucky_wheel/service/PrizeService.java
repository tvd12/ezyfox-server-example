package com.tvd12.example.lucky_wheel.service;

import com.tvd12.example.lucky_wheel.entity.Prize;
import com.tvd12.example.lucky_wheel.entity.User;

public interface PrizeService {
	
	void savePrize(Prize record);
	
	Prize createPrize(String username, int prizeRecord);
}
