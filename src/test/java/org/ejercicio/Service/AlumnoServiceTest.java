package org.ejercicio.Service;

import org.ejercicio.Entity.Alumno;
import org.ejercicio.Exception.AlumnoNotFoundException;
import org.ejercicio.Repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    @Test
    public void testListarAlumnos() {
        Alumno alumno1 = new Alumno("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumno2 = new Alumno("Ana", "Gomez", "ana@mail.com", 22);

        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno1, alumno2));

        List<Alumno> alumnos = alumnoService.listarAlumnos();

        assertNotNull(alumnos);
        assertEquals(2, alumnos.size());
    }

    @Test
    public void testCrearAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);

        when(alumnoRepository.save(alumno)).thenReturn(alumno);

        Alumno resultado = alumnoService.crearAlumno(alumno);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    public void testObtenerAlumnoPorId() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        Optional<Alumno> resultado = alumnoService.obtenerAlumnoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    public void testActualizarAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumnoActualizado = new Alumno("Juan", "Perez", "juan@mail.com", 22);

        when(alumnoRepository.existsById(1L)).thenReturn(true);
        when(alumnoRepository.save(alumnoActualizado)).thenReturn(alumnoActualizado);

        Alumno resultado = alumnoService.actualizarAlumno(1L, alumnoActualizado);

        assertNotNull(resultado);
        assertEquals(22, resultado.getEdad());
    }

    @Test
    public void testEliminarAlumno() {
        when(alumnoRepository.existsById(1L)).thenReturn(true);

        alumnoService.eliminarAlumno(1L);

        verify(alumnoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarAlumnoNotFound() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);

        when(alumnoRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoService.actualizarAlumno(1L, alumno);
        });

        assertEquals("Alumno no encontrado", exception.getMessage());
    }

    @Test
    public void testEliminarAlumnoNotFound() {
        when(alumnoRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoService.eliminarAlumno(1L);
        });

        assertEquals("Alumno no encontrado", exception.getMessage());
    }
}
