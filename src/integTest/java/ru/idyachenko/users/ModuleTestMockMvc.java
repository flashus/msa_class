package ru.idyachenko.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static ru.idyachenko.users.utils.RestUtils.validationRestStub;
// import static ru.idyachenko.users.utils.TestUtils.classpathFileToObject;
import static ru.idyachenko.users.utils.TestUtils.classpathFileToString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
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

// import java.util.UUID;
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// // MOCKMVC (1)
// @AutoConfigureMockMvc
// @AutoConfigureWireMock(port = 0)
@ActiveProfiles("integTest-mockMvc")
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
public class ModuleTestMockMvc {

    // // MOCKMVC (1)
    // @Autowired
    // private MockMvc mockMvc;

    // (5) PostgreSQL мокаем взаимодействие с БД
    // @MockBean
    // private UserRepository repository;

    @Autowired
    @NonNull
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // // (4) REST мокаем взаимодействие с REST
    // @BeforeEach
    // void initStubs() {
    // WireMock.reset();
    // validationRestStub(HttpStatus.OK,
    // "lesson2/responses/validate_OK.json");
    // }

    @Test
    void applicationContextStartedSuccessfullyTest(ApplicationContext context) throws Exception {
        assertThat(context).isNotNull();
        // health-check
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void saveSuccessfullyTest() throws Exception {
        // when
        // @NonNull
        String request = classpathFileToString("/requests/user1.json");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));
        String userId = resultActions.andReturn().getResponse().getHeader("X-UserId");

        resultActions
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$",
                        equalTo(String.format("Пользователь Sobolev добавлен в базу с id = %s",
                                userId))));

    }

}
