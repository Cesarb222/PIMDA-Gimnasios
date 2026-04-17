package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.Models.Entrenamiento;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EntrenoRepository;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.UsuarioRepository;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.EntrenamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/entrenamientos")
public class EntrenamientoController {

    @Autowired
    EntrenamientoServicio entrenoServicio;

    //PETICIONES:
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getEntrenos(@PathVariable UUID uuid){
        UsuarioRepository usuarioServicio = new UsuarioRepository();
        Usuarios u = usuarioServicio.findByUUID(uuid.toString());
        if (u!=null){
            List<Entrenamiento> lista = entrenoServicio.getAllEjercicio(u);
            if (lista!=null) return ResponseEntity.ok(lista);
        }
        return ResponseEntity.status(404).body("No se ha podido encontrar los entrenos");
    }
    @GetMapping("/entreno/{id}")
    public ResponseEntity<?> getEntreno(@PathVariable long id){
        Entrenamiento entrenamiento = entrenoServicio.getEntreno( (int) id);
        if (entrenamiento!=null) return ResponseEntity.ok(entrenamiento);
        return ResponseEntity.status(404).body("No se ha podido encontrar eel entreno");
    }
    @PostMapping("/add")
    public ResponseEntity<?> addEntreno(@RequestBody Entrenamiento entreno){
        boolean resultado = entrenoServicio.addEjercicio(entreno);
        if (resultado) return ResponseEntity.ok(resultado);
        return ResponseEntity.status(404).body("No se ha podido insertar el entreno");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEntreno(@PathVariable long id){
        int idInt = (int) id;
        boolean resultado = entrenoServicio.deleteEjercicio(idInt);
        if(resultado) return ResponseEntity.ok().body("Borrado correctamente");
        return ResponseEntity.ok().body("Fallo al borrar el entreno");
    }


}
