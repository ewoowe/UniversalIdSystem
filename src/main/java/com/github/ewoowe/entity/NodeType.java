package com.github.ewoowe.entity;

import com.github.ewoowe.UidException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeType {

    /**
     * node type
     */
    @NotNull
    private String type;

    /**
     * node type label
     */
    private String label;

    /**
     * current node type's id length
     */
    @NotNull
    @Min(value = 0)
    private Integer idLength;

    /**
     * current node's need take node id for view name
     */
    private Boolean viewNoId;

    /**
     * current node's default id
     */
    private String defaultId;

    /**
     * current node's next level nodes' type length
     */
    private Integer typeLength;

    /**
     * next level nodes' type values
     */
    private Map<String, NodeType> values;

    public void selfCheck() throws UidException {
        if (typeLength != null) {
            if (typeLength < 1)
                throw new UidException("typeLength cant be less 1");
            if (values == null || values.isEmpty())
                throw new UidException("values cant be null when typeLength not null");
            if (idLength == null)
                idLength = 0;
            if (idLength < 0)
                throw new UidException("idLength cant be negative");
            for (Map.Entry<String, NodeType> entry : values.entrySet()) {
                if (entry.getKey().length() != typeLength)
                    throw new UidException("type value not match the typeLength");
                entry.getValue().selfCheck();
            }
        } else {
            if (values != null && !values.isEmpty())
                throw new UidException("typeLength cant be null when values not empty");
        }
    }
}
