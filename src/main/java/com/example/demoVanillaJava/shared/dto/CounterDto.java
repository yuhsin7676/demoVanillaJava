package com.example.demoVanillaJava.shared.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CounterDto {
    private String name;
    private List<TagDto> tags;
}
