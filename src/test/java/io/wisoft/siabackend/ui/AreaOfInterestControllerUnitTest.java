package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.application.AreaOfInterestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;
import java.util.Objects;

import static io.wisoft.siabackend.ui.AreaOfInterestController.*;
import static io.wisoft.siabackend.util.MakeDTO.createAOIRegisterDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
@Transactional
@SuppressWarnings("rawtypes")
public class AreaOfInterestControllerUnitTest {

  AreaOfInterestController controller;

  @Mock
  AreaOfInterestService service;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  @BeforeEach
  public void setup() {
    this.controller = new AreaOfInterestController(service);
  }

  //unit test 이기 때문에 service 계층 이후는 모두 정상동작 된다.
  @Test
  @DisplayName("aoi 등록 테스트")
  public void registerAreaOfInterestTest() {
    //given
    AreaOfInterestRegisterDTO registerDTO = createAOIRegisterDTO();
    AreaOfInterestResponseDTO responseId = new AreaOfInterestResponseDTO(1L);

    //when
    when(service.areaOfInterestRegister(registerDTO)).thenReturn(1L);
    ResponseEntity<AreaOfInterestResponseDTO> response = controller.registerAreaOfInterest(registerDTO);

    //then
    assertEquals(CREATED, response.getStatusCode());
    assertEquals(responseId.getId(), Objects.requireNonNull(response.getBody()).getId());
  }

}
