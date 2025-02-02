package com.bpx.style_sphere_backend.utilities;

import com.bpx.style_sphere_backend.models.dtos.AddressDTO;
import com.bpx.style_sphere_backend.models.entities.Address;

public class AddressConverter {

    public static AddressDTO toDto(Address address) {
        return new AddressDTO.Builder()
                .id(address.getId())
                .country(address.getCountry())
                .county(address.getCounty())
                .city(address.getCity())
                .street(address.getStreet())
                .number(address.getNumber())
                .build();
    }

    public static Address toEntity(AddressDTO addressDTO) {
        return new Address.Builder()
                .id(addressDTO.getId())
                .country(addressDTO.getCountry())
                .county(addressDTO.getCounty())
                .city(addressDTO.getCity())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .build();
    }
}
