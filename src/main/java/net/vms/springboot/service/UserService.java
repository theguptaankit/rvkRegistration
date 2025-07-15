package net.vms.springboot.service;

import net.vms.springboot.model.User;
import net.vms.springboot.web.dto.UserRegistrationDto;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
