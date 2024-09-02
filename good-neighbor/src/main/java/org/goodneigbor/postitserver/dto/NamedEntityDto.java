package org.goodneigbor.postitserver.dto;

public class NamedEntityDto extends IdEntityDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NamedEntityDto))
            return false;
       return super.equals(other);
    }
}
