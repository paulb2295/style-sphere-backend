package com.bpx.style_sphere_backend.controllers;

import com.bpx.style_sphere_backend.aspects.CurrentUser;
import com.bpx.style_sphere_backend.models.dtos.AddressDTO;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.services.interfaces.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    @CurrentUser
    public ResponseEntity<Boolean> addAddress(@RequestBody AddressDTO addressDTO, User user) {
        return ResponseEntity.status(201).body(addressService.addAddress(addressDTO, user));
    }

    @GetMapping
    @CurrentUser
    public ResponseEntity<AddressDTO> getAddress(User user) {
        return ResponseEntity.status(200).body(addressService.getAddress(user));
    }

    @PutMapping
    @CurrentUser
    public ResponseEntity<AddressDTO> editAddress(@RequestBody AddressDTO addressDTO, User user) {
        return ResponseEntity.status(201).body(addressService.editAddress(addressDTO, user));
    }


}
