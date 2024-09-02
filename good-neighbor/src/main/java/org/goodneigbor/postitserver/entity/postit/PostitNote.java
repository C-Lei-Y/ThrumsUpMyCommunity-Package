package org.goodneigbor.postitserver.entity.postit;

import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.goodneigbor.postitserver.entity.AbstractDatedEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "postit_note")
public class PostitNote extends AbstractDatedEntity {

    @Id
    @SequenceGenerator(name = "postit_note_id_seq", sequenceName = "postit_note_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postit_note_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    @ColumnDefault("0")
    @Version
    private Long version;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @Column(name = "text_value")
    @Size(min = 0, max = 2048)
    private String textValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postit_board_id", nullable = false)
    private Board board;

    @Column(name = "update_text_date")
    private Date updateTextDate;

    @Column(name = "color")
    @Size(min = 0, max = 128)
    private String color;

    @Column(name = "order_num")
    @Min(0)
    @Max(100000)
    private Integer orderNum;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "attached_file_id", referencedColumnName = "id")
    private AttachedFile attachedFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Date getUpdateTextDate() {
        return updateTextDate;
    }

    public void setUpdateTextDate(Date updateTextDate) {
        this.updateTextDate = updateTextDate;
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

    public AttachedFile getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(AttachedFile attachedFile) {
        this.attachedFile = attachedFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PostitNote))
            return false;
        PostitNote other = (PostitNote) obj;
        return Objects.equals(getId(), other.getId());
    }

}
