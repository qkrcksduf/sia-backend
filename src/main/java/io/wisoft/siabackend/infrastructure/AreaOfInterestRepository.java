package io.wisoft.siabackend.infrastructure;

import io.wisoft.siabackend.domain.AreaOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaOfInterestRepository extends JpaRepository<AreaOfInterest, Long> {

  String selectAreaOfInterestIntersectedRegion = "SELECT DISTINCT a.id, a.name, a.area FROM aoi a, region r WHERE ST_Intersects(a.area, r.area) = true AND r.id=:id";

  @Query(nativeQuery = true, value = selectAreaOfInterestIntersectedRegion)
  List<AreaOfInterest> findAreaOfInterestsIntersectedRegion(Long id);


  String selectAreaOfInterestClosestSpecificCoordinate = "SELECT id, name, area FROM aoi a WHERE (ST_Distance(:point\\:\\:geography, a.area)) = (SELECT MIN(ST_Distance(:point\\:\\:geography, a.area)) FROM aoi a)";

  @Query(nativeQuery = true, value = selectAreaOfInterestClosestSpecificCoordinate)
  AreaOfInterest findAreaOfInterestClosestSpecificCoordinate(String point);

}
