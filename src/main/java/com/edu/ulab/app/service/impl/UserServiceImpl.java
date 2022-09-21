package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final Storage<User> storage;
@Autowired
    public UserServiceImpl(UserMapper userMapper, Storage<User> storage) {
        this.userMapper = userMapper;
        this.storage = storage;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (Objects.isNull(userDto)){
            throw new NotFoundException("UserDto is null");
        }
        User user = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(storage.newEntity(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (Objects.isNull(userDto)){
            throw new NotFoundException("UserDto is null");
        }
        User user = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(storage.updateEntity(user));
    }

    @Override
    public UserDto getUserById(Long id) {
        if (Objects.isNull(id)){
            throw new NotFoundException("Long is null");
        }
        User user = storage.getEntity(id);
        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        if (Objects.isNull(id)){
            throw new NotFoundException("Long is null");
        }
        storage.deleteEntity(id);
    }
}
