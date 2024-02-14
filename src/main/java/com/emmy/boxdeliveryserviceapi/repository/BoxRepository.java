package com.emmy.boxdeliveryserviceapi.repository;

import com.emmy.boxdeliveryserviceapi.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

  boolean existsByTxref(String txref);
}
