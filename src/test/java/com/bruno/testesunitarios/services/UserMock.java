package com.bruno.testesunitarios.services;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.models.dto.UserSaveDTO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserMock {
    public static User user() {
        return new User(1, "Test", "test@email.com");
    }

    public static List<User> userList() {
        return Arrays.asList(
            new User(1, "Test", "test@email.com"),
            new User(2, "Aroldo", "aroldo@email.com"),
            new User(3, "Tarciso", "tarciso@email.com"),
            new User(4, "Claudio", "claudio@email.com")
        );
    }

    public static List<User> tUserList() {
        return Arrays.asList(
            new User(1, "Test", "test@email.com"),
            new User(3, "Tarciso", "tarciso@email.com")
        );
    }
}
