package com.example.gimnasioBack.backEndGimnasioF.Servicios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.UsuarioRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServicio {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuarios> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuarios findByUUID(String id){
        return  usuarioRepository.findByUUID(id);
    }
    public Usuarios login(String email,String password){
        return usuarioRepository.login(email,password);
    }
    public Usuarios insertar(Usuarios userData){
      return usuarioRepository.insertar(userData);
    }

    public Usuarios findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public boolean updatePassword(String email, String password){
        Usuarios u = usuarioRepository.findByEmail(email);
        System.out.println(password);
        return usuarioRepository.actualizarPassword(u,password);
    }

    public void updateGimnasio(UUID uuid, Gimnasio g){
        Usuarios user = usuarioRepository.findByUUID(uuid.toString());
        if (user!=null) usuarioRepository.updateGimnasio(g,user);
    }


}
