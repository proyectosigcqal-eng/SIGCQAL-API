package com.sigcqal.api.infra.Auth.Entity;

import java.io.Serializable;

public record UserRoleId(Long user, Integer role) implements Serializable {}
