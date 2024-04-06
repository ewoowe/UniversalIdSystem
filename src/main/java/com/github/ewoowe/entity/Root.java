package com.github.ewoowe.entity;

import com.github.ewoowe.UidException;
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

    public void selfCheck() throws UidException {
        if (typeLength == null || typeLength < 1)
            throw new UidException("root typeLength cant be null or cant be less 1");
        if (values == null || values.isEmpty())
            throw new UidException("root's values map cant be null or empty");
        for (Map.Entry<String, NodeType> entry : values.entrySet()) {
            if (entry.getKey().length() != typeLength)
                throw new UidException("type value not match the typeLength");
            entry.getValue().selfCheck();
        }
    }
}
