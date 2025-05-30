package backend_blogapp.mapper;

import backend_blogapp.dto.response.UserResponse;
import backend_blogapp.model.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(MappingContext<User, UserResponse> context) {
        User user = context.getSource();
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setBio(user.getBio());
        response.setRoles(user.getRoles());
        response.setProfileImage(user.getProfileImage());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}

