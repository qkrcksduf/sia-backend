package io.wisoft.siabackend.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.siabackend.application.AreaOfInterestService;
import io.wisoft.siabackend.ui.AreaOfInterestController.AreaOfInterestRegisterDTO;
import org.junit.jupiter.api.DisplayName;
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

import java.io.File;

import static io.wisoft.siabackend.util.MakeDTO.createAOIRegisterDTO;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@SuppressWarnings("rawtypes")
public class AreaOfInterestControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AreaOfInterestService areaOfInterestService;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  // MockMvc를 통해 HTTP 요청을 한다.
  // Spring Container, Dispatcher Servlet등 Spring의 모든 동작과 controller, service, repository가 모두 정상동작하는지 테스트한다.
  @Test
  @DisplayName("aoi 등록 테스트")
  public void registerAreaOfInterestTest() throws Exception {
    //given
    AreaOfInterestRegisterDTO registerDTO = createAOIRegisterDTO();

    //when
    ResultActions resultActions = mvc.perform(post("/regions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDTO)))
        .andDo(print());

    //then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.id", is(1)));
  }

  //조회 테스트는 삽입도 함께 이루어지므로 통합 테스트만 수행한다.
  @Test
  @DisplayName("특정 좌표에 가장 가까운 관심지역 조회 테스트")
  public void findAreaOfInterestsIntersectedRegionTest() throws Exception {
    //given
    AreaOfInterestRegisterDTO areaOfInterestRegisterDTO = createAOIRegisterDTO();
    areaOfInterestService.areaOfInterestRegister(areaOfInterestRegisterDTO);

    //when
    ResultActions resultActions = mvc.perform(get("/aois?lat=126.837&long=37.689"));

    //then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

}
