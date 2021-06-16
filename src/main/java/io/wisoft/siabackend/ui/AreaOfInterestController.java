package io.wisoft.siabackend.ui;

import io.wisoft.siabackend.service.AreaOfInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/aois")
@RequiredArgsConstructor
public class AreaOfInterestController {

  private final AreaOfInterestService service;

  //관심 지역의 이름, 전체 지리 정보를 저장한다.
  @PostMapping
  public ResponseEntity<AreaOfInterestResponseDTO> registerAreaOfInterest(@RequestBody @Valid AreaOfInterestRegisterDTO registerDTO) {
    Long id = service.areaOfInterestRegister(registerDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(new AreaOfInterestResponseDTO(id));
  }
}
