package com.cognixia.stagestream.services;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import com.cognixia.stagestream.models.Address;
import com.cognixia.stagestream.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('" + AppConstants.ADMIN_ROLE + "', '" + AppConstants.USER_ROLE + "')")
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public Address updateAddress(Long id, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setZipcode(updatedAddress.getZipcode());
        
        return addressRepository.save(existingAddress);
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        addressRepository.delete(address);
    }

    @PreAuthorize("hasAnyRole('" + AppConstants.ADMIN_ROLE + "', '" + AppConstants.USER_ROLE + "')")
    public Optional<Address> findByStreetAndCityAndStateAndZipcode(String street, String city, String state, String zipcode) {
        return addressRepository.findByStreetAndCityAndStateAndZipcode(street, city, state, zipcode);
    }
}