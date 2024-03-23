package com.bruno.testesunitarios.models.dto;

import com.bruno.testesunitarios.models.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDTO
{
    private Integer id;
    private String name;
    private String email;

    public static UserSaveDTO toThis(User user) {
        return new UserSaveDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    public User toUser() {
        return new User(id, name, email);
    }
}
