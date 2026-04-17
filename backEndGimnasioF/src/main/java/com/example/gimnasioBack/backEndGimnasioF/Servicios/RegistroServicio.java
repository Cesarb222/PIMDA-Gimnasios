package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Registro;
import com.example.gimnasioBack.backEndGimnasioF.Models.UsuarioRegistro;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EntrenoRepository;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class RegistroServicio {
    @Autowired
     RegistroRepository registroRepository;

    public List<Registro> findByAll(){
        return registroRepository.findByAll();
    }

    public List<Registro> getRegistrosUser(UUID uuid){
        return registroRepository.getRegistrosUser(uuid);
    }
    public Registro findById(int id){
        return registroRepository.findById(id);
    }
    public List<UsuarioRegistro> findAllComentariosById(Registro registro){
        return  registroRepository.findAllComentariosById(registro);
    }
    public Registro addRegistro(Registro registro){
        return registroRepository.addRegistro(registro);
    }
    public boolean addComentario(UsuarioRegistro registro){
        return  registroRepository.addComentario(registro);
    }
}
