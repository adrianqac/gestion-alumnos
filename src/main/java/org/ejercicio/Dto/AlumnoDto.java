package org.ejercicio.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDto {

    private String nombre;
    private String apellidos;
    private String email;
    private Integer edad;
}
