package com.github.jabinespbi.oneapp.usercenter.controller;

import com.github.jabinespbi.oneapp.usercenter.payload.JwtResponse;
import com.github.jabinespbi.oneapp.usercenter.payload.SamlAssertion;
import com.github.jabinespbi.oneapp.usercenter.security.jwt.JwtUtils;
import com.github.jabinespbi.oneapp.usercenter.security.services.UserDetailsImpl;
import com.github.jabinespbi.oneapp.usercenter.security.sso.SamlAssertionExtractor;
import jakarta.validation.Valid;
import org.opensaml.saml2.core.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final SamlAssertionExtractor samlAssertionExtractor;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          SamlAssertionExtractor samlAssertionExtractor) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.samlAssertionExtractor = samlAssertionExtractor;
    }

    @PostMapping("/sso/acs")
    public ResponseEntity<?> login(@Valid @RequestBody SamlAssertion samlAssertion) {
        Assertion assertion = samlAssertionExtractor.extractAssertion(samlAssertion.getSAMLResponse());
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(samlAssertion.getUsername(), samlAssertion.getPassword());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtUtils.generateJwtToken(authentication);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var jwtResponse = new JwtResponse(
                jwt,
                userDetails.id(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok(jwtResponse);
    }
}
