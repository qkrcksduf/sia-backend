package io.wisoft.siabackend.application;

import io.wisoft.siabackend.domain.AreaOfInterest;
import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;

import io.wisoft.siabackend.ui.AreaOfInterestController.AreaOfInterestRegisterDTO;
import io.wisoft.siabackend.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.siabackend.domain.AreaOfInterest.createAreaOfInterest;
import static io.wisoft.siabackend.util.GeometryUtil.makePoint;
import static io.wisoft.siabackend.util.GeometryUtil.makePolygon;

@Service
@Transactional
@RequiredArgsConstructor
public class AreaOfInterestService {

  private final AreaOfInterestRepository repository;

  public Long areaOfInterestRegister(final AreaOfInterestRegisterDTO registerDTO) {
    String polygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = GeometryUtil.wktToGeometry(polygon);

    AreaOfInterest areaOfInterest = createAreaOfInterest(registerDTO.getName(), (Polygon) geoPolygon);
    repository.save(areaOfInterest);

    return areaOfInterest.getId();
  }

  public AreaOfInterest findAreaOfInterestClosestSpecificCoordinate(final Double latitude, final Double longitude) {
    String point = makePoint(latitude, longitude);

    return repository.findAreaOfInterestClosestSpecificCoordinate(point);
  }

}
