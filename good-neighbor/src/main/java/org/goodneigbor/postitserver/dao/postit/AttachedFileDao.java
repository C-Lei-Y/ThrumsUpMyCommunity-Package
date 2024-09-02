package org.goodneigbor.postitserver.dao.postit;

import org.goodneigbor.postitserver.entity.postit.AttachedFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AttachedFileDao extends CrudRepository<AttachedFile, Long> {

    Page<AttachedFile> findAll(Pageable pageable);

}
