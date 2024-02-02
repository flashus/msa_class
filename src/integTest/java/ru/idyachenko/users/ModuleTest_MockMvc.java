package ru.idyachenko.users;

import com.github.tomakehurst.wiremock.client.WireMock;

import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.repository.UserRepository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

// import java.util.UUID;

// MOCKMVC (1)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("integTest-mockMvc")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// (3) KEYCLOAK Отключаем проверку токенов
// @Import({ PermitAllResourceServerWebSecurityConfig.class })
public class ModuleTest_MockMvc {

        // MOCKMVC (1)
        @Autowired
        private MockMvc mockMvc;

        // (5) PostgreSQL мокаем взаимодействие с БД
        @MockBean
        private UserRepository repository;

        // // (4) REST мокаем взаимодействие с REST
        // @BeforeEach
        // void initStubs() {
        // WireMock.reset();
        // validationRestStub(HttpStatus.OK,
        // "lesson2/responses/validate_OK.json");
        // }

        @Autowired
        @NonNull
        private WebApplicationContext webApplicationContext;

        @BeforeEach
        void init() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

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
