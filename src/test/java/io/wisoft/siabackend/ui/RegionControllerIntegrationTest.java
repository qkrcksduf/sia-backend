package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.core.Is.is;

import java.io.File;

import static io.wisoft.siabackend.util.MakeDTO.createRegionRegisterDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@SuppressWarnings("rawtypes")
public class RegionControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  // MockMvc를 통해 HTTP 요청을 한다.
  // Spring Container, Dispatcher Servlet등 Spring의 모든 동작과 controller, service, repository가 모두 정상동작하는지 테스트한다.
  @Test
  public void registerRegionTest() throws Exception {
    //given
    RegionRegisterDTO registerDTO = createRegionRegisterDTO();

    //when
    ResultActions resultActions = mvc.perform(post("/regions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDTO)))
        .andDo(print());

    //then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.id", is(1)));
  }

}
