package com.example.test;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class InputNumber {
    private String inputNumber;

    public void plusOne() {
        long buffer = Long.parseLong(this.inputNumber) + 1;
        this.inputNumber = Long.toString(buffer);
    }
}
