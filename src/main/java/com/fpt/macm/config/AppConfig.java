package com.fpt.macm.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppConfig {
	private List<String> authorizedRedirectUris = new ArrayList<>();

	private String tokenSecret;

	private long tokenExpirationMsec;

	public AppConfig authorizedRedirectUris(List<String> authorizedRedirectUris) {
		this.authorizedRedirectUris = authorizedRedirectUris;
		return this;
	}

	public List<String> getAuthorizedRedirectUris() {
		return authorizedRedirectUris;
	}

	public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
		this.authorizedRedirectUris = authorizedRedirectUris;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public long getTokenExpirationMsec() {
		return tokenExpirationMsec;
	}

	public void setTokenExpirationMsec(long tokenExpirationMsec) {
		this.tokenExpirationMsec = tokenExpirationMsec;
	}

}
