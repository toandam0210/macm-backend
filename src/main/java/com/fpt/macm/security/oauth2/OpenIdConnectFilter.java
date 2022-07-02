package com.fpt.macm.security.oauth2;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;

public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

	@Value("${google.clientId}")
	private String clientId;

	@Value("${google.issuer}")
	private String issuer;

	@Value("${google.jwkUrl}")
	private String jwkUrl;

	@Autowired
	UserRepository userRepository;

	public OAuth2RestOperations restTemplate;

	public OpenIdConnectFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(new NoopAuthenticationManager());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		OAuth2AccessToken accessToken;
		try {
			accessToken = restTemplate.getAccessToken();
		} catch (OAuth2Exception e) {
			throw new BadCredentialsException("Could not obtain access token", e);
		}

		try {
			final String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
			String kid = JwtHelper.headers(idToken).get("kid");
			
			final Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(kid));
			final Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);
			verifyClaims(authInfo);
			final OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo, accessToken);
//			Optional<User> userOptional = userRepository.findByEmail(user.getUsername());
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			if (StringUtils.endsWithIgnoreCase(user.getUsername(), "fpt.edu.vn")) {
				Optional<User> userOptional = userRepository.findByEmail(user.getUsername());
				if (userOptional.isPresent()) {
					User currentUser = userOptional.get();
					mappedAuthorities.addAll(AuthorityUtils.createAuthorityList(currentUser.getRole().getName()));
				}
			} else {
				mappedAuthorities.addAll(AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
			}
			final OpenIdConnectUserDetails newUser = new OpenIdConnectUserDetails(authInfo, accessToken,
					mappedAuthorities);
			return new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
		} catch (final Exception e) {
			throw new BadCredentialsException("Could not obtain user details from token", e);
		}
	}

	public void verifyClaims(Map claims) {
		int exp = (int) claims.get("exp");
		Date expireDate = new Date(exp * 1000L);
		Date now = new Date();
		if (expireDate.before(now) || !claims.get("iss").equals(issuer) || !claims.get("aud").equals(clientId)) {
			throw new RuntimeException("Invalid claims");
		}
	}

	private RsaVerifier verifier(String kid) throws Exception {
		JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
		Jwk jwk = provider.get(kid);
		return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
	}

	public void setRestTemplate(OAuth2RestTemplate restTemplate2) {
		restTemplate = restTemplate2;

	}

	private static class NoopAuthenticationManager implements AuthenticationManager {

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
		}

	}

}
