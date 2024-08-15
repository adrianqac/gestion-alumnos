package org.ejercicio.Controller;

import org.ejercicio.Dto.AlumnoDto;
import org.ejercicio.Entity.Alumno;
import org.ejercicio.Exception.AlumnoNotFoundException;
import org.ejercicio.Repository.AlumnoRepository;
import org.ejercicio.Service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AlumnoRestControllerTest {

    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoRestController alumnoRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarAlumnos() {
        Alumno alumno1 = new Alumno("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumno2 = new Alumno("Ana", "Gomez", "ana@mail.com", 22);

        when(alumnoService.listarAlumnos()).thenReturn(Arrays.asList(alumno1, alumno2));

        ResponseEntity<List<Alumno>> response = alumnoRestController.listarAlumnos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testObtenerAlumnoPorId() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);

        when(alumnoService.obtenerAlumnoPorId(1L)).thenReturn(Optional.of(alumno));

        ResponseEntity<Alumno> response = alumnoRestController.obtenerAlumnoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
    }

    @Test
    public void testObtenerAlumnoPorIdNotFound() {
        when(alumnoService.obtenerAlumnoPorId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoRestController.obtenerAlumnoPorId(1L);
        });

        assertEquals("Alumno no encontrado", exception.getMessage());
    }

    @Test
    public void testCrearAlumno() {
        AlumnoDto alumnoDto = new AlumnoDto("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumno = new Alumno(alumnoDto.getNombre(), alumnoDto.getApellidos(), alumnoDto.getEmail(), alumnoDto.getEdad());

        when(alumnoService.crearAlumno(any(Alumno.class))).thenReturn(alumno);

        ResponseEntity<Alumno> response = alumnoRestController.crearAlumno(alumnoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
    }

    @Test
    public void testActualizarAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumnoActualizado = new Alumno("Juan", "Perez", "perez@mail.com", 22);

        AlumnoDto alumnoDto = new AlumnoDto(alumnoActualizado.getNombre(), alumnoActualizado.getApellidos(), alumnoActualizado.getEmail(), alumnoActualizado.getEdad());

        when(alumnoService.obtenerAlumnoPorId(1L)).thenReturn(Optional.of(alumno));
        when(alumnoService.actualizarAlumno(1L, alumnoActualizado)).thenReturn(alumnoActualizado);

        ResponseEntity<Alumno> response = alumnoRestController.actualizarAlumno(1L, alumnoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(22, response.getBody().getEdad());
    }

    @Test
    public void testActualizarAlumnoNotFound() {
        AlumnoDto alumnoDto = new AlumnoDto("Juan", "Perez", "juan@mail.com", 20);
        Alumno alumno = new Alumno(alumnoDto.getNombre(), alumnoDto.getApellidos(), alumnoDto.getEmail(), alumnoDto.getEdad());

        when(alumnoService.obtenerAlumnoPorId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoRestController.actualizarAlumno(1L, alumnoDto);
        });

        assertEquals("Alumno no encontrado", exception.getMessage());
    }

    @Test
    public void testEliminarAlumno() {
        when(alumnoService.obtenerAlumnoPorId(1L)).thenReturn(Optional.of(new Alumno()));

        ResponseEntity<Void> response = alumnoRestController.eliminarAlumno(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testEliminarAlumnoNotFound() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoRestController.eliminarAlumno(1L);
        });

        assertEquals("Alumno no encontrado", exception.getMessage());
    }
}
