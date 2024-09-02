package org.goodneigbor.postitserver.service.global.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goodneigbor.postitserver.dao.global.UserAuthorityDao;
import org.goodneigbor.postitserver.dao.global.UserDao;
import org.goodneigbor.postitserver.dto.global.UserDto;
import org.goodneigbor.postitserver.entity.global.User;
import org.goodneigbor.postitserver.entity.global.UserAuthority;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.mapper.global.UserMapper;
import org.goodneigbor.postitserver.service.global.GlobalService;
import org.goodneigbor.postitserver.service.global.UserService;
import org.goodneigbor.postitserver.util.parameter.ParameterConst;
import org.goodneigbor.postitserver.util.parameter.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Streams;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final Integer MIN_PASSWORD_LENGTH = 5;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthorityDao userAuthorityDao;

    @Autowired
    private GlobalService globalService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDao.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found for : " + username));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object userPrincipal = authentication.getPrincipal();
        if (!(userPrincipal instanceof User)) {
            return null;
        } else {
            return UserMapper.INSTANCE.toDto((User) userPrincipal);
        }
    }

    @Override
    public List<UserDto> getUserList() {
        LOGGER.info("GetUserList");
        Iterable<User> userIterable = userDao.findAll(Sort.by("username").ascending());
        return Streams.stream(userIterable).map(UserMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(Long userId, UserDto userDto)
            throws NotFoundException, FunctionnalException, InvalidDataException {
        User user = null;

        if (userId == null) {
            // Creation
            Optional<String> maxUserParameter = globalService.getParameterValue(ParameterConst.USER_MAX);
            Long maxUser = ParameterUtil.getLong(maxUserParameter, 0l);
            if (userDao.count() > maxUser) {
                throw new FunctionnalException("Max User achieved, creation is blocked");
            }

            user = new User();
            user.setEnabled(false);
            LOGGER.info("Create user");

        } else {
            // Update
            Optional<User> userOpt = userDao.findById(userId);
            user = userOpt.orElseThrow(() -> new NotFoundException("User"));
            LOGGER.info("Update user with the id : {}", userId);
        }

        updatePassword(userDto.getPassword(), user);
        updateRoleList(userDto.getRoleList(), user);
        UserMapper.INSTANCE.updateEntity(userDto, user);

        return UserMapper.INSTANCE.toDto(userDao.save(user));
    }

    private void updatePassword(String password, User user) throws InvalidDataException {
        if (StringUtils.isEmpty(password)) {
            return;
        }

        if (password.contains(" ") || StringUtils.length(password) < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password of user is invalid");
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
    }

    private void updateRoleList(List<String> roleList, User user) throws FunctionnalException {
        if (roleList == null) {
            return;
        }

        List<UserAuthority> authorityList = new ArrayList<>();
        Set<String> roleSet = new HashSet<>(roleList);

        for (String role : roleSet) {
            authorityList.add(userAuthorityDao.findByAuthority(role)
                .orElseThrow(() -> new FunctionnalException("Role not found : " + role)));
        }
        user.setAuthorityList(authorityList);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> userOpt = userDao.findById(userId);
        if (!userOpt.isPresent()) {
            LOGGER.warn("User not found for deletion, id = %s", userId);
            return;
        }
        userDao.delete(userOpt.get());
        LOGGER.info("Delete user with the id : {}", userId);
    }

    @Override
    public List<String> getRoleList() {
        LOGGER.info("getRoleList");
        Iterable<UserAuthority> userAuthorityIterable = userAuthorityDao.findAll(Sort.by("authority").ascending());
        return Streams.stream(userAuthorityIterable).map(UserAuthority::getAuthority).collect(Collectors.toList());
    }

}
