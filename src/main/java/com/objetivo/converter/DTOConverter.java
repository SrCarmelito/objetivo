package com.objetivo.converter;

public interface DTOConverter<E, D> {

    E from(D dto);

    D to(E entity);

}
