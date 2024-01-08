package com.moistAbes.projectManager.mappers;

public interface Mapper<A,B> {
    B mapToDto(A a);

    A mapToEntity(B b);
}
