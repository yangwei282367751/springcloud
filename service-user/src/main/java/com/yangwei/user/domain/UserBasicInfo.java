package com.yangwei.user.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author 杨威
 */
@Data
@Entity
@Table(name="T_USER_BASIC_INFO")
public class UserBasicInfo{

  @Id
  @GeneratedValue(generator = "user-uuid")
  @GenericGenerator(name="user-uuid", strategy = "uuid")
  @Column(name="user_id")
  private String userId;

  @Column(name="user_name")
  private String username;

  @Column(name="password")
  private String password;

  @Column(name="is_enabled")
  private boolean isEnabled;

  @Column(name="create_time")
  private long createTime;

  @Column(name="last_login_time")
  private long lastLoginTime;

  @Column(name="is_credentials_non_expired")
  private boolean isCredentialsNonExpired;

  @Column(name="is_account_non_locked")
  private boolean isAccountNonLocked;

  @Column(name="is_account_non_expired")
  private boolean isAccountNonExpired;

}
