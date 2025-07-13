package org.youngmonkeys.ezyfox_server_spring_example.common.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youngmonkeys.ezyfox_server_spring_example.common.entity.SocketUser;

@Repository
public interface SocketUserRepository extends JpaRepository<SocketUser, Long> {

    SocketUser findByUsername(String username);
}
