package io.wisoft.siabackend.service;

import io.wisoft.siabackend.domain.Region;
import io.wisoft.siabackend.infrastructure.RegionRepository;
import io.wisoft.siabackend.ui.RegionController;
import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.siabackend.domain.Region.createRegion;
import static io.wisoft.siabackend.util.GeometryUtil.makePolygon;
import static io.wisoft.siabackend.util.GeometryUtil.wktToGeometry;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository repository;

  public Long regionRegister(RegionRegisterDTO registerDTO) {
    String polygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = wktToGeometry(polygon);

    Region region = createRegion(registerDTO.getName(), (Polygon) geoPolygon);
    repository.save(region);

    return region.getId();
  }

}
