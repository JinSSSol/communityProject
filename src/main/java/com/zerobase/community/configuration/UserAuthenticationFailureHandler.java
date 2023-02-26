package com.zerobase.community.configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String errorMessage;
		if (exception instanceof BadCredentialsException
			|| exception instanceof InternalAuthenticationServiceException){
			errorMessage="아이디 또는 비밀번호가 맞지 않습니다.";
		}else if (exception instanceof UsernameNotFoundException){
			errorMessage="존재하지 않는 아이디 입니다.";
		}
		else{
			errorMessage="알 수 없는 이유로 로그인이 안되고 있습니다.";
		}


		setUseForward(true);
		setDefaultFailureUrl("/user/login?error=true");
		request.setAttribute("errorMessage", errorMessage);

		System.out.println("로그인에 실패하였습니다.");

		super.onAuthenticationFailure(request, response, exception);
	}
}
