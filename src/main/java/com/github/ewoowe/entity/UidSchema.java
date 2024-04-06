package com.github.ewoowe.entity;

import com.github.ewoowe.UidException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UidSchema {

    private String name;

    private String description;

    @NotNull
    private Root root;

    public void selfCheck() throws UidException {
        if (root == null)
            throw new UidException("UidSchema invalid, root cant be null");
        root.selfCheck();
    }

}
