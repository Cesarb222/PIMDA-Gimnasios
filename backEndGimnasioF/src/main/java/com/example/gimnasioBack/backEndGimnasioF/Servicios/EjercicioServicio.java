package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Ejercicio;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioServicio {
    @Autowired
    EjercicioRepository ejercicioRepository;

    public List<Ejercicio> findAll(){
        return ejercicioRepository.findAll();
    }
    public Ejercicio findByName(String nombre){
        return ejercicioRepository.findByName(nombre);
    }
}
