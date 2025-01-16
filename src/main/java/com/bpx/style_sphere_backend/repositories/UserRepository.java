package com.bpx.style_sphere_backend.repositories;

import com.bpx.style_sphere_backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
