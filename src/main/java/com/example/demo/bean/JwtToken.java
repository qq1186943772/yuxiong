package com.example.demo.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

@SuppressWarnings("serial")
@NoArgsConstructor
@Data
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
    	this.token = token;
    }
    
    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public String getCredentials() {
        return token;
    }
}
