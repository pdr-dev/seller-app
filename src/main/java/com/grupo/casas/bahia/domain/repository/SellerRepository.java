package com.grupo.casas.bahia.domain.repository;

import com.grupo.casas.bahia.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByDocument(String document);

    @Query(value = "SELECT " +
            " MAX(CAST(SUBSTRING(matricula FROM '^[0-9]+') AS INTEGER)) + 1 " +
            " FROM " +
            " tb_vendedor ", nativeQuery = true)
    Optional<String> findNextRegistrationNumber();

}
