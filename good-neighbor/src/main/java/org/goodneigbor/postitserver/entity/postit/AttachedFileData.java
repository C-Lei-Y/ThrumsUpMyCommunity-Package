package org.goodneigbor.postitserver.entity.postit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "postit_attached_file_data")
public class AttachedFileData {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "data", length = 500000)
    @NotNull
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private AttachedFile file;

    public AttachedFileData() {
    }

    public AttachedFileData(byte[] data, AttachedFile file) {
        this.data = data;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public AttachedFile getFile() {
        return file;
    }

    public void setFile(AttachedFile file) {
        this.file = file;
    }

}
