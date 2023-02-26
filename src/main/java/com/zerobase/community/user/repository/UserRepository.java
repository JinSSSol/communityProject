package com.zerobase.community.user.repository;

import com.zerobase.community.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserEmail(String userEmail);

}
