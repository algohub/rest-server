package org.algohub.rest.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Question implements Serializable {
  private static final long serialVersionUID = 50000L;

  @Column(unique=true, nullable = false, length = 191)
  @Id
  @Size(min = 3, max = 191)
  private String id;

  @Column(unique=true, nullable = false, columnDefinition = "TEXT")
  @NotNull
  @Size(min = 16, max = 65535)
  private String json;

  protected Question() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public Question(String id, String json) {
    this.id = id;
    this.json = json;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }
}
