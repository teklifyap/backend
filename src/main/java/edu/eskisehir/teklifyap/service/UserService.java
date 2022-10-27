package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    protected User findById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("UserNotFound"));
    }

//    protected User findByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
//    }

    public void save() {
        User user = new User();
        user.setName("test");
        user.setSurname("test");
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
