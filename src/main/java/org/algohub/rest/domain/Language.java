package org.algohub.rest.domain;

import java.io.Serializable;
import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Language implements Serializable {

  private static final long serialVersionUID = 40000L;

  @Column(unique=true, nullable = false)
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @NotNull
  private Integer id;

  @Column(unique=true, nullable = false, length = 45)
  @NotNull
  @Size(min = 2, max = 45)
  private String language;

  protected Language() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public Language(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
