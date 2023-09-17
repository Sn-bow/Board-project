package com.mysite.board.siteUser;


import com.mysite.board.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setCreateUserDateTime(LocalDateTime.now());
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser (String username) {
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        if(_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

}
