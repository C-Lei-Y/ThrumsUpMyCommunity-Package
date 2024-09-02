package org.goodneigbor.postitserver.dto.postit;

import org.goodneigbor.postitserver.dto.IdEntityDto;

public class AttachedFileDto extends IdEntityDto {

    private Long postitNoteId;

    private String filename;

    private Long size;

    private String type;

    public Long getPostitNoteId() {
        return postitNoteId;
    }

    public void setPostitNoteId(Long postitNoteId) {
        this.postitNoteId = postitNoteId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AttachedFileDto))
            return false;
        return super.equals(other);
    }

}
