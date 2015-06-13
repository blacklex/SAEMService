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
import com.hibernate.model.DomicilioHospitales;
import org.hibernate.Session;

public class DomicilioHospitalDAO extends HibernateUtil {

	// property constants
    public Boolean save(DomicilioHospitales transientInstance) {
        //log.debug("saving TblAbwUsuario instance");
        try {
            Session s = getSession();
            s.beginTransaction();
            s.save(transientInstance);
            s.getTransaction().commit();
            //log.debug("save successful");
            s.close();
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);  
            return false;
        } finally {
            getSession().close();
        }
    }

    public Boolean delete(DomicilioHospitales transientInstance) {
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
        } finally {
            getSession().close();
        }
    }

    public Boolean update(DomicilioHospitales transientInstance) {
        Session s = getSession();
        try {

            s.beginTransaction();
            s.update(transientInstance);
            s.getTransaction().commit();
            s.close();
            System.out.println("--->Domicikio Hospital actualizado");
            return true;
        } catch (RuntimeException re) {
//                    System.out.println(re.getCause().getMessage());
            s.close();
            System.out.println("--->Domicilio Hospital no actualizado");
            return false;
        } finally {
            getSession().close();
        }
    }

    public DomicilioHospitales findById(Long id) {
        //log.debug("getting TblAbwUsuario instance with id: " + id);
        try {
            DomicilioHospitales instance = (DomicilioHospitales) getSession().get(
                    DomicilioHospitales.class, id);
            return instance;
        } catch (RuntimeException re) {
            //log.error("get failed", re);
            throw re;
        } finally {
            getSession().close();
        }
    }

}
