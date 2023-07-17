package com.example.test.service;

import com.example.test.model.InputNumber;
import com.example.test.repository.InputNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InputNumberServiceImpl implements InputNumberService {
    private final InputNumberRepository inputNumberRepository;

    @Override
    public void addInputNumber(InputNumber inputNumber) {
        InputNumber save = inputNumberRepository.save(inputNumber);
        log.info("Success add record to DB H2 with id: {} value: {}", save.getId(), save.getInputNumber());
    }

    @Override
    public InputNumber plusOne(InputNumber inputNumber) {
        long buffer = Long.parseLong(inputNumber.getInputNumber()) + 1;
        inputNumber.setInputNumber(Long.toString(buffer));
        return inputNumber;
    }
}