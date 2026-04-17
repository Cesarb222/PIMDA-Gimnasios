package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.DTO.NombreEjercicio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Ejercicio;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EjercicioRepository;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.EjercicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios/")
public class EjercicioController {
    @Autowired
    EjercicioServicio ejercicioServicio;
    //PETICIONES:
    @GetMapping("/")
    public ResponseEntity<List<Ejercicio>> getAll(){
        List<Ejercicio> lista = ejercicioServicio.findAll();
        return ResponseEntity.ok(lista);
    }
    @PostMapping("/ejercicio")
    public ResponseEntity<?> getEjercicioByName(@RequestBody NombreEjercicio nombreEjercicio){
        Ejercicio ej = ejercicioServicio.findByName(nombreEjercicio.getNombre());
        if (ej!=null) return ResponseEntity.ok(ej);
        else return ResponseEntity.status(400).body("No hay ejercicio con ese nombre");
    }

}
