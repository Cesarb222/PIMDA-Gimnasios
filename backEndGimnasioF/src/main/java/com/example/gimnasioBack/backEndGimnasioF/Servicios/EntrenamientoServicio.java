package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Ejercicio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Entrenamiento;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EntrenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenamientoServicio {
    @Autowired
    EntrenoRepository entrenoRepository;

    public List<Entrenamiento> getAllEjercicio(Usuarios u){
        return entrenoRepository.getAllEjercicio(u);
    }
    public Entrenamiento getEntreno(int id){
        return entrenoRepository.getEntreno(id);
    }
    public boolean addEjercicio(Entrenamiento entreno){
        return entrenoRepository.addEjercicio(entreno);
    }
    public boolean deleteEjercicio(int idInt){
        return entrenoRepository.deleteEjercicio(idInt);
    }
}
