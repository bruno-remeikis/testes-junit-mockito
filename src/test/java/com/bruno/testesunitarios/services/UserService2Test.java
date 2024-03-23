package com.bruno.testesunitarios.services;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.models.dto.UserSaveDTO;
import com.bruno.testesunitarios.repositories.UserRepository;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Aponta para application-test.propertiess
class UserService2Test
{
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    /*@BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.save(UserMock.userMock()))
            .thenReturn(UserMock.userMock());
    }*/

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor; // <- Pega o valor real que é passado ao repository mockado

    @Nested
    class save {
        @Test
        @DisplayName("Should create user with success")
        void shouldCreateUserWithSuccess() throws Exception {
            // -- Arrange --
            var user = UserMock.user();
            user.setDtInsert(new Date());

            doReturn(user).when(userRepository)
                .save(userArgumentCaptor.capture() /*user*/);

            var input = UserMock.user();
            input.setId(null);

            // -- Act --
            var output = userService.save(UserSaveDTO.toThis(input));

            // -- Assert --
            assertNotNull(output);

            var captured = userArgumentCaptor.getValue();

            assertNull(captured.getId()); // <- O ID deve chegar nulo ao repository
            assertEquals(user.getId(), output.getId()); // <- O ID final deve ser igual ao mockado
            assertEquals(output.getName(), captured.getName()); // <- O name mockado deve ser igual ao nome que chega ao repository
            assertEquals(output.getEmail(), captured.getEmail()); // <- O email mockado deve ser igual ao nome que chega ao repository
            assertNotNull(output.getDtInsert()); // <- A dtInsert retornada deve estar valorada
            assertNull(output.getDtUpdate()); // <- A dtUpdate retornada deve ser nula
        }

        @Test
        @DisplayName("Should throw DataIntegrityViolationException")
        void shouldThrowDataIntegrityViolationException() {
            var user = UserMock.user();

            //var e = new DataIntegrityViolationException("", new ConstraintViolationException("", null, ""));

            doThrow(DataIntegrityViolationException.class)
                .when(userRepository).save(user);

            assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.save(UserSaveDTO.toThis(user))
            );
        }

        /*
        @Test
        @DisplayName("Should throw an exception when email already exist")
        void shouldThrowAnExceptionWhenEmailAlreadyExist() {
            var user = UserMock.user();

            // doAnswer: usado para jogar uma exception que não está listada no trows do userRepository.save(.)
            doAnswer(invocation -> {
                throw new SQLIntegrityConstraintViolationException();
            }).when(userRepository).save(user);

            assertThrows(
                SQLIntegrityConstraintViolationException.class,
                () -> userService.save(UserSaveDTO.toThis(user))
            );
        }

        @Test
        @DisplayName("Should throw an exception when a not-null attribute is null")
        void ShouldThrowAnExceptionWhenANotNullAttributeIsNull() {
            var user = UserMock.user();
            user.setName(null);

            doAnswer(invocation -> {
                throw new DataIntegrityViolationException("");
            }).when(userRepository).save(user);

            assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.save(UserSaveDTO.toThis(user))
            );
        }
    */
    }

    @Nested
    class search
    {
        @Test
        @DisplayName("Should return a list of users on search for 'T'")
        void shouldReturnAListOfUsersOnSearchForT() {
            var tUserList = UserMock.tUserList();

            doReturn(tUserList).when(userRepository).search("T");

            assertEquals(tUserList, userService.search("T"));
        }

        @Test
        @DisplayName("Should return all users when search null")
        void shouldReturnAllUsersWhenSearchIsNull() {
            var userList = UserMock.userList();

            doReturn(userList).when(userRepository).findAll();

            assertEquals(userList, userService.search(null));
        }

        @Test
        @DisplayName("Should return all users when search is blank")
        void shouldReturnAllUsersWhenSearchIsBlank() {
            var userList = UserMock.userList();

            doReturn(userList).when(userRepository).findAll();

            assertEquals(userList, userService.search(" "));
        }

        @Test
        @DisplayName("Should return an empty list on search for 'Z'")
        void shouldReturnAnEmptyListOnSearchForZ() {
            List<User> emptyList = List.of();

            doReturn(emptyList).when(userRepository).search("Z");

            assertEquals(emptyList, userService.search("Z"));
        }
    }

    @Nested
    class find
    {
        @Test
        @DisplayName("Should find user by ID with success")
        void shouldFindUserByIdWithSuccess() {
            var user = UserMock.user();

            doReturn(Optional.of(user))
                .when(userRepository).findById(user.getId());

            var output = userService.findById(user.getId());

            assertNotNull(output);
            assertEquals(user, output);
        }

        @Test
        @DisplayName("Should return null on find by inexistent ID")
        void shouldReturnNullOnFindByInexistentId() {
            doReturn(Optional.empty())
                .when(userRepository).findById(99);

            var output = userService.findById(99);

            assertNull(output);
        }
    }

    @Nested
    class delete
    {
        @Test
        @DisplayName("Should delete an user with success")
        void shouldDeleteAnUserWithSuccess() {
            // -- Arrange --
            var user = UserMock.user();

            doNothing()
                .when(userRepository).deleteById(user.getId());

            doReturn(Optional.of(user))
                .when(userRepository).findById(user.getId());

            // -- Act --
            var output = userService.delete(user.getId());

            // -- Assert --
            assertEquals(user, output);
        }
    }
}