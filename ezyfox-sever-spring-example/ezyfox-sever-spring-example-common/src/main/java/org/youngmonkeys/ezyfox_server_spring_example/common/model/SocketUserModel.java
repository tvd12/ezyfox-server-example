package org.youngmonkeys.ezyfox_server_spring_example.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocketUserModel {
    private long id;
    private String username;
    private String password;
    private String displayName;
}
