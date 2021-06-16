package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.service.AreaOfInterestService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

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
