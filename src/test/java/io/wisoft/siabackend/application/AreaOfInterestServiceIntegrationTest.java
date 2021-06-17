package io.wisoft.siabackend.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;

import static io.wisoft.siabackend.ui.AreaOfInterestController.*;
import static io.wisoft.siabackend.util.MakeDTO.createAOIRegisterDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@SuppressWarnings("rawtypes")
@Transactional
public class AreaOfInterestServiceIntegrationTest {

  @Autowired
  AreaOfInterestService service;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  //실제 해당 서비스의 메소드를 호출하여 서비스의 기능과 레파지토리에 저장되는 동작까지 통합 테스트한다.
  @Test
  @DisplayName("aoi 등록 테스트")
  public void registerAreaOfInterestTest() {
    //given
    AreaOfInterestRegisterDTO registerDTO = createAOIRegisterDTO();
    Long expectedId = 1L;

    //when
    Long id = service.areaOfInterestRegister(registerDTO);

    //then
    assertEquals(expectedId, id);
  }

}
