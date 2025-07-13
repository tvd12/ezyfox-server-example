package org.youngmonkeys.ezyfox_server_spring_example.common.converter;

import org.springframework.stereotype.Component;
import org.youngmonkeys.ezyfox_server_spring_example.common.entity.SocketUser;
import org.youngmonkeys.ezyfox_server_spring_example.common.model.SocketUserModel;

@Component
public class CommonEntityToModelConverter {

    public SocketUserModel toModel(SocketUser entity) {
        if (entity == null) {
            return null;
        }
        return SocketUserModel.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .password(entity.getPassword())
            .displayName(entity.getDisplayName())
            .build();
    }
}
