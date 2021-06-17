package io.wisoft.siabackend.util;

import io.wisoft.siabackend.ui.RegionController.RegionRegisterDTO;

import java.util.List;
import java.util.Map;

import static io.wisoft.siabackend.ui.AreaOfInterestController.*;

public class MakeDTO {

  public static RegionRegisterDTO createRegionRegisterDTO() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 126.835, "y", 37.688),
        Map.of("x", 127.155, "y", 37.702),
        Map.of("x", 127.184, "y", 37.474),
        Map.of("x", 126.821, "y", 37.454),
        Map.of("x", 126.835, "y", 37.688)
    );

    RegionRegisterDTO registerDTO = new RegionRegisterDTO();
    registerDTO.setName("서울시");
    registerDTO.setArea(area);
    return registerDTO;
  }

  public static RegionRegisterDTO createNoNameRegionRegisterDTO() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 126.835, "y", 37.688),
        Map.of("x", 127.155, "y", 37.702),
        Map.of("x", 127.184, "y", 37.474),
        Map.of("x", 126.821, "y", 37.454),
        Map.of("x", 126.835, "y", 37.688)
    );

    RegionRegisterDTO registerDTO = new RegionRegisterDTO();
    registerDTO.setArea(area);
    return registerDTO;
  }

  public static RegionRegisterDTO createRegionNoClosedLinestring() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 126.835, "y", 37.688),
        Map.of("x", 127.155, "y", 37.702),
        Map.of("x", 127.184, "y", 37.474),
        Map.of("x", 126.821, "y", 37.454)
    );

    RegionRegisterDTO registerDTO = new RegionRegisterDTO();
    registerDTO.setArea(area);
    return registerDTO;
  }

  public static AreaOfInterestRegisterDTO createAOIRegisterDTO() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 127.02, "y", 37.742),
        Map.of("x", 127.023, "y", 37.664),
        Map.of("x", 126.945, "y", 37.605),
        Map.of("x", 126.962, "y", 37.692),
        Map.of("x", 127.02, "y", 37.742)
    );

    AreaOfInterestRegisterDTO registerDTO = new AreaOfInterestRegisterDTO();
    registerDTO.setName("북한산");
    registerDTO.setArea(area);
    return registerDTO;
  }

  public static AreaOfInterestRegisterDTO createAOINoNameRegisterDTO() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 127.02, "y", 37.742),
        Map.of("x", 127.023, "y", 37.664),
        Map.of("x", 126.945, "y", 37.605),
        Map.of("x", 126.962, "y", 37.692),
        Map.of("x", 127.02, "y", 37.742)
    );

    AreaOfInterestRegisterDTO registerDTO = new AreaOfInterestRegisterDTO();
    registerDTO.setArea(area);
    return registerDTO;
  }

  public static AreaOfInterestRegisterDTO createAOINoClosedLinestring() {
    List<Map<String, Double>> area = List.of(
        Map.of("x", 127.02, "y", 37.742),
        Map.of("x", 127.023, "y", 37.664),
        Map.of("x", 126.945, "y", 37.605),
        Map.of("x", 126.962, "y", 37.692)
    );

    AreaOfInterestRegisterDTO registerDTO = new AreaOfInterestRegisterDTO();
    registerDTO.setName("북한산");
    registerDTO.setArea(area);
    return registerDTO;
  }

}
