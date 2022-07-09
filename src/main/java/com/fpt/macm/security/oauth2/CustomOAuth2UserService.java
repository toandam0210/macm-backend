package com.fpt.macm.security.oauth2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.security.UserPrincipal;
import com.fpt.macm.security.user.OAuth2UserInfo;
import com.fpt.macm.security.user.OAuth2UserInfoFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
		User user = new User();
		if (StringUtils.endsWithIgnoreCase(oAuth2UserInfo.getEmail(), "fpt.edu.vn")) {
			Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
			if (userOptional.isPresent()) {
					User currentUser = userOptional.get();
					mappedAuthorities.addAll(AuthorityUtils.createAuthorityList(currentUser.getRole().getName()));
				}
			} else {
				mappedAuthorities.addAll(AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
			}
		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

}
