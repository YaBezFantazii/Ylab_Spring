package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.web.request.create.UserRequest;
import com.edu.ulab.app.web.request.update.UserRequestUpdate;
import org.mapstruct.Mapper;
import com.edu.ulab.app.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);
    UserDto userRequestToUserDtoUpdate(UserRequestUpdate userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
