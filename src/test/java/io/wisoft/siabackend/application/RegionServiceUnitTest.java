package io.wisoft.siabackend.application;

import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;
import io.wisoft.siabackend.infrastructure.RegionRepository;
import io.wisoft.siabackend.ui.AreaOfInterestController;
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

import static io.wisoft.siabackend.util.GeometryUtil.makePolygon;
import static io.wisoft.siabackend.util.MakeDTO.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  public void registerRegionTest() {
    //given
    RegionRegisterDTO registerDTO = createRegionRegisterDTO();
    String expectedPolygon = "POLYGON((126.835 37.688,127.155 37.702,127.184 37.474,126.821 37.454,126.835 37.688))";


    //when
    String stringPolygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = GeometryUtil.wktToGeometry(stringPolygon);
    Polygon polygon = (Polygon) geoPolygon;

    //then
    assertEquals(expectedPolygon, stringPolygon);
    assertEquals(Polygon.class, polygon.getClass());
  }

  @Test
  @DisplayName("region 등록시 polygon의 시작점과 끝점이 일치하지 않아 polygon을 만들수 없는 IllegalArgumentException 테스트")
  public void registerAreaOfInterestIllegalArgumentExceptionTest() {
    //given
    RegionRegisterDTO registerDTO = createRegionNoClosedLinestring();

    //then
    assertThrows(IllegalArgumentException.class, () -> {
      service.regionRegister(registerDTO);
    });
  }

}
