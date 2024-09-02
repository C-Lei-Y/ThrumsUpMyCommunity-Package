package org.goodneigbor.postitserver.dto;

import java.util.Objects;

public class IdEntityDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IdEntityDto))
            return false;
        IdEntityDto other = (IdEntityDto) obj;
        return Objects.equals(id, other.id);
    }

}
