package io.wisoft.siabackend.infrastructure;

import io.wisoft.siabackend.domain.AreaOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaOfInterestRepository extends JpaRepository<AreaOfInterest, Long> {

  String selectAOIIntersectedRegion = "SELECT DISTINCT a.id, a.name, a.area FROM aoi a, region r WHERE st_intersects(a.area, r.area) = true AND r.id=:id";

  @Query(nativeQuery = true, value = selectAOIIntersectedRegion)
  List<AreaOfInterest> findAreaOfInterestsIntersectedRegion(Long id);

}
