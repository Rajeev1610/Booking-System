package com.example.InventoryService.repo;

import com.example.InventoryService.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<InventoryEntity,Long> {

    Optional<InventoryEntity> findByName(String name);
}
