package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.PasswordEncoder;
import edu.eskisehir.teklifyap.core.Singleton;
import edu.eskisehir.teklifyap.domain.dto.PasswordDto;
import edu.eskisehir.teklifyap.domain.dto.UpdateUserDto;
import edu.eskisehir.teklifyap.domain.dto.UserDto;
import edu.eskisehir.teklifyap.domain.model.PasswordResetToken;
import edu.eskisehir.teklifyap.domain.model.Token;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.UserMapper;
import edu.eskisehir.teklifyap.repository.PasswordTokenRepository;
import edu.eskisehir.teklifyap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PasswordTokenRepository passwordTokenRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
                       TokenService tokenService, PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    protected User findById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("UserNotFound"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }

    public UserDto getProfile(Long uid) throws Exception {

        User user = findById(uid);
        return userMapper.toUserDto(user);
    }

    public void updateProfile(Long id, UpdateUserDto body) throws Exception {
        User user = findById(id);
        if (passwordEncoder.bCryptPasswordEncoder().matches(body.getPassword(), user.getPassword())) {

            if (body.getNewPassword() != null && Singleton.validatePass(body.getNewPassword())) {
                user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(body.getNewPassword()));
            }
            if (body.getName() != null) user.setName(body.getName());
            if (body.getSurname() != null) user.setSurname(body.getSurname());
            if (body.getEmail() != null && Singleton.validateEmail(body.getEmail())) user.setEmail(body.getEmail());
            if (body.getPassword() != null) user.setPassword(body.getPassword());
            if (body.getNewPassword() != null) user.setPassword(body.getNewPassword());

            save(user);
        } else {
            throw new Exception("PasswordNotMatch");
        }
    }

    public void deleteUser(String email, String token) throws Exception {
        Token optional = tokenService.findByTokenAndEmail(token, email);
        User user = findByEmail(email);
        userRepository.deleteUser(user.getId());
        tokenService.delete(optional);
    }


    public void resetPassword(String email, String token, PasswordDto passwordDto) throws Exception {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new Exception("Link is invalid or broken"));
        User user = findByEmail(email);
        if (passwordDto.getPassword().equals(passwordDto.getPasswordConfirm())) {
            user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(passwordDto.getPassword()));
            save(user);
            passwordTokenRepository.delete(passwordResetToken);
        } else {
            throw new Exception("PasswordNotMatch");
        }
        passwordTokenRepository.delete(passwordResetToken);
    }
}
