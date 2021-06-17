package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.application.RegionService;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.*;

import static io.wisoft.siabackend.ui.RegionController.*;
import static io.wisoft.siabackend.util.MakeDTO.createRegionRegisterDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
@Testcontainers
@Transactional
@SuppressWarnings("rawtypes")
class RegionControllerUnitTest {

  RegionController controller;

  @Mock
  RegionService service;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  @BeforeEach
  public void setup() {
    this.controller = new RegionController(service);
  }

  //unit test 이기 때문에 service 계층 이후는 모두 정상동작 된다.
  @Test
  @DisplayName("region 등록 테스트")
  public void registerRegionTest() {
    //given
    RegionRegisterDTO registerDTO = createRegionRegisterDTO();
    RegionResponseDTO responseId = new RegionResponseDTO(1L);

    //when
    when(service.regionRegister(registerDTO)).thenReturn(1L);
    ResponseEntity<RegionResponseDTO> response = controller.registerRegion(registerDTO);

    //then
    assertEquals(CREATED, response.getStatusCode());
    assertEquals(responseId.getId(), Objects.requireNonNull(response.getBody()).getId());
  }

}