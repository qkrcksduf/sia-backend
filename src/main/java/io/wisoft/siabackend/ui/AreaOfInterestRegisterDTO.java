package io.wisoft.siabackend.ui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.geo.Polygon;

import javax.validation.constraints.NotBlank;
import java.util.*;

@AllArgsConstructor
@Getter
@Data
public class AreaOfInterestRegisterDTO {

  @NotBlank
  public String name;

  public List<Map<String, Double>> area;

//  @Data
//  public static class Location {
//    double x;
//    double y;
//  }

}
