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
import com.hibernate.model.Administradores;
import java.util.List;
import org.hibernate.Session;


public class AdministradorDAO extends HibernateUtil {
	
	// property constants

         public Boolean save(Administradores transientInstance) {
		//log.debug("saving TblAbwUsuario instance");
		try {
			Session s = getSession();
                        s.beginTransaction();
                        s.save(transientInstance);
                        s.getTransaction().commit();
			//log.debug("save successful");
                        return true;
		} catch (RuntimeException re) {
			//log.error("save failed", re);  
                    return false;
		}
	}
         
         public Boolean update(Administradores transientInstance) {
        //log.debug("saving TblAbwUsuario instance");
        Session s = getSession();
        try {
            s.beginTransaction();
            s.update(transientInstance);
            s.getTransaction().commit();
            s.close();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);  
            s.close();
            return false;
        }
    }
         
        public Boolean delete(Administradores transientInstance) {
		//log.debug("saving TblAbwUsuario instance");
		try {
			Session s = getSession();
                        s.beginTransaction();
                        s.delete(transientInstance);
                        s.getTransaction().commit();
			//log.debug("save successful");
                        return true;
		} catch (RuntimeException re) {
			//log.error("save failed", re);
                    return false;
		}
	}
         
        public Administradores findById(String id) {
		//log.debug("getting TblAbwUsuario instance with id: " + id);
		try {
			Administradores instance = (Administradores) getSession().get(
					Administradores.class, id);
                        System.out.println(instance.getTelParticular()+" "+instance.getUsuarios().getNombreUsuario());
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
        
        

        public List<Administradores> listarById(String id) {
        System.err.println("Bandera 1");
        Session s = getSession();
        try
        {
            System.err.println("Bandera 2");
            s.beginTransaction();
            System.err.println("Bandera 3");
            List<Administradores> listAdmins = (List<Administradores>)s.createQuery("from DomicilioAdministradores where Administradores_tel_particular='"+id+"'").list();
            System.err.println("Bandera 4");
            s.getTransaction().commit();
            System.err.println("Bandera 5");
            s.close();
            return listAdmins;
        }
        catch(Exception e)
        {
            s.close();
            return null;
        }
    }
}
