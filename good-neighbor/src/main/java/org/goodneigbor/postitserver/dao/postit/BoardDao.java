package org.goodneigbor.postitserver.dao.postit;

import org.goodneigbor.postitserver.entity.postit.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface BoardDao extends CrudRepository<Board, Long> {

    Iterable<Board> findAll(Sort sort);

}
