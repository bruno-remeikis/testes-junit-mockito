package com.bruno.testesunitarios.services;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Array;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Aponta para application-test.propertiess
class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userAC;

    /*private User createUser() {

    }*/

    /*
    @Nested
    class search
    {
        @Test
        @DisplayName("Should return multiple users")
        void shouldReturnMultipleUsers() {

        }

        @Test
        @DisplayName("Should return a specific user")
        void shouldReturnASpecificUser() {

        }

        @Test
        @DisplayName("Should return an empty list")
        void shouldReturnAnEmptyList() {
            String search = "Fulano";

            List<User> emptyUsersList = Arrays.asList(new User[]{});

            doReturn(emptyUsersList)
                .when(userRepository).search(search);

            assertEquals(
                emptyUsersList,
                userService.search(search)
            );
        }
    }

    @Nested
    class save
    {
        @Test
        @DisplayName("Should create an user with success")
        void shouldCreateUserWithSuccess() {
            // Arrange

            var user = UserMock.user();

            doReturn(persistedUser)
                .when(userRepository)
                .save(userAC.capture());

            // Act

            var output = userService.save(newUser);
            var captured = userAC.getValue();

            // Assert
            assertNotNull(output);
            //assertEquals(input.);
        }

        @Test
        @DisplayName("Should throw exception when not-null attribute is null")
        void shouldThrowExceptionWhenNotNullAttributeIsNull() {
            when(userRepository.save(any()))
                .thenThrow(IllegalArgumentException.class);

            User u = new User(
                null,
                null,
                "email@email.com"
            );

            assertThrows(
                IllegalArgumentException.class,
                () -> userService.save(u)
            );
        }

        @Test
        @DisplayName("Should throw exception when email exist")
        void shouldThrowExceptionWhenEmailExist() {
            // Arrange
            when(userRepository.save(any()))
                .thenThrow(RuntimeException.class);
            //doThrow(new IllegalArgumentException())
            //    .when(userRepository).save(any());

            User input = new User(
                null,
                "name",
                "email@email.com"
            );

            // Act & Assert
            assertThrows(
                SQLIntegrityConstraintViolationException.class,
                () -> userService.save(input)
            );
        }

        /*@Test
        @DisplayName("")
        void shouldNotPersistDuplicateEmail() {
            // Arrange
            User user = new User(
                null,
                "teste",
                "teste@email.com"
            );

            //
            doReturn

            // Act

            // Assert
            assertThrows(
                SQLIntegrityConstraintViolationException.class,
                () -> userService.save(user)
            );
        }///
    }
    */
}