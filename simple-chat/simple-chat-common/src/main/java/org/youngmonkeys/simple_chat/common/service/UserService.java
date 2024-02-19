package org.youngmonkeys.simple_chat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.simple_chat.common.entity.User;
import org.youngmonkeys.simple_chat.common.repo.UserRepository;

import java.time.LocalDateTime;

@EzySingleton
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long addUser(String username, String hashedPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setStatus("ACTIVATED");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user.getId();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByField(
            "username",
            username
        );
    }
}
