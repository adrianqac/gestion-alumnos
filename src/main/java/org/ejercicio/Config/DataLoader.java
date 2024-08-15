package org.ejercicio.Config;

import org.ejercicio.Entity.Alumno;
import org.ejercicio.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica si ya hay datos en la base de datos
        //alumnoRepository.deleteAll();
        if (alumnoRepository.count() == 0) {
            // Inserta datos por defecto
            Alumno alumno1 = new Alumno("Juan", "Pérez", "juan@mail.com", 18);
            Alumno alumno2 = new Alumno("Ana", "García", "ana@mail.com",18);
            Alumno alumno3 = new Alumno("Mauricio", "Valle", "mauricio@mail.com",19);
            Alumno alumno4 = new Alumno("Mariana", "Rosales", "mariana@mail.com",20);
            Alumno alumno5 = new Alumno("Moises", "Alvarado", "moises@mail.com",19);

            alumnoRepository.save(alumno1);
            alumnoRepository.save(alumno2);
            alumnoRepository.save(alumno3);
            alumnoRepository.save(alumno4);
            alumnoRepository.save(alumno5);

            System.out.println("Datos iniciales insertados.");
        }
    }
}
