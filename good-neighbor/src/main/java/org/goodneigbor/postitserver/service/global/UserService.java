package org.goodneigbor.postitserver.service.global;

import java.util.List;

import org.goodneigbor.postitserver.dto.global.UserDto;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getCurrentUser();

    List<UserDto> getUserList();

    UserDto saveUser(Long userId, UserDto userDto) throws NotFoundException, FunctionnalException, InvalidDataException;

    void deleteUser(Long userId);

    List<String> getRoleList();

}
