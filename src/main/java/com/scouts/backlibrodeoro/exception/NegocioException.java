package com.scouts.backlibrodeoro.exception;

import com.scouts.backlibrodeoro.types.TypeException;

public class NegocioException extends RuntimeException {

    private TypeException typeException;

    public NegocioException(String mensaje, TypeException typeException){
        super(mensaje);
        this.typeException = typeException;
    }

    public TypeException getTypeException() {
        return typeException;
    }
}
