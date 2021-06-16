package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

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

  @AllArgsConstructor
  @Getter
  public static class RegionRegisterDTO {
    private final String name;
    private final List<Map<String, Double>> area;
  }

  @AllArgsConstructor
  @Getter
  public static class RegionResponseDTO {
    private final Long id;
  }

}
