package io.wisoft.siabackend.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeometryUtil {

  private static final WKTReader wktReader = new WKTReader();

  //geometry 타입으로의 변환작업은 공통작업에 해당한다.
  public static Geometry wktToGeometry(String wellKnownText) {
    Geometry geometry = null;

    try {
      geometry = wktReader.read(wellKnownText);
      geometry.setSRID(4326);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return geometry;
  }

  //polygon을 만드는 작업은 region, aoi 의 공통 작업에 해당한다.
  public static String makePolygon(List<Map<String, Double>> area) {
    StringBuilder polygon = new StringBuilder("POLYGON((");
    for (Map<String, Double> stringDoubleMap : area) {
      polygon.append(stringDoubleMap.get("x")).append(" ").append(stringDoubleMap.get("y")).append(",");
    }
    polygon.deleteCharAt(polygon.length() - 1);
    polygon.append("))");
    return String.valueOf(polygon);
  }

  public static String makePoint(Double latitude, Double longitude) {
    return "POINT(" + latitude + " " + longitude + ")";
  }

  public static List<Map<String, Double>> makeArea(String area) {
    return Stream.of(area
        .replace("POLYGON ((", "")
        .replace("))", "")
        .split(","))
        .map(String::trim)
        .map(s -> s.split(" "))
        .map(s -> Map.of("x", Double.valueOf(s[0]), "y", Double.valueOf(s[1])))
        .collect(Collectors.toList());
  }

}
