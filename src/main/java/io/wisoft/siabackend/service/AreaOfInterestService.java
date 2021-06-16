package io.wisoft.siabackend.service;

import io.wisoft.siabackend.domain.AreaOfInterest;
import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;
import io.wisoft.siabackend.ui.AreaOfInterestRegisterDTO;
import io.wisoft.siabackend.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AreaOfInterestService {

  private final AreaOfInterestRepository repository;

  @Transactional
  public Long areaOfInterestRegister(AreaOfInterestRegisterDTO registerDTO) {
    String stringPolygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = GeometryUtil.wktToGeometry(stringPolygon);
    AreaOfInterest areaOfInterest = new AreaOfInterest(registerDTO.getName(), (Polygon) geoPolygon);
    repository.save(areaOfInterest);
    return areaOfInterest.getId();
  }

  private String makePolygon(List<Map<String, Double>> area) {
    StringBuilder polygon = new StringBuilder("POLYGON((");
    for (Map<String, Double> stringDoubleMap : area) {
      polygon.append(stringDoubleMap.get("x")).append(" ").append(stringDoubleMap.get("y")).append(",");
    }
    polygon.deleteCharAt(polygon.length() - 1);
    polygon.append("))");
    System.out.println(polygon);
    System.out.println(polygon.length());
    return String.valueOf(polygon);
  }

}
