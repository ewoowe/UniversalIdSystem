package com.github.ewoowe.entity;

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
}
