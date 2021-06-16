package io.wisoft.siabackend.service;

import io.wisoft.siabackend.domain.AreaOfInterest;
import io.wisoft.siabackend.domain.Region;
import io.wisoft.siabackend.infrastructure.AreaOfInterestRepository;
import io.wisoft.siabackend.infrastructure.RegionRepository;
import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.siabackend.domain.Region.createRegion;
import static io.wisoft.siabackend.util.GeometryUtil.makePolygon;
import static io.wisoft.siabackend.util.GeometryUtil.wktToGeometry;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;
  private final AreaOfInterestRepository aoiRepository;

  public Long regionRegister(RegionRegisterDTO registerDTO) {
    String polygon = makePolygon(registerDTO.getArea());
    Geometry geoPolygon = wktToGeometry(polygon);

    Region region = createRegion(registerDTO.getName(), (Polygon) geoPolygon);
    regionRepository.save(region);

    return region.getId();
  }

  public List<AreaOfInterest> findAreaOfInterestsIntersectedRegion(Long id) {
    return aoiRepository.findAreaOfInterestsIntersectedRegion(id);
  }

}
