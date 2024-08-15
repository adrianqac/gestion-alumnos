package org.ejercicio.Controller;

import org.ejercicio.Dto.AlumnoDto;
import org.ejercicio.Entity.Alumno;
import org.ejercicio.Exception.AlumnoNotFoundException;
import org.ejercicio.Service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoRestController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        return ResponseEntity.ok( alumnoService.listarAlumnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumnoPorId(@PathVariable Long id) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));
        return ResponseEntity.ok(alumno);
    }

    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@Validated @RequestBody AlumnoDto alumnoDto) {
        Alumno nuevoAlumno = alumnoService.crearAlumno(new Alumno(alumnoDto.getNombre(), alumnoDto.getApellidos(), alumnoDto.getEmail(), alumnoDto.getEdad()));
        return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @Validated @RequestBody AlumnoDto alumnoActualizado) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        alumno.setNombre(Objects.isNull(alumnoActualizado.getNombre()) ? alumno.getNombre() : alumnoActualizado.getNombre());
        alumno.setEmail(Objects.isNull(alumnoActualizado.getEmail()) ? alumno.getEmail() : alumnoActualizado.getEmail());
        alumno.setApellidos(Objects.isNull(alumnoActualizado.getApellidos()) ? alumno.getApellidos() : alumnoActualizado.getApellidos());
        alumno.setEdad(Objects.isNull(alumnoActualizado.getEdad())  ? alumno.getEdad() : alumnoActualizado.getEdad());
        alumnoService.crearAlumno(alumno);

        return ResponseEntity.ok(alumno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        alumnoService.eliminarAlumno(alumno.getId()) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
