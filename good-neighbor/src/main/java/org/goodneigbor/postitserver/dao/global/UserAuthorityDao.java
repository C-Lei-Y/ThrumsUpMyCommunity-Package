package org.goodneigbor.postitserver.dao.global;

import java.util.Optional;

import org.goodneigbor.postitserver.entity.global.UserAuthority;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthorityDao extends CrudRepository<UserAuthority, Long> {

    Iterable<UserAuthority> findAll(Sort sort);

    Optional<UserAuthority> findByAuthority(String authority);

}
