package org.goodneigbor.postitserver.dao.global;

import org.goodneigbor.postitserver.entity.global.Like;
import org.springframework.data.repository.CrudRepository;

public interface LikeDao extends CrudRepository<Like, Long> {

}
