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
import com.hibernate.model.PeticionesSalientes;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class PeticionesSalientesDAO extends HibernateUtil {

    // property constants
    public Boolean save(PeticionesSalientes transientInstance) {
        Session s = getSession();
        //log.debug("saving TblAbwUsuario instance");
        try {

            s.beginTransaction();
            s.save(transientInstance);
            s.getTransaction().commit();

            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {

            //log.error("save failed", re);  
            return false;
        } finally {
            s.close();
        }
    }

    public Boolean delete(PeticionesSalientes transientInstance) {
        //log.debug("saving TblAbwUsuario instance");
        Session s = getSession();
        try {
            s.beginTransaction();
            s.delete(transientInstance);
            s.getTransaction().commit();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);
            return false;
        } finally {
            s.close();
        }
    }

    public PeticionesSalientes findById(Session s, String id) {
        //log.debug("getting TblAbwUsuario instance with id: " + id);

        try {
            PeticionesSalientes instance = (PeticionesSalientes) s.get(
                    PeticionesSalientes.class, id);
            return instance;
        } catch (RuntimeException re) {
            //log.error("get failed", re);
            throw re;
        } finally {

        }
    }

    public Boolean update(PeticionesSalientes transientInstance) {
        Session s = getSession();
        try {
            PeticionesSalientes p = findById(s, transientInstance.getIdPeticionesSalientes());
            p.setComentario(transientInstance.getComentario());
            p.setEstatus(transientInstance.getEstatus());

            s.beginTransaction();
            s.update(p);
            s.getTransaction().commit();
            s.close();
            System.out.println("--->Peticion sal actualizado");
            return true;
        } catch (RuntimeException re) {
            s.close();
            System.out.println("--->Peticion sal no actualizado");
            return false;
        }
    }

    public List<PeticionesSalientes> findAll(Session s) {
        try {
            String queryString = "from PeticionesSalientes";
            Query queryObject = s.createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        } finally {
            //s.close();
        }
    }

    public List<PeticionesSalientes> findAllByHospital(Session s, String codigoHospital) {
        //Session s = getSession();
        try {
            String queryString = "from PeticionesSalientes where Hospitales_codigo_hospital =:codigoHospital and estatus='PP' order by prioridad";
            Query queryObject = s.createQuery(queryString);
            queryObject.setParameter("codigoHospital", codigoHospital);
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        } finally {

        }
    }

    public List<PeticionesSalientes> finByHospitalNss(Session s, String nss) {
        try {
            String queryString = "from PeticionesSalientes where Pacientes_nss =:nss and estatus='PP'";
            Query queryObject = s.createQuery(queryString);
            queryObject.setParameter("nss", nss);
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        } finally {

        }
    }

}
