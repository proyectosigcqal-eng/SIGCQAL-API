package com.sigcqal.api.test.support;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sigcqal.api.security.JwtAuthenticationFilter;

@TestConfiguration
public class WebMvcTestSecurityConfig {

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
}
