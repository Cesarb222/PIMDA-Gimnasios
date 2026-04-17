package com.example.gimnasioBack.backEndGimnasioF.Servicios;


import com.example.gimnasioBack.backEndGimnasioF.DTO.RecuperarPassword;
import com.example.gimnasioBack.backEndGimnasioF.Models.Codigo;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.EmailRepository;
import com.example.gimnasioBack.backEndGimnasioF.Repositorios.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServicio {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final JavaMailSender emailSender;

    public EmailServicio(final JavaMailSender emailSender ){
        this.emailSender = emailSender;
    }

    public int recuperarContraseña(Usuarios user) throws MessagingException {
        //Codigo Aleatorio
        int codigo = ((int) (Math.random()*1000)+9999);
        //creamos una dto en la que solo contiene el codigo
        RecuperarPassword dto = new RecuperarPassword();
        dto.setCodigo(codigo);
        //Creamos el mensaje
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(message,true,"UTF-8");
        //email al que se va enviar
        h.setTo(user.getEmail());
        //Asunto del correo
        h.setSubject("Recuperación de contraseña");
        //Y el cuerpo del correo en el que le podemos mandar HTML y su uso es muy similar a phpmailer
        h.setText("<h3>Hola, "+user.getNombre()+"</h3>"
                +"<p>Hemos recivido la peticion de cambiar la contraseña, si no has sido tu ignora este correo</p>"
                    +"<h1>"+codigo+"</h1>"
                ,true);
        //ponemos el correo desde donde se va a mandar, en el futuro se pondria el de la empresa
        h.setFrom("cesar33jverne@gmail.com");
        //Y enviamos el email con una funcion asincrona, evitando que se reviente la applixacion por tema de timeout
        usuarioRepository.enviarEmail(message,emailSender);
        emailRepository.addCodigo(new Codigo(codigo,user));
        //Eliminamos os codigos del usuario para evitar duplicados en un futuro
        this.eliminarCodigosDB(user.getId());
        return codigo;
    }

    public boolean eliminarCodigosDB(UUID uuid){
        Usuarios user = usuarioRepository.findByUUID(uuid.toString());
        if (user!=null) return  emailRepository.deleteAllByUser(user);
        else return false;
    }




}
