package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageUserImpl implements Storage <User> {
    private static long autoIncUser = 1;
    private final Map<Long, User> userStorage = new HashMap<>();
    @Override
    public User newEntity(User user){
        user.setId(autoIncUser);
        userStorage.put(autoIncUser, user);
        autoIncUser++;
        return user;
    }

    @Override
    public User updateEntity(User user){
        if (user.getId()==null){
            return newEntity(user);
        }
        if (userStorage.get(user.getId())==null){
            throw new NotFoundException("User not created");
        }
        // Полностью заменяем пользователя
        userStorage.remove(user.getId());
        userStorage.put(user.getId(),user);
        return user;
    }


    @Override
    public User getEntity (Long id){
        if (Objects.isNull(userStorage.get(id))){
            throw new NotFoundException("User not created");
        }
        return userStorage.get(id);
    }

    @Override
    public List<User> getListEntity(Long id) {
        return null;
    }

    @Override
    public void deleteEntity (Long id){
        userStorage.remove(id);
    }

}
