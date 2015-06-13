/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hibernate.dao;

/**
 *
 * @author Alejandro
 */
import com.hibernate.cfg.HibernateUtil;
import static com.hibernate.cfg.HibernateUtil.getSession;
import com.hibernate.model.Usuarios;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAO extends HibernateUtil {

	// property constants
    public Boolean save(Usuarios transientInstance) {
        //log.debug("saving TblAbwUsuario instance");
        try {
            Session s = getSession();
            s.beginTransaction();
            s.save(transientInstance);
            s.getTransaction().commit();
            s.close();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);  
            return false;
        }
    }

    public Boolean delete(Usuarios transientInstance) {
        //log.debug("saving TblAbwUsuario instance");
        try {
            Session s = getSession();
            s.beginTransaction();
            s.delete(transientInstance);
            s.getTransaction().commit();
            s.close();
            System.out.println("--->Usuario eliminado");
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            System.out.println("---->Error no se elimino el usuario");
            //log.error("save failed", re);
            return false;
        }
    }

    public Boolean update(Usuarios transientInstance) {
        Session s = getSession();
        try {

            s.beginTransaction();
            s.update(transientInstance);
            s.getTransaction().commit();
            s.close();
            System.out.println("--->Usuario actualizado");
            return true;
        } catch (RuntimeException re) {
            s.close();
            System.out.println("--->Usuario no actualizado");
            return false;
        }
    }

    public Usuarios findById(Session s, String id) {
        //log.debug("getting TblAbwUsuario instance with id: " + id);
        try {
            Usuarios instance = (Usuarios) s.get(
                    Usuarios.class, id);
            return instance;
        } catch (RuntimeException re) {
            //log.error("get failed", re);
            throw re;
        }
    }

    public Boolean deleteHospital(String nombreUsuario) {
        Session s = getSession();
        try {

            s.beginTransaction();
            Query q = s.createQuery("from Usuarios where nombre_usuario = :nombre_usuario");
            q.setParameter("nombre_usuario", nombreUsuario);

            Usuarios instance = (Usuarios) q.list().get(0);
            s.delete(instance);
            s.getTransaction().commit();
            s.close();
            return true;
        } catch (RuntimeException re) {
            s.close();
            return false;
        }
    }

    public boolean deleteAdmin(String nombreUsuarioEdit) {
        try {
            Session s = getSession();
            s.beginTransaction();
            Query q = s.createQuery("from Usuarios where nombre_usuario = :nombre_usuario");
            q.setParameter("nombre_usuario", nombreUsuarioEdit);
            Usuarios usuario = (Usuarios) q.list().get(0);
            s.delete(usuario);
            s.getTransaction().commit();
            s.close();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {

            //log.error("save failed", re);
            return false;
        }

    }

    public boolean deletePaciente(String nombreUsuarioEdit) {
        try {
            Session s = getSession();
            s.beginTransaction();
            Query q = s.createQuery("from Usuarios where nombre_usuario = :nombre_usuario");
            q.setParameter("nombre_usuario", nombreUsuarioEdit);
            Usuarios usuario = (Usuarios) q.list().get(0);
            s.delete(usuario);
            s.getTransaction().commit();
            s.close();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);
            return false;
        }
    }

    public List<Usuarios> listarById(Session s, String id) {
        try {
            System.err.println("Bandera 1");
            System.err.println("Bandera 2");
            s.beginTransaction();
            System.err.println("Bandera 3");
            List<Usuarios> listUsuarios = (List<Usuarios>) s.createQuery("from Usuarios where nombreUsuario='" + id + "'").list();
            System.err.println("Bandera 4");
            s.getTransaction().commit();
            System.err.println("Bandera 5");
            return listUsuarios;

        } catch (Exception e) {
            return null;
        }

    }

    public List<Usuarios> listar(Session s, int from, int to) {
        try {
            System.err.println("Bandera 1");
            System.err.println("Bandera 2");
            s.beginTransaction();
            System.err.println("Bandera 3");
            List<Usuarios> listUsuarios = (List<Usuarios>) s.createQuery("from Usuarios where tipoUsuario='Administrador'").list();
            System.err.println("Bandera 4");
            s.getTransaction().commit();
            System.err.println("Bandera 5");
            return listUsuarios;

        } catch (Exception e) {
            return null;
        }

    }

    public List<Usuarios> findUsuariosLike(Session s, String flitroNombreUsuario) {
        try {
            String queryString = "from Usuarios where tipoUsuario='Administrador'and lower(nombreUsuario) LIKE (:searchKeyword)";
            Query queryObject = s.createQuery(queryString);
            queryObject.setParameter("searchKeyword", "%" + flitroNombreUsuario + "%");
            return queryObject.list();

        } catch (RuntimeException re) {
            throw re;
        } finally {
            //getSession().close();
        }

    }

    public List<Usuarios> findPacientesLike(Session s, String flitroNombreUsuario) {
        try {
            String queryString = "from Usuarios where tipoUsuario='Paciente'and lower(nombreUsuario) LIKE (:searchKeyword)";
            Query queryObject = s.createQuery(queryString);
            queryObject.setParameter("searchKeyword", "%" + flitroNombreUsuario + "%");
            return queryObject.list();

        } catch (RuntimeException re) {
            throw re;
        } finally {
            //getSession().close();
        }

    }

    public List<Usuarios> listarPacientes(Session s, int from, int to) {
        try {
            System.err.println("Bandera 1");
            System.err.println("Bandera 2");
            s.beginTransaction();
            System.err.println("Bandera 3");
            List<Usuarios> listUsuarios = (List<Usuarios>) s.createQuery("from Usuarios where tipoUsuario='Paciente'").list();
            System.err.println("Bandera 4");
            s.getTransaction().commit();
            System.err.println("Bandera 5");
            return listUsuarios;

        } catch (Exception e) {
            return null;
        }

    }

}
