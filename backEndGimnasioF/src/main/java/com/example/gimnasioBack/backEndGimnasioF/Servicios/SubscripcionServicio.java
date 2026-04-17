package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Subscripcion;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.SubscripcionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscripcionServicio {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SubscripcionRepository subscripcionRepository;

    public boolean addSubscripcion(Subscripcion s){
        return subscripcionRepository.addSubscripcion(s);
    }

    //Le damos la transaccion
    @Transactional
    public void actualizarSubscripciones() {
        //En el entityManger cramos la query para llamar al procedure
        //Nos mostrarara el mensaje si todo sale perfectamente
        entityManager
                .createNativeQuery("CALL actualizar_subscripcion()")
                .executeUpdate();
        System.out.println("DB ACTUALIZADA CORRECTAMENTE");
    }
}
