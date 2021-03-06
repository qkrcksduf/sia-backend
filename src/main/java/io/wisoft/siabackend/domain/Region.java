package io.wisoft.siabackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@SequenceGenerator(name = "region_sequence_generator")
public class Region {

  @Id
  @GeneratedValue(generator = "region_sequence_generator")
  @Column(columnDefinition = "bigserial")
  private Long id;

  @Column(unique = true, columnDefinition = "varchar(30)", nullable = false)
  private String name;

  @Column(columnDefinition = "geometry('Polygon', 4326)")
  private Polygon area;

  //도메인을 생성하는 작업은 도메인 로직에 해당한다.
  public static Region createRegion(String name, Polygon area) {
    Region region = new Region();
    region.name = name;
    region.area = area;

    return region;
  }

}
