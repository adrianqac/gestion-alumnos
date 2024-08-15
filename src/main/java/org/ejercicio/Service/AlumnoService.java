package org.ejercicio.Service;

import org.ejercicio.Entity.Alumno;
import org.ejercicio.Repository.AlumnoRepository;
import org.ejercicio.Exception.AlumnoNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno crearAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    public Alumno actualizarAlumno(Long id, Alumno alumno) {
        if (!alumnoRepository.existsById(id)) {
            throw new AlumnoNotFoundException("Alumno no encontrado");
        }
        alumno.setId(id);
        return alumnoRepository.save(alumno);
    }

    public void eliminarAlumno(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new AlumnoNotFoundException("Alumno no encontrado");
        }
        alumnoRepository.deleteById(id);
    }
}
