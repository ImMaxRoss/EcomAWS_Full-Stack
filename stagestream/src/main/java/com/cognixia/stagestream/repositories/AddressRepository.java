package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    

    Optional<Address> findByStreetAndCityAndStateAndZipcode(String street, String city, String state, String zipcode);

}