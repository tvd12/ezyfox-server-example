package com.tvd12.example.lucky_wheel.repo;

import com.tvd12.example.lucky_wheel.entity.Wheel;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository("wheelRepo")
public interface WheelRepo extends EzyMongoRepository<String, Wheel> {

}
