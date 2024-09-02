package org.goodneigbor.postitserver.dao.global;

import java.util.Optional;

import org.goodneigbor.postitserver.entity.global.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    Iterable<User> findAll(Sort sort);

    Optional<User> findByUsername(String username);

}
