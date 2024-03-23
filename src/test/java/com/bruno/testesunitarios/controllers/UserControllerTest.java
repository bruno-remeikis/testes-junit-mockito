package com.bruno.testesunitarios.controllers;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.services.UserMock;
import com.bruno.testesunitarios.services.UserService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest
{
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    private final String url = "/user";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mvc;
    private User user;
    private String json;

    /*@BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();
        user = new User(null, "Fake name", "fake@email.com");
        json = objectMapper.writeValueAsString(user);
    }*/

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController)
            //.alwaysDo(print())
            .build();
    }

    @Nested
    class search
    {
        // https://www.youtube.com/watch?v=inq1vTkMOF8
        @Test
        void shouldReturnAnUsersListWithSuccess() throws Exception {
            String search = "F";

            doReturn(Arrays.asList(user))
                .when(userService).search("F");

            mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(userService).search("F");
            verifyNoMoreInteractions(userService);
        }
    }

    @Nested
    class delete
    {
        @Test
        @DisplayName("Should delete an user and return it with success")
        void shouldDeleteAnUserAndReturnItWithSuccess() throws Exception {
            var user = UserMock.user();

            doReturn(user)
                .when(userService).delete(user.getId());

            //MvcResult result =
            mvc.perform(
                    delete(url + "/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(user)))
                .andReturn();
        }

        @Test
        @DisplayName("Should return a not found (404) status")
        void shouldReturnANotFoundStatus() throws Exception {
            doReturn(null)
                .when(userService).delete(anyInt());

            //MvcResult result =
            mvc.perform(
                    delete(url + "/{id}", anyInt())
                    .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn();

            //System.out.println(result.getResponse().getContentAsString());
        }
    }
}
