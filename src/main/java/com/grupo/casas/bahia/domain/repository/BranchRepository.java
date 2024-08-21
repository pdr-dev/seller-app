package com.grupo.casas.bahia.domain.repository;

import com.grupo.casas.bahia.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByDocument(String document);

}
