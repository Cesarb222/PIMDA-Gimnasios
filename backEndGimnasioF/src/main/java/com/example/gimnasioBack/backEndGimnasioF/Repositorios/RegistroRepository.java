package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Registro;
import com.example.gimnasioBack.backEndGimnasioF.Models.UsuarioRegistro;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RegistroRepository {
    public RegistroRepository(){

    }

    //Funcion para acceder a los registros de un usuario
    public List<Registro> getRegistrosUser(UUID uuid){
        List<Registro> lista = new ArrayList<>();
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            Usuarios user = session.find(Usuarios.class, uuid);
            lista = session.createQuery("from Registro WHERE usuario=: id_usuario ORDER BY f_registro ASC",Registro.class)
                    .setParameter("id_usuario",user)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //cerramos la session
            if (session != null) session.close();
        }
        return lista;
    }

    //Funcion patra buscar un registro por su id
    public Registro findById(int id){
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            return session.find(Registro.class,id);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return null;
    }

    //Funcion para mostrar todos los registros publicos
    public List<Registro> findByAll(){
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            return session.createQuery("from Registro WHERE publico = true",Registro.class).getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return null;
    }

    //Funcion para buscar los comentarios de una publicacion
    public List<UsuarioRegistro> findAllComentariosById(Registro registro){
        List<UsuarioRegistro> lista = new ArrayList<UsuarioRegistro>();
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            lista = session.createQuery("from UsuarioRegistro WHERE registro=:registro ORDER BY f_publicacion DESC",UsuarioRegistro.class)
                            .setParameter("registro",registro)
                            .getResultList();
        }catch (Exception e){
            if (session!=null) session.close();
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return lista;
    }

    //Funcion para añadir el registro
    public Registro addRegistro( Registro registro){
        Transaction tr = null;
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.persist(registro);
            tr.commit();
            return registro;
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return null;
    }

    //Funcion para añadir comentario al registro
    public boolean addComentario( UsuarioRegistro registro){
        Transaction tr = null;
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.persist(registro);
            tr.commit();
            return true;
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return false;
    }
}
