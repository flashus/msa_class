package ru.idyachenko.users;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("integTest-mockMvc")
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    @NonNull
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createUser() throws Exception {
        String request = "{\n" + "\"fname\": \"Vasya\",\n" + "\"lname\": \"Petrov\",\n"
                + "\"mname\": \"Ivanovich\",\n" + "\"avatarUrl\": \"http://das.vasya.avatar\",\n"
                + "\"nickname\": \"vasya\",\n" + "\"email\": \"vasya@mail.com\"\n" + "}";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(request));
        String userId = resultActions.andReturn().getResponse().getHeader("X-UserId");

        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$", equalTo(
                        String.format("Пользователь Petrov добавлен в базу с id = %s", userId))));
    }
}
