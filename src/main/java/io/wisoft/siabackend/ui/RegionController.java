package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.domain.AreaOfInterest;
import io.wisoft.siabackend.application.RegionService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

  private final RegionService service;

  //행정지역의 이름, 전체 지리 정보를 저장한다.
  @PostMapping
  public ResponseEntity<RegionResponseDTO> registerRegion(@RequestBody @Valid final RegionRegisterDTO registerDTO) {
    Long id = service.regionRegister(registerDTO);

    return ResponseEntity.status(CREATED).body(new RegionResponseDTO(id));
  }

  @GetMapping("/{region-id}/aois/intersects")
  public ResponseEntity<AreaOfInterestsResponseDTO> findAreaOfInterestsIntersectedRegion(@PathVariable("region-id") Long id) {
    List<AreaOfInterest> areaOfInterestIntersectedRegion = service.findAreaOfInterestsIntersectedRegion(id);
    System.out.println(areaOfInterestIntersectedRegion);

    return ResponseEntity.status(HttpStatus.OK).body(new AreaOfInterestsResponseDTO(areaOfInterestIntersectedRegion));
  }

  private static List<Map<String, Double>> makeArea(String area) {
    return Stream.of(area
        .replace("POLYGON ((", "")
        .replace("))", "")
        .split(","))
        .map(String::trim)
        .map(s -> s.split(" "))
        .map(s -> Map.of("x", Double.valueOf(s[0]), "y", Double.valueOf(s[1])))
        .collect(Collectors.toList());
  }

  @Getter
  public static class AreaOfInterestsResponseDTO {

    private final List<AreaOfInterestIntersectionRegionDTO> aois = new ArrayList<>();

    public AreaOfInterestsResponseDTO(List<AreaOfInterest> areaOfInterests) {
      for (AreaOfInterest areaOfInterest : areaOfInterests) {
        this.aois.add(new AreaOfInterestIntersectionRegionDTO(areaOfInterest));
      }
    }

  }


  @Getter
  public static class AreaOfInterestIntersectionRegionDTO {

    private final Long id;
    private final String name;
    private final List<Map<String, Double>> area;

    public AreaOfInterestIntersectionRegionDTO(final AreaOfInterest areaOfInterest) {
      this.id = areaOfInterest.getId();
      this.name = areaOfInterest.getName();
      this.area = makeArea(areaOfInterest.getArea().toString());
    }

  }

  @AllArgsConstructor
  @Getter
  public static class RegionRegisterDTO {

    @NotBlank
    private final String name;
    private final List<Map<String, Double>> area;

  }

  @AllArgsConstructor
  @Getter
  public static class RegionResponseDTO {

    private final Long id;

  }

}
