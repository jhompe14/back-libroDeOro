package com.scouts.backlibrodeoro.validator;

public interface IValidator {
    public <T> void validator(T entity) throws Exception;
}
