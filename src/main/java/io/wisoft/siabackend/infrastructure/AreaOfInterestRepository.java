package io.wisoft.siabackend.infrastructure;

import io.wisoft.siabackend.domain.AreaOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaOfInterestRepository extends JpaRepository<AreaOfInterest, Long> {
}
