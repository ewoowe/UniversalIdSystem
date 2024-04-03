package com.github.ewoowe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ewoowe.entity.NodeType;
import com.github.ewoowe.entity.Root;
import com.github.ewoowe.entity.UidSchema;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class UniversalIdSystem {

    private final UidSchema uidSchema;

    public UniversalIdSystem(String filePath) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            StringBuilder sb = new StringBuilder();
            allLines.forEach(sb::append);
            ObjectMapper mapper = new ObjectMapper();
            uidSchema = mapper.readValue(sb.toString(), UidSchema.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pair<String, NodeType> getNodeType(String type, Map<String, NodeType> values) {
        for (Map.Entry<String, NodeType> entry : values.entrySet()) {
            if (entry.getValue().getType().equals(type))
                return new MutablePair<>(entry.getKey(), entry.getValue());
        }
        return null;
    }

    private boolean existType(String type, Map<String, NodeType> values) {
        for (Map.Entry<String, NodeType> entry : values.entrySet()) {
            if (entry.getValue().getType().equals(type))
                return true;
        }
        return false;
    }

    private boolean typeIdNotExist(String typeId, Map<String,NodeType> values) {
        return !values.containsKey(typeId);
    }

    private String getId(int idLength, String id) {
        assert idLength >= id.length();
        StringBuilder reverse = new StringBuilder(id);
        StringBuilder sb = new StringBuilder(reverse.reverse().toString());
        while (sb.length() != idLength)
            sb.append("0");
        return sb.reverse().toString();
    }

    private String getSubUid(Pair<String, NodeType> node, String type, String id) throws UidException {
        NodeType value = node.getValue();
        if (id == null) {
            if (value.getDefaultId() == null && value.getIdLength() > 0)
                throw new UidException("type " + type + " no defaultId, id must be set");
            return node.getKey() + getId(value.getIdLength(),
                    value.getDefaultId() == null ? "" : value.getDefaultId());
        } else {
            if (value.getIdLength() < id.length())
                throw new UidException("id " + id + " too long");
            return node.getKey() + getId(value.getIdLength(), id);
        }
    }

    private String getUid(String uid, int index, NodeType node, String type, String id) throws UidException {
        Integer idLength = node.getIdLength();
        Map<String, NodeType> values = node.getValues();
        if ((uid.length() - index - idLength) < 0)
            throw new UidException("uid invalid, too short");
        if (values.isEmpty())
            throw new UidException("no type " + type);
        if ((uid.length() - index - idLength) == 0) {
            Pair<String, NodeType> nodeType = getNodeType(type, values);
            if (nodeType == null)
                throw new UidException("no type " + type);
            return uid + getSubUid(nodeType, type, id);
        }
        String typeId = uid.substring(index + idLength, index + idLength + node.getTypeLength());
        if (typeIdNotExist(typeId, values))
            throw new UidException("uid invalid, type id not exist");
        return getUid(uid, index + idLength + node.getTypeLength(), values.get(typeId), type, id);
    }

    public String getUid(String uid, String type, String id) throws UidException {
        Root root = uidSchema.getRoot();
        Integer rootTypeLength = root.getTypeLength();
        Map<String, NodeType> values = root.getValues();
        if (uid == null) {
            Pair<String, NodeType> nodeType = getNodeType(type, values);
            if (nodeType == null)
                throw new UidException("no type " + type);
            return getSubUid(nodeType, type, id);
        }
        if (uid.length() < rootTypeLength)
            throw new UidException("uid invalid, too short");
        if (values.isEmpty())
            throw new UidException("no type " + type);
        String rootTypeId = uid.substring(0, rootTypeLength);
        if (typeIdNotExist(rootTypeId, values))
            throw new UidException("uid invalid, type id not exist");
        return getUid(uid, rootTypeLength, values.get(rootTypeId), type, id);
    }
}
