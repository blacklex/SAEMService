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
import com.hibernate.model.DomicilioAdministradores;
import org.hibernate.Session;


public class DomicilioAdministradorDAO extends HibernateUtil {
	
	// property constants

         public Boolean save(DomicilioAdministradores transientInstance) {
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
         
         public Boolean update(DomicilioAdministradores transientInstance) {
		//log.debug("saving TblAbwUsuario instance");
		try {
			Session s = getSession();
                        s.beginTransaction();
                        s.update(transientInstance);
                        s.getTransaction().commit();
			//log.debug("save successful");
                        return true;
		} catch (RuntimeException re) {
			//log.error("save failed", re);  
                    return false;
		}
	}
         
        public Boolean delete(DomicilioAdministradores transientInstance) {
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
         
        public DomicilioAdministradores findById(Long id) {
		//log.debug("getting TblAbwUsuario instance with id: " + id);
		try {
			DomicilioAdministradores instance = (DomicilioAdministradores) getSession().get(
					DomicilioAdministradores.class, id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
         
        public DomicilioAdministradores findByClaveForanea(String id) {
		//log.debug("getting TblAbwUsuario instance with id: " + id);
		try {
			DomicilioAdministradores domAdmins;
			Session s = getSession();
                        s.beginTransaction();
                        domAdmins = (DomicilioAdministradores) s.createQuery("from DomicilioAdministradores where Administradores_tel_particular = '"+id+"'").uniqueResult();
                        s.getTransaction().commit();
                        return domAdmins;
		} catch(Exception e)
        {
            return null;
        }
	}
      

}
