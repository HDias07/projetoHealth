package com.healthconnect.healthconnect.exception;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(Long id) {
        super("Paciente com id " + id + " nao foi encontrado.");
    }
}
