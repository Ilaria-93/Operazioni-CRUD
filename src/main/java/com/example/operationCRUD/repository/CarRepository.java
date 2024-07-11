package com.example.operationCRUD.repository;

import com.example.operationCRUD.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
