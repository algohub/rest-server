package org.algohub.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User implements Serializable {

  private static final long serialVersionUID = 10000L;

  public enum Gender {
    Male,
    Female,
    MtF,
    FtM
  }

  public enum Occupation {
    Student,
    Professional
  }

  @Column(unique=true, nullable = false, updatable = false)
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Min(0)
  private Long id;

  @Column(unique=true, nullable = false, length = 16)
  @NotNull
  @Size(min = 3, max = 16)
  @Pattern(regexp = "[a-z0-9_-]{3,16}")
  private String username;

  @Column(name = "password_hash", nullable = false, length = 60)
  @NotNull
  @Size(min = 60, max = 60)
  @JsonIgnore
  private String passwordHash;

  @Column(unique=true, nullable = false, length = 255)
  @NotNull
  @Size(min = 5, max = 255)
  @Email
  private String email;

  @Column(nullable = false, length = 8)
  @Enumerated(EnumType.STRING)
  @NotNull
  private Gender gender;

  @Column(nullable = false)
  @NotNull
  private @Version @JsonIgnore Integer version;

  @Column(nullable = false, length = 12)
  @Enumerated(EnumType.STRING)
  @NotNull
  private Occupation occupation;

  @Column(name = "registered_at", nullable = false)
  @NotNull
  private java.sql.Timestamp registeredAt;

  @Column(nullable = false, columnDefinition = "bit(1) DEFAULT 1")
  @NotNull
  private Boolean enabled;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
  private Set<Role> roles = new HashSet<>();

  @Column
  @Past
  private java.sql.Date birthday;

  @ManyToOne
  @JoinColumn(name = "country", referencedColumnName="id")
  private Country country;

  @ManyToOne
  @JoinColumn(name = "language", referencedColumnName="id")
  private Language language;

  @ManyToOne
  @JoinColumn(name = "industry", referencedColumnName="id")
  private Industry industry;

  @Column(length = 128)
  @Size(min = 16, max = 128)
  @URL
  private String avatar;

  @Column(length = 128)
  @Size(min = 16, max = 128)
  @URL
  private String website;

  @PrePersist
  protected void onCreate() {
    registeredAt = new java.sql.Timestamp(System.currentTimeMillis());
  }

  protected User() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
  }

  public User(String username, String passwordHash, String email, Gender gender,
      Occupation occupation, Timestamp registeredAt, Date birthday, Country country,
      Language language, Industry industry, String avatar, String website) {
    this.username = username;
    this.passwordHash = passwordHash;
    this.email = email;
    this.gender = gender;
    this.occupation = occupation;
    this.registeredAt = registeredAt;
    this.enabled = true;
    this.birthday = birthday;
    this.country = country;
    this.language = language;
    this.industry = industry;
    this.avatar = avatar;
    this.website = website;
  }

  public User(String username, String passwordHash, String email, Gender gender,
      Occupation occupation) {
    this.username = username;
    this.passwordHash = passwordHash;
    this.email = email;
    this.gender = gender;
    this.occupation = occupation;
    this.registeredAt = new Timestamp(System.currentTimeMillis());
    this.enabled = true;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Occupation getOccupation() {
    return occupation;
  }

  public void setOccupation(Occupation occupation) {
    this.occupation = occupation;
  }

  public Timestamp getRegisteredAt() {
    return registeredAt;
  }

  public void setRegisteredAt(Timestamp registeredAt) {
    this.registeredAt = registeredAt;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Industry getIndustry() {
    return industry;
  }

  public void setIndustry(Industry industry) {
    this.industry = industry;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }
}
