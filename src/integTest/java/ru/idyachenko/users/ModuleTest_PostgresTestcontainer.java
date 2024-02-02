package ru.idyachenko.users;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.idyachenko.users.containers.PostgresContainerWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.idyachenko.users.utils.TestUtils.classpathFileToString;

@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
// WireMock (1)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("integTest-testCont")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ModuleTest_PostgresTestcontainer {

    @Container
    private static final PostgreSQLContainer<PostgresContainerWrapper> postgresContainer = new PostgresContainerWrapper();

    @DynamicPropertySource
    public static void initSystemParams(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    protected MockMvc mockMvc;

    // @BeforeEach
    // void initStubs() {
    // // WireMock (2)
    // WireMock.reset();
    // validationRestStub(HttpStatus.OK,
    // "lesson4/responses/validate_OK.json");
    // }

    @Test
    void applicationContextStartedSuccessfullyTest(ApplicationContext context) throws Exception {
        assertThat(context).isNotNull();
        // health-check
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health")).andExpect(status().isOk());
    }

    @Test
    void saveSuccessfullyTest() throws Exception {

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

    @Test
    void getSuccessfullyTest() throws Exception {
        // save
        String request = classpathFileToString("/requests/user1.json");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));
        String userId = resultActions.andReturn().getResponse().getHeader("X-UserId");
        String userPath = resultActions.andReturn().getResponse().getHeader("Location");

        resultActions
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$",
                        equalTo(String.format("Пользователь Sobolev добавлен в базу с id = %s",
                                userId))));

        // get previously saved
        mockMvc.perform(MockMvcRequestBuilders.get(userPath)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(userId)));
    }
}