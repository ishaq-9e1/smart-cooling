package com.smartcooling.repository;

import com.smartcooling.model.CoolingSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoolingRepository extends JpaRepository<CoolingSystem, Long> {}
