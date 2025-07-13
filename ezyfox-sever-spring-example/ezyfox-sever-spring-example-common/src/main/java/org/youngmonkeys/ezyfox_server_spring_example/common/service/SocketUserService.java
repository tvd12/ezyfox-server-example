package org.youngmonkeys.ezyfox_server_spring_example.common.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youngmonkeys.ezyfox_server_spring_example.common.converter.CommonEntityToModelConverter;
import org.youngmonkeys.ezyfox_server_spring_example.common.model.SocketUserModel;
import org.youngmonkeys.ezyfox_server_spring_example.common.repo.SocketUserRepository;

@Service
@Setter
public class SocketUserService {

    @Autowired
    private SocketUserRepository socketUserRepository;

    @Autowired
    private CommonEntityToModelConverter entityToModelConverter;

    public SocketUserModel getSocketUserByUsername(String username) {
        return entityToModelConverter.toModel(
            socketUserRepository.findByUsername(username)
        );
    }
}
