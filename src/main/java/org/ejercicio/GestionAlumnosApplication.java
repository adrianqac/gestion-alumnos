package org.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class GestionAlumnosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionAlumnosApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void openSwaggerUI() {
        try {
            //Abrir swagger-ui en el navegador
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080/swagger-ui/index.html"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
