package org.goodneigbor.postitserver.dto.postit;

import org.goodneigbor.postitserver.dto.NamedEntityDto;

public class PostitNoteDto extends NamedEntityDto {

    private String text;

    private Long boardId;

    private String color;

    private Integer orderNum;

    private AttachedFileDto attachedFile;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public AttachedFileDto getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(AttachedFileDto attachedFile) {
        this.attachedFile = attachedFile;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PostitNoteDto))
            return false;
        return super.equals(other);
    }

}
