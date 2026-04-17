package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.DTO.RecuperarPassword;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.EmailServicio;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.UsuarioServicio;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/send")
public class EmailController {
    @Autowired
    private EmailServicio emailServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;
    @GetMapping
    public ResponseEntity<?> recuperarPassword(@RequestParam String email) throws MessagingException {
        Usuarios u = usuarioServicio.findByEmail(email);
        System.out.println(u);
        if (u!=null){
            int codigo = emailServicio.recuperarContraseña(u);
            System.out.println(codigo);
            return ResponseEntity.ok(codigo);
        }
        return ResponseEntity.status(400).body("No se ha podido mandar el el codigo por que no encuentra usuario");
    }


}
