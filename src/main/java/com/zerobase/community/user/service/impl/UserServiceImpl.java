package com.zerobase.community.user.service.impl;

import com.zerobase.community.user.entity.User;
import com.zerobase.community.user.model.UserInput;
import com.zerobase.community.user.repository.UserRepository;
import com.zerobase.community.user.service.UserService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public boolean register(UserInput parameter) {
		Optional<User> optionalUser = userRepository.findByUserEmail(parameter.getUserEmail());
		if (optionalUser.isPresent()) {
			return false;
		}

		String encPassword = BCrypt.hashpw(parameter.getUserPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		String birtStr = parameter.getUserBirth();
		LocalDate birth = LocalDate.parse(birtStr);

		User user = User.builder()
			.userEmail(parameter.getUserEmail())
			.userPassword(encPassword)
			.userName(parameter.getUserName())
			.userBirth(birth)
			.createAt(LocalDate.now())
			.build();

		userRepository.save(user);

		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> optionalUser = userRepository.findById(username);
//
//		if (!optionalUser.isPresent()) {
//			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
//		}
//
//		User user = optionalUser.get();
//
//
//	}
}
