package com.bpx.style_sphere_backend.repositories;

import com.bpx.style_sphere_backend.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
