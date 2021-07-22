package com.tvd12.example.lucky_wheel.repo;

import com.tvd12.example.lucky_wheel.entity.Prize;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository("prizeRepo")
public interface PrizeRepo extends EzyMongoRepository<Long, Prize> {
}
