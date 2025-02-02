package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.aspects.CurrentUser;
import com.bpx.style_sphere_backend.models.dtos.AddressDTO;
import com.bpx.style_sphere_backend.models.entities.User;

public interface AddressService {
    @CurrentUser
    boolean addAddress(AddressDTO addressDTO, User user);

    @CurrentUser
    AddressDTO getAddress(User user);

    AddressDTO editAddress(AddressDTO addressDTO, User user);
}
