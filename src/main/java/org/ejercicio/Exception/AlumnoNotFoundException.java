package org.ejercicio.Exception;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
