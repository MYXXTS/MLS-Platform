package com.myxxts.mls.server.model.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document (collection = "user")
public class User implements UserDetails {

  @Id
  private ObjectId uid; // User id

  private Integer articleCount; // User article count

  private Integer commentCount; // User comment count

  private String nickname; // User nickname

  private String username; // User username

  private String password; // User password

  private String email; // User email

  private String website; // User website

  private String avatar; // User avatar

  private String description; // User description

  private Set<ObjectId> rids; // User role

  private Set<String> token; // User token

  private LocalDateTime createTime; // User create time

  private LocalDateTime updateTime; // User update time

  private LocalDateTime lastLoginTime; // User last login time

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities () {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

}
