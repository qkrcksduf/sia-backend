package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.application.AreaOfInterestService;
import io.wisoft.siabackend.domain.AreaOfInterest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static io.wisoft.siabackend.util.GeometryUtil.makeArea;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/aois")
@RequiredArgsConstructor
public class AreaOfInterestController {

  private final AreaOfInterestService service;

  // 관심 지역의 이름, 전체 지리 정보를 저장한다.
  @PostMapping
  public ResponseEntity<AreaOfInterestResponseDTO> registerAreaOfInterest(@RequestBody @Valid final AreaOfInterestRegisterDTO registerDTO) {
    Long id = service.areaOfInterestRegister(registerDTO);

    return ResponseEntity.status(CREATED).body(new AreaOfInterestResponseDTO(id));
  }

  @GetMapping()
  public ResponseEntity<AreaOfInterestClosestSpecificCoordinate> findAreaOfInterestClosestSpecificCoordinate(@RequestParam("lat") Double latitude, @RequestParam("long") Double longitude) {
    AreaOfInterest areaOfInterestClosestSpecificCoordinate = service.findAreaOfInterestClosestSpecificCoordinate(latitude, longitude);

    return ResponseEntity.status(OK).body(new AreaOfInterestClosestSpecificCoordinate(areaOfInterestClosestSpecificCoordinate));
  }

  @Getter
  public static class AreaOfInterestClosestSpecificCoordinate {

    private final Long id;
    private final String name;
    private final List<Map<String, Double>> area;

    public AreaOfInterestClosestSpecificCoordinate(final AreaOfInterest areaOfInterest) {
      this.id = areaOfInterest.getId();
      this.name = areaOfInterest.getName();
      this.area = makeArea(areaOfInterest.getArea().toString());
    }

  }

  // 현재 시점에서는 구현해야 하는 기능이 많이 없기 때문에 DTO는 해당 DTO를 사용하는 inner 클래스로 만듬.
  @AllArgsConstructor
  @Getter
  public static class AreaOfInterestRegisterDTO {

    @NotBlank
    private final String name;
    private final List<Map<String, Double>> area;

  }

  @AllArgsConstructor
  @Getter
  public static class AreaOfInterestResponseDTO {

    private final Long id;

  }

}
