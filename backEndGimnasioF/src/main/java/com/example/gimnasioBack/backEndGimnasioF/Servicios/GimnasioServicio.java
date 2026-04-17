package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.GimnasioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GimnasioServicio {
    @Autowired
    private GimnasioRepositorio gimnasioRepositorio;

    public Gimnasio getGimnasio(int id){
        return gimnasioRepositorio.getGimnasio(id);
    }

    public Gimnasio updateGimnasio(Gimnasio g){
        return gimnasioRepositorio.updateGimnasio(g);
    }
}
