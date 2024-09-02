package org.goodneigbor.postitserver.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

public class PageDto<T> {

    public static <T, E> PageDto<T> of(Page<E> entitiesPage, Function<E, T> mapperEntityToDto) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setTotalPages(entitiesPage.getTotalPages());
        pageDto.setTotalElements(entitiesPage.getTotalElements());
        pageDto.setElements(entitiesPage.getContent().stream().map(mapperEntityToDto).collect(Collectors.toList()));
        return pageDto;
    }

    private List<T> elements;

    private Long totalElements;

    private Integer totalPages;

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
