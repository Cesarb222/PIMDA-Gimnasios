package com.example.gimnasioBack.backEndGimnasioF.Controladores;

import com.example.gimnasioBack.backEndGimnasioF.Models.Subscripcion;
import com.example.gimnasioBack.backEndGimnasioF.Servicios.SubscripcionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/subscripcion")
public class SubscripcionController {

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    @PostMapping("/add")
    public ResponseEntity<?> addSubscripcion(@RequestBody Subscripcion s){
        boolean estado = subscripcionServicio.addSubscripcion(s);
        System.out.println(s);
        if (estado) return ResponseEntity.ok(estado);
        return ResponseEntity.status(400).body("Error al cargar la subscripcion");
    }
}
