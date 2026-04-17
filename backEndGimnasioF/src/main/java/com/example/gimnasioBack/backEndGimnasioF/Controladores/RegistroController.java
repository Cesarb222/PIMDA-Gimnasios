package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.Models.Registro;
import com.example.gimnasioBack.backEndGimnasioF.Models.UsuarioRegistro;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.RegistroRepository;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.UsuarioRepository;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.RegistroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {
    @Autowired
    RegistroServicio registroServicios;
    //PETICIONES:
    @GetMapping("/")
    public ResponseEntity<?> getAllRegistros(){
        List<Registro> registros = registroServicios.findByAll();
        if (registros!=null) return ResponseEntity.ok(registros);
        return ResponseEntity.status(404).body("No hay registros");
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getRegistros(@PathVariable UUID uuid){
        List<Registro> registros =  registroServicios.getRegistrosUser(uuid);
        if(registros!=null) return ResponseEntity.ok(registros);
        return  ResponseEntity.status(404).body("Error no hay registros para este usuario");
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getRegistro(@PathVariable long id){
        int idInt = (int) id;
        Registro registro = registroServicios.findById(idInt);
        if (registro!=null) return ResponseEntity.ok(registro);
        return ResponseEntity.status(404).body("No hay registros con este ID");
    }
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<?> getComentarios(@PathVariable long id){
        int idInt = (int) id;
        Registro registro = registroServicios.findById(idInt);
        UsuarioRepository usuarioServicio = new UsuarioRepository();
        if (registro!=null){
            List<UsuarioRegistro> comentarios = registroServicios.findAllComentariosById(registro);
            for (UsuarioRegistro u: comentarios){
                System.out.println(u);
            }
            return ResponseEntity.ok(comentarios);
        }
        return ResponseEntity.status(404).body("Error al buscar comentario");
    }
    @PostMapping("/add")
    public ResponseEntity<?> addRegistro(@RequestBody Registro registro){
        Registro r =  registroServicios.addRegistro(registro);
        if(r!=null) return ResponseEntity.ok(r);
        return  ResponseEntity.status(404).body("Fallo al insertar o devolver el objeto");
    }

    @PostMapping("/comentarios/add")
    public ResponseEntity<?> addComentario(@RequestBody UsuarioRegistro registro){
        boolean resultado = registroServicios.addComentario(registro);
        if (resultado) return ResponseEntity.ok(resultado);
        return ResponseEntity.status(404).body("Error al insertar comentario");
    }


}
