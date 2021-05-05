package com.tvd12.mmorpg.stresstest.config;

import com.tenio.engine.heartbeat.HeartBeatManager;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.mmorpg.stresstest.world.Constants;
import com.tvd12.mmorpg.stresstest.world.World;

@EzyConfigurationBefore
public class WorldConfig {

    @EzySingleton
    public World world() {
        World world = new World(Constants.DESIGN_WIDTH, Constants.DESIGN_HEIGHT);
        world.debug("mmorpg stresstest");
        var hearbeatManager = new HeartBeatManager();
        try {
            hearbeatManager.initialize(1);
            hearbeatManager.create("world", world);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return world;
    }
}
