package com.github.ewoowe.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class Root {
    @NotNull(message = "root typeLength cant be null")
    @Min(value = 1, message = "typeLength cant be less 1")
    private Integer typeLength;

    @NotEmpty(message = "root values cant be empty")
    @NotNull(message = "root values cant be empty")
    private Map<String, NodeType> values;
}
