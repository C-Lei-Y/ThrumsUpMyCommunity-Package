package org.goodneigbor.postitserver.dao.global;

import org.goodneigbor.postitserver.entity.global.Parameter;
import org.springframework.data.repository.CrudRepository;

public interface ParameterDao extends CrudRepository<Parameter, String> {

}
