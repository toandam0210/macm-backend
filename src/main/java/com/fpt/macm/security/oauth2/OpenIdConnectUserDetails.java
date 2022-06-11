//package com.fpt.macm.security.oauth2;
//
//import java.util.Collection;
//import java.util.Map;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//
//public class OpenIdConnectUserDetails implements UserDetails {
//
//	  private static final long serialVersionUID = 1L;
//
//	    private String userId;
//	    private String username;
//	    private OAuth2AccessToken token;
//	    private Collection<? extends GrantedAuthority> authorities;
//
//	    public OpenIdConnectUserDetails(Map<String, String> userInfo, OAuth2AccessToken token,
//	    		Collection<? extends GrantedAuthority> authorities) {
//	        this.userId = userInfo.get("sub");
//	        this.username = userInfo.get("email");
//	        this.token = token;
//	        this.authorities = authorities;
//	    }
//	    
//	    public OpenIdConnectUserDetails(Map<String, String> userInfo, OAuth2AccessToken token) {
//	        this.userId = userInfo.get("sub");
//	        this.username = userInfo.get("email");
//	        this.token = token;
//	    }
//	
//		public String getUserId() {
//			return userId;
//		}
//
//
//
//		public void setUserId(String userId) {
//			this.userId = userId;
//		}
//
//
//
//		public OAuth2AccessToken getToken() {
//			return token;
//		}
//
//
//
//		public void setToken(OAuth2AccessToken token) {
//			this.token = token;
//		}
//
//
//
//		public void setUsername(String username) {
//			this.username = username;
//		}
//
//
//
//		@Override
//		public Collection<? extends GrantedAuthority> getAuthorities() {
//			return authorities;
//		}
//
//		@Override
//		public String getPassword() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public String getUsername() {
//			// TODO Auto-generated method stub
//			return username;
//		}
//
//		@Override
//		public boolean isAccountNonExpired() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isAccountNonLocked() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isCredentialsNonExpired() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isEnabled() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//}
