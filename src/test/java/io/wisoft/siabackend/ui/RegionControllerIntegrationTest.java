package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.application.AreaOfInterestService;
import io.wisoft.siabackend.application.RegionService;
import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.wisoft.siabackend.ui.AreaOfInterestController.*;
import static io.wisoft.siabackend.util.MakeDTO.*;
import static org.hamcrest.core.Is.is;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  private RegionService regionService;

  @Autowired
  private AreaOfInterestService areaOfInterestService;

  @Autowired
  private ObjectMapper objectMapper;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  // MockMvc??? ?????? HTTP ????????? ??????.
  // Spring Container, Dispatcher Servlet??? Spring??? ?????? ????????? controller, service, repository??? ?????? ????????????????????? ???????????????.
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

  @Test
  @DisplayName("region ?????? DTO??? validaion ????????? methodArgumentException ?????????")
  public void registerRegionValidationFailTest() throws Exception {
    //given
    RegionRegisterDTO registerDTO = createNoNameRegionRegisterDTO();

    //when
    ResultActions resultActions = mvc.perform(post("/regions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDTO)))
        .andDo(print());

    //then
    resultActions.andExpect(status().isBadRequest());
  }

  // ?????? ???????????? ????????? ?????? ?????????????????? ?????? ???????????? ????????????.
  @Test
  @DisplayName("??????????????? ??????????????? ???????????? ???????????? ?????? ?????????")
  public void findAreaOfInterestsIntersectedRegionTest() throws Exception {
    //given
    RegionRegisterDTO regionRegisterDTO = createRegionRegisterDTO();
    AreaOfInterestRegisterDTO areaOfInterestRegisterDTO = createAOIRegisterDTO();
    regionService.regionRegister(regionRegisterDTO);
    areaOfInterestService.areaOfInterestRegister(areaOfInterestRegisterDTO);

    //when
    ResultActions resultActions = mvc.perform(get("/regions/1/aois/intersects"));

    //then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

}
