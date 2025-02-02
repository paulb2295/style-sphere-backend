package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.models.dtos.AddressDTO;
import com.bpx.style_sphere_backend.models.entities.Address;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.repositories.AddressRepository;
import com.bpx.style_sphere_backend.repositories.UserRepository;
import com.bpx.style_sphere_backend.services.interfaces.AddressService;
import com.bpx.style_sphere_backend.utilities.AddressConverter;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addAddress(AddressDTO addressDTO, User user) {
        Address address = AddressConverter.toEntity(addressDTO);
        user.setAddress(address);
        return userRepository.save(
                user
        ).getAddress() != null;
    }

    @Override
    public AddressDTO getAddress(User user) {
        return AddressConverter.toDto(user.getAddress());
    }

    @Override
    public AddressDTO editAddress(AddressDTO addressDTO, User user) {
        user.setAddress(AddressConverter.toEntity(addressDTO));
        return AddressConverter.toDto(
                userRepository.save(user).getAddress()
        );
    }
}
