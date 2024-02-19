package org.youngmonkeys.simple_chat.plugin.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import lombok.AllArgsConstructor;
import org.youngmonkeys.simple_chat.common.entity.User;
import org.youngmonkeys.simple_chat.common.service.UserService;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;
import static org.youngmonkeys.simple_chat.common.constant.CommonConstants.KEY_DB_USER_ID;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
@AllArgsConstructor
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

    private final UserService userService;

    @Override
    public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
        String username = event.getUsername();
        logger.info("{} login in", username);
        long userId;
        String password = event.getPassword();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            userId = userService.addUser(
                username,
                BCrypt.hashpw(password, "$2a$10$ldrOLxsfSv76i/Htanolke")
            );
        } else {
            userId = user.getId();
            String hashedPassword = user.getPassword();
            if (!BCrypt.checkpw(password, hashedPassword)) {
                throw new EzyLoginErrorException(
                    EzyLoginError.INVALID_PASSWORD
                );
            }
        }
        event.setUserProperty(KEY_DB_USER_ID, userId);
    }
}
