package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.DTO.LoginRequest;
import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.UsuarioRepository;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios/")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //PETICIONES:
    @GetMapping("/")
    public ResponseEntity<List<Usuarios>> usuarios(){
        List<Usuarios> lista = usuarioServicio.findAll();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/emails")
    public ResponseEntity<List<String>> usuariosEmail(){
        List<Usuarios> lista = usuarioServicio.findAll();
        List<String> emails = new ArrayList<String>();
        for (Usuarios u : lista){
            emails.add(u.getEmail());
        }
        return ResponseEntity.ok(emails);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> findUUID(@PathVariable("id") String id){
        Usuarios u = usuarioServicio.findByUUID(id);
        if (u == null) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(u);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest userData){
        System.out.println("aaa");
        Usuarios u = usuarioServicio.login(userData.getEmail(),userData.getPasswd());
        if (u == null) return ResponseEntity.status(404).body("Email o contraseña incorrectos");
        System.out.println(u);
        return ResponseEntity.ok(u);
    }
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuarios userData){
        Usuarios u = usuarioServicio.insertar(userData);
        if (u == null) return ResponseEntity.status(404).body("Email o contraseña incorrectos");
        return ResponseEntity.ok(u);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePassword(@RequestBody LoginRequest userData){
        boolean estado = usuarioServicio.updatePassword(userData.getEmail(),userData.getPasswd());
        if (estado) return ResponseEntity.ok(estado);
        return ResponseEntity.status(404).body("Fallo al actualizar contraseña");
    }

    @PatchMapping("/update/gym/{uuid}")
    public void updateGimnasio(@RequestBody Gimnasio g, @PathVariable("uuid") UUID uuid){
        usuarioServicio.updateGimnasio(uuid,g);
    }

}
