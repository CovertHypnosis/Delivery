package com.example.delivery.models;

import java.io.Serializable;

public interface UserDetails extends Serializable {
    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();

}
