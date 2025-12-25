package com.quickmove.GoFaster.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;


import com.quickmove.GoFaster.entity.Userr;

public interface UserRepository extends JpaRepository<Userr, Integer> {
    Optional<Userr> findByMobileno(Long mobileno);
}

