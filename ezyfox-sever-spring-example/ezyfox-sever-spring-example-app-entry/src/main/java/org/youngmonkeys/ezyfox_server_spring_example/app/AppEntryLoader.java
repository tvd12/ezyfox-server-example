package org.youngmonkeys.ezyfox_server_spring_example.app;

import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;

public class AppEntryLoader extends EzyAbstractAppEntryLoader {

    @Override
    public EzyAppEntry load() throws Exception {
        return EzyClasses.newInstance("org.youngmonkeys.ezyfox_server_spring_example.app.AppEntry");
    }
}
