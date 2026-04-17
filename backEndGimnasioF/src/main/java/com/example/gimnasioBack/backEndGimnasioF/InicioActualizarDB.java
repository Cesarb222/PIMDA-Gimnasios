package com.example.gimnasioBack.backEndGimnasioF;

import com.example.gimnasioBack.backEndGimnasioF.Servicios.EmailServicio;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.SubscripcionServicio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicioActualizarDB implements CommandLineRunner {
    //Metodo para actualizar la base de datos nada mas arrancar el servidor de spring
    //Esto llama a un procedure y nos actualiza a los usuarios que su subscripcion esta acabada
    private final SubscripcionServicio subscripcionServicio;

    public InicioActualizarDB(SubscripcionServicio subscripcionServicio) {
        this.subscripcionServicio = subscripcionServicio;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true){
            Lanzador l = new Lanzador(subscripcionServicio);
            Thread h = new Thread(l);
            h.start();
            h.join();
        }
    }
}
