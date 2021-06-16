package io.wisoft.siabackend.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "aoi")
public class AreaOfInterest {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "bigserial")
  private Long id;

  @Column(columnDefinition = "varchar(30)")
  private String name;

  @Column(columnDefinition = "geometry('Polygon', 4326)")
  private Polygon area;

  public AreaOfInterest(String name, Polygon polygon) {
    this.name = name;
    this.area = polygon;
  }

}
