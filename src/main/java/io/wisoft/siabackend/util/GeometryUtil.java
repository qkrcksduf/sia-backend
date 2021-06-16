package io.wisoft.siabackend.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.List;
import java.util.Map;

public class GeometryUtil {

  private static final WKTReader wktReader = new WKTReader();

  //geometry 타입으로의 변환작업은 공통작업에 해당한다.
  public static Geometry wktToGeometry(String wellKnownText) {
    Geometry geometry = null;

    try {
      geometry = wktReader.read(wellKnownText);
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
    System.out.println(polygon);
    System.out.println(polygon.length());
    return String.valueOf(polygon);
  }

}
