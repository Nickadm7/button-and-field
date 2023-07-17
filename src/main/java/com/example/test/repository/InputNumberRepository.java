package com.example.test.repository;

import com.example.test.model.InputNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputNumberRepository extends JpaRepository<InputNumber, Long> {
}