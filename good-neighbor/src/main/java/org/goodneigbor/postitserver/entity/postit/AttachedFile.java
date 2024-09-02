package org.goodneigbor.postitserver.entity.postit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.goodneigbor.postitserver.entity.AbstractDatedEntity;

@Entity
@Table(name = "postit_attached_file")
public class AttachedFile extends AbstractDatedEntity {

    @Id
    @SequenceGenerator(name = "postit_attached_file_id_seq", sequenceName = "postit_attached_file_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postit_attached_file_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "filename")
    @NotNull
    @Size(min = 1, max = 128)
    private String filename;

    @Column(name = "size")
    @NotNull
    private Long size;

    @Column(name = "mime_type")
    @NotNull
    @Size(min = 3, max = 256)
    private String type;

    @OneToOne(mappedBy = "file", optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull
    private AttachedFileData filedata;

    @OneToOne(mappedBy = "attachedFile")
    private PostitNote postitNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AttachedFileData getFiledata() {
        return filedata;
    }

    public void setFiledata(AttachedFileData filedata) {
        this.filedata = filedata;
    }

    public PostitNote getPostitNote() {
        return postitNote;
    }

    public void setPostitNote(PostitNote postitNote) {
        this.postitNote = postitNote;
    }

}
