package com.objetivo.converter;

public interface DTOConverter<E, D> {

    E from(D dto, E entity);

    D to(E entity);

}
