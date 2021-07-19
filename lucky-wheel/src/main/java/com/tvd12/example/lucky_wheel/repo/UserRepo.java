package com.tvd12.example.lucky_wheel.repo;

import com.tvd12.example.lucky_wheel.entity.User;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository("userRepo")
public interface UserRepo extends EzyMongoRepository<Long, User> {
}

