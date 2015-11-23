package org.algohub.rest.domain;

import java.io.Serializable;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Entity
public class Industry implements Serializable {

  private static final long serialVersionUID = 30000L;

  @Column(unique=true, nullable = false)
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @NotNull
  private Integer id;

  @Column(unique=true, nullable = false, length = 45)
  @NotNull
  @Size(min = 2, max = 45)
  private String industry;

  @Column(nullable = false)
  @NotNull
  @Min(1)
  @Max(2)
  private Integer level;

  protected Industry() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public Industry(String industry, Integer level) {
    this.industry = industry;
    this.level = level;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }
}
