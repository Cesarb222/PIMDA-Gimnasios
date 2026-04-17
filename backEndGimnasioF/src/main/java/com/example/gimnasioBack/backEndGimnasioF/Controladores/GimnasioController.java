package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.GimnasioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/gimnasio")
public class GimnasioController {
    @Autowired
    private GimnasioServicio gimnasioServicio;
    @GetMapping("/{id}")
    public ResponseEntity<?>  getGimnasio(@PathVariable long id){
        Gimnasio g = gimnasioServicio.getGimnasio((int) id);
        if (g!=null) return  ResponseEntity.ok(g);
        return ResponseEntity.status(400).body("Gimnasio no encontrado");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateGimnasio(@RequestBody Gimnasio g){
        Gimnasio gym = gimnasioServicio.updateGimnasio(g);
        if (gym != null) return ResponseEntity.ok(gym);
        return ResponseEntity.status(400).body("Error al actualizar el gimnasio");
    }
}
