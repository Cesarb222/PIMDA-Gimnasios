package com.example.gimnasioBack.backEndGimnasioF.Repositorios;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import jakarta.mail.internet.MimeMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    public UsuarioRepository() {
    }

    @Autowired
    private EmailRepository emailRepository;

    public Usuarios insertar(Usuarios user){
        //Iniciamos la transaccion y la sesion a null
        Transaction tr = null;
        Session session = null;
        try {
            //Abrimos la sesion para utilizar los metodos
            session = Conexion.conectar().openSession();
            //Comenzamos la transaccion
            tr = session.beginTransaction();
            //creo una variable hash en la que se va a guardar el hasheo de la contraseña
            //este recive el numero re roundas siendo este el cost factor que lo va a hacer en este caso 12 veces
            //y la contraseña que se tendra que pasar como un array de caracteres
            String hash = BCrypt.withDefaults().hashToString(12,user.getPasswd().toCharArray());
            //seteamos la contraseña
            user.setPasswd(hash);
            //y lo ejecutamos los cambios  y mandamps el usuario
            session.persist(user);
            tr.commit();
            return user;
        }catch (Exception e){
            //en caso de fallar la inserccion no se guarda en la bd por que hacemos rollback
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuarios> findAll(){
        //Lista vacia
        ArrayList<Usuarios> lista = new ArrayList<>();
        //creamos la transsaccion
        Transaction tr = null;
        try{
           tr = Conexion.SESSION_FACTORY().beginTransaction();
            //Hacemos la consulta con HQL
           List<Usuarios> listaUsuairos = Conexion.SESSION_FACTORY().createQuery("from Usuarios", Usuarios.class).getResultList();
           lista = (ArrayList<Usuarios>) listaUsuairos;
           tr.commit();
        }catch (Exception e){
            if(tr!=null) tr.rollback();
            e.printStackTrace();
        }
        //Devolvemos la lista
        return lista;
    }
    //Buscamos el Usuario por UUID
    public Usuarios findByUUID(String UUIDUsuario){
        //asignamos las variables a null
        Usuarios u = null;
        Transaction tr = null;
        try{
            //si encuentra usuario lo retornamos y si no hacemos rollback
            tr = Conexion.SESSION_FACTORY().beginTransaction();
            for(Usuarios user : this.findAll()){
                System.out.println(user);
                if(user.getId().toString().equals(UUIDUsuario)) u = user;
            }
            tr.commit();
        }catch (Exception e){
            if(tr !=null) tr.rollback();
            e.printStackTrace();
        }

        return u;
    }

    //Buscamos al usuario por email
    public Usuarios findByEmail(String email){
        Usuarios u = null;
        Transaction tr = null;
        try{
            tr = Conexion.SESSION_FACTORY().beginTransaction();
            for(Usuarios user : this.findAll()){
                System.out.println(user);
                if(user.getEmail().equals(email)) u = user;
            }
            tr.commit();
        }catch (Exception e){
            if(tr !=null) tr.rollback();
            e.printStackTrace();
        }

        return u;
    }

    //Metodo de login
    public Usuarios login(String email, String password){
        Usuarios u = null;
        Transaction tr = null;
        try{
            //Si el usuario coincide con el email y con la contraseña encriptada nos lo retorna
            for (Usuarios user : this.findAll()){
                if(user.getEmail().equals(email)){
                    if (BCrypt.verifyer().verify(password.toCharArray(), user.getPasswd()).verified) u = user;
                }
            }
        }catch (Exception e){
            if(tr != null) tr.rollback();
            e.printStackTrace();
        }
        return u;
    }

    public boolean actualizarPassword(Usuarios user,String newPassword){
        boolean estado = false;
        Transaction tr = null;
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            String hash = BCrypt.withDefaults().hashToString(12,newPassword.toCharArray());
            user.setPasswd(hash);
            session.merge(user);
            tr.commit();
            estado = emailRepository.deleteAllByUser(user);
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }
        return estado;
    }

    public void updateGimnasio(Gimnasio g, Usuarios user){
        Transaction tr = null;
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            user.setGimnasio(g);
            session.merge(user);
            tr.commit();
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }
    }


    @Async
    public void enviarEmail(MimeMessage message, JavaMailSender emailSender) {
        emailSender.send(message);
    }
}
