package org.algohub.rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role implements GrantedAuthority {

  private static final long serialVersionUID = 50000L;

  @Column(unique=true, nullable = false, updatable = false)
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Min(0)
  @NotNull
  private Integer id;

  @Column(unique=true, nullable = false, updatable = false, length = 16)
  @NotEmpty
  private String name;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
  private Set<User> users = new HashSet<User>();

  protected Role() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public Role(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

}
