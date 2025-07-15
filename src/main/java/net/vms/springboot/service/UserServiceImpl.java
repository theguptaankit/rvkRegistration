package net.vms.springboot.service;

import net.vms.springboot.model.Role;
import net.vms.springboot.model.User;
import net.vms.springboot.repository.UserRepository;
import net.vms.springboot.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getSmartCardId(), registrationDto.getFirstName(),
                registrationDto.getUsername(), registrationDto.getPassword(), Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }
}
