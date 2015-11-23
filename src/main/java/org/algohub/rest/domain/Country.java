package org.algohub.rest.domain;

import java.io.Serializable;
import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Country implements Serializable {

  private static final long serialVersionUID = 20000L;

  @Column(unique=true, nullable = false)
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @NotNull
  private Integer id;

  @Column(name = "country_code", columnDefinition="CHAR(2)", unique=true, nullable = false)
  @NotNull
  @Size(min = 2, max = 2)
  private String countryCode;

  @Column(name = "country_name", unique=true, nullable = false, length = 45)
  @NotNull
  @Size(min = 2, max = 45)
  private String countryName;

  protected Country() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public Country(String countryCode, String countryName) {
    this.countryCode = countryCode;
    this.countryName = countryName;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }
}
