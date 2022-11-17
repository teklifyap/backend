package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.dto.UserDto;
import edu.eskisehir.teklifyap.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    User regiterDtoToUser(RegisterDto registerDto);


}
