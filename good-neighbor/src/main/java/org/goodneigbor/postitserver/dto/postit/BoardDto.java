package org.goodneigbor.postitserver.dto.postit;

import org.goodneigbor.postitserver.dto.NamedEntityDto;

public class BoardDto extends NamedEntityDto {

    private Integer orderNum;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
