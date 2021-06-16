package io.wisoft.siabackend.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryUtil {

  private static final WKTReader wktReader = new WKTReader();

  public static Geometry wktToGeometry(String wellKnownText) {
    Geometry geometry = null;

    try {
      geometry = wktReader.read(wellKnownText);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return geometry;
  }

}
