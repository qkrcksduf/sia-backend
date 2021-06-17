package io.wisoft.siabackend.application;

import io.wisoft.siabackend.domain.AreaOfInterest;
import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;
import io.wisoft.siabackend.ui.AreaOfInterestController;
import io.wisoft.siabackend.ui.AreaOfInterestController.AreaOfInterestRegisterDTO;
import io.wisoft.siabackend.util.GeometryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.wisoft.siabackend.util.GeometryUtil.makePolygon;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@Transactional
@SuppressWarnings("rawtypes")
public class AreaOfInterestServiceUnitTest {

  AreaOfInterestService service;

  @Mock
  AreaOfInterestRepository repository;

  @BeforeEach
  public void setup() {
    this.service = new AreaOfInterestService(repository);
  }

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  //실제 서비스가 반환하는 값은 영속성 컨텍스트에 저장되어 있는 키 값이다. 하지만 키 값을 얻기 위해서는
  //Repository 와 통합테스트를 수행해야 하기때문에 단위테스트에서는 내부 메소드가 정상동작하는 지만 확인한다.
  @Test
  @DisplayName("aoi 등록 테스트")
  public void registerAreaOfInterestTest() {
    //given
    AreaOfInterestRegisterDTO registerDTO = createRegisterDTO();
    String expectedPolygon = "POLYGON((126.835 37.688,127.155 37.702,127.184 37.474,126.821 37.454,126.835 37.688))";

    //when
    String stringPolygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = GeometryUtil.wktToGeometry(stringPolygon);
    Polygon polygon = (Polygon) geoPolygon;

    //then
    assertEquals(expectedPolygon, stringPolygon);
    assertEquals(Polygon.class, polygon.getClass());
  }

  private AreaOfInterestRegisterDTO createRegisterDTO() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 126.835, "y", 37.688),
        Map.of("x", 127.155, "y", 37.702),
        Map.of("x", 127.184, "y", 37.474),
        Map.of("x", 126.821, "y", 37.454),
        Map.of("x", 126.835, "y", 37.688)
    );

    return new AreaOfInterestRegisterDTO("서울시", area);
  }
}
