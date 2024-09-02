package org.goodneigbor.postitserver.entity.postit;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.goodneigbor.postitserver.entity.AbstractDatedEntity;

@Entity
@Table(name = "postit_board")
public class Board extends AbstractDatedEntity {

    @Id
    @SequenceGenerator(name = "postit_board_id_seq", sequenceName = "postit_board_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postit_board_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @Column(name = "order_num")
    @Min(0)
    @Max(1000)
    private Integer orderNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    @OrderBy("orderNum ASC")
    private List<PostitNote> noteList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<PostitNote> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<PostitNote> noteList) {
        this.noteList = noteList;
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
        if (!(obj instanceof Board))
            return false;
        Board other = (Board) obj;
        return Objects.equals(getId(), other.getId());
    }

}
