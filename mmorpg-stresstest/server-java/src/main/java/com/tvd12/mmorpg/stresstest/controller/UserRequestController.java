package com.tvd12.mmorpg.stresstest.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.mmorpg.stresstest.world.World;
import lombok.Setter;

@Setter
@EzyRequestController
public class UserRequestController extends EzyLoggable {

    @EzyAutoBind
    private World world;

    @EzyDoHandle("r")
    public void getGameId(EzyUser user) {
        world.onUserGetNeighbours(user);
    }
}
