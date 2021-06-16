package io.wisoft.siabackend.application;

import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;
import io.wisoft.siabackend.infrastructure.RegionRepository;
import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;
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
public class RegionServiceUnitTest {

  RegionService service;

  @Mock
  RegionRepository regionRepository;

  @Mock
  AreaOfInterestRepository areaOfInterestRepository;

  @Container
  static DockerComposeContainer postgreSQLContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  @BeforeEach
  public void setup() {
    this.service = new RegionService(regionRepository, areaOfInterestRepository);
  }

  //실제 서비스가 반환하는 값은 영속성 컨텍스트에 저장되어 있는 키 값이다. 하지만 키 값을 얻기 위해서는
  //Repository 와 통합테스트를 수행해야 하기때문에 단위테스트에서는 내부 메소드가 정상동작하는 지만 확인한다.
  @Test
  @DisplayName("region 등록 테스트")
  public void registerRegion() {
    //given
    RegionRegisterDTO registerDTO = createRegisterDTO();
    String expectedPolygon = "POLYGON((127.02 37.742,127.023 37.664,126.945 37.605,126.962 37.692,127.02 37.742))";

    //when
    String stringPolygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = GeometryUtil.wktToGeometry(stringPolygon);
    Polygon polygon = (Polygon) geoPolygon;

    //then
    assertEquals(expectedPolygon, stringPolygon);
    assertEquals(Polygon.class, polygon.getClass());
  }

  private RegionRegisterDTO createRegisterDTO() {

    List<Map<String, Double>> area = List.of(
        Map.of("x", 127.02, "y", 37.742),
        Map.of("x", 127.023, "y", 37.664),
        Map.of("x", 126.945, "y", 37.605),
        Map.of("x", 126.962, "y", 37.692),
        Map.of("x", 127.02, "y", 37.742)
    );

    return new RegionRegisterDTO("북한산", area);
  }

}