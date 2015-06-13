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
import com.hibernate.model.Hospitales;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class HospitalDAO extends HibernateUtil {

    // property constants
    public Boolean save(Hospitales transientInstance) {
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
        } finally {
            getSession().close();
        }
    }

    public Boolean delete(Hospitales transientInstance) {
        try {
            Session s = getSession();
            s.beginTransaction();
            s.delete(transientInstance);
            s.getTransaction().commit();
            s.close();
            //log.debug("save successful");
            return true;
        } catch (RuntimeException re) {
            //log.error("save failed", re);
            return false;
        } finally {
            getSession().close();
        }
    }

    public Boolean update(Hospitales transientInstance) {
        Session s = getSession();
        try {

            s.beginTransaction();
            s.update(transientInstance);
            s.getTransaction().commit();
            s.close();
            System.out.println("--->Hospital actualizado");
            return true;
        } catch (RuntimeException re) {
//                    System.out.println(re.getCause().getMessage());
            s.close();
            System.out.println("--->Hospital no actualizado");
            return false;
        } finally {
            getSession().close();
        }
    }

    public Hospitales findById(Session s, String id) {

        try {
            Hospitales instance = (Hospitales) s.get(
                    Hospitales.class, id);

            return instance;
        } catch (RuntimeException re) {
            //log.error("get failed", re);
            throw re;
        } finally {
            //getSession().close();
        }
    }

    public List<Hospitales> findAll(Session s) {
        try {
            String queryString = "from Hospitales";
            Query queryObject = s.createQuery(queryString);
            //getSession().close();
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        } finally {
            //getSession().close();
        }
    }

    public List<Hospitales> findHospitalLike(Session s, String hospitalNombre) {
        try {
            String queryString = "from Hospitales where lower(nombre) LIKE (:searchKeyword)";
            Query queryObject = s.createQuery(queryString);
            queryObject.setParameter("searchKeyword", "%" + hospitalNombre + "%");
            return queryObject.list();

        } catch (RuntimeException re) {
            throw re;
        } finally {
            //getSession().close();
        }
    }

    public Boolean addHospitalEspecialidad(String codigoHospital, int noEspecialidad) {
        String query = "INSERT INTO Especialidades_has_Hospitales (Especialidades_no_especialidad,Hospitales_codigo_hospital) "
                + "VALUES(" + noEspecialidad + ",'" + codigoHospital + "')";
        System.out.println("--->" + query);
        Session s = getSession();
        s.beginTransaction();
        try {
            SQLQuery queryObject = s.createSQLQuery(query);
            queryObject.executeUpdate();
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            s.close();
        }
    }

    public Boolean deleteHospitalEspecialidad(String codigoHospital) {
        String query = "DELETE FROM Especialidades_has_Hospitales "
                + "WHERE Hospitales_codigo_hospital='" + codigoHospital + "';";
        System.out.println("--->" + query);
        Session s = getSession();
        s.beginTransaction();
        try {
            SQLQuery queryObject = s.createSQLQuery(query);
            queryObject.executeUpdate();
            s.getTransaction().commit();
            System.out.println("***");
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            s.close();
        }
    }

}
