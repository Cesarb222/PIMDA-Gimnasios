package com.example.gimnasioBack.backEndGimnasioF;

import com.example.gimnasioBack.backEndGimnasioF.Servicios.SubscripcionServicio;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Lanzador implements Runnable {

    private final SubscripcionServicio subscripcionServicio;

    public Lanzador(SubscripcionServicio subscripcionServicio) {
        this.subscripcionServicio = subscripcionServicio;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(86400000);
            subscripcionServicio.actualizarSubscripciones();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
