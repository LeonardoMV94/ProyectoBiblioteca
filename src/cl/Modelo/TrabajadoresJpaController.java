/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.IllegalOrphanException;
import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Correos;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Direcciones;
import cl.Entity.Telefonos;
import cl.Entity.Boletas;
import cl.Entity.Arriendos;
import cl.Entity.Trabajadores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class TrabajadoresJpaController implements Serializable {

    public TrabajadoresJpaController() {
    }

    
    public TrabajadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajadores trabajadores) throws PreexistingEntityException, Exception {
        if (trabajadores.getCorreosList() == null) {
            trabajadores.setCorreosList(new ArrayList<Correos>());
        }
        if (trabajadores.getDireccionesList() == null) {
            trabajadores.setDireccionesList(new ArrayList<Direcciones>());
        }
        if (trabajadores.getTelefonosList() == null) {
            trabajadores.setTelefonosList(new ArrayList<Telefonos>());
        }
        if (trabajadores.getBoletasList() == null) {
            trabajadores.setBoletasList(new ArrayList<Boletas>());
        }
        if (trabajadores.getArriendosList() == null) {
            trabajadores.setArriendosList(new ArrayList<Arriendos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Correos> attachedCorreosList = new ArrayList<Correos>();
            for (Correos correosListCorreosToAttach : trabajadores.getCorreosList()) {
                correosListCorreosToAttach = em.getReference(correosListCorreosToAttach.getClass(), correosListCorreosToAttach.getIdcorreo());
                attachedCorreosList.add(correosListCorreosToAttach);
            }
            trabajadores.setCorreosList(attachedCorreosList);
            List<Direcciones> attachedDireccionesList = new ArrayList<Direcciones>();
            for (Direcciones direccionesListDireccionesToAttach : trabajadores.getDireccionesList()) {
                direccionesListDireccionesToAttach = em.getReference(direccionesListDireccionesToAttach.getClass(), direccionesListDireccionesToAttach.getIddireccion());
                attachedDireccionesList.add(direccionesListDireccionesToAttach);
            }
            trabajadores.setDireccionesList(attachedDireccionesList);
            List<Telefonos> attachedTelefonosList = new ArrayList<Telefonos>();
            for (Telefonos telefonosListTelefonosToAttach : trabajadores.getTelefonosList()) {
                telefonosListTelefonosToAttach = em.getReference(telefonosListTelefonosToAttach.getClass(), telefonosListTelefonosToAttach.getIdtelefono());
                attachedTelefonosList.add(telefonosListTelefonosToAttach);
            }
            trabajadores.setTelefonosList(attachedTelefonosList);
            List<Boletas> attachedBoletasList = new ArrayList<Boletas>();
            for (Boletas boletasListBoletasToAttach : trabajadores.getBoletasList()) {
                boletasListBoletasToAttach = em.getReference(boletasListBoletasToAttach.getClass(), boletasListBoletasToAttach.getFolioBoleta());
                attachedBoletasList.add(boletasListBoletasToAttach);
            }
            trabajadores.setBoletasList(attachedBoletasList);
            List<Arriendos> attachedArriendosList = new ArrayList<Arriendos>();
            for (Arriendos arriendosListArriendosToAttach : trabajadores.getArriendosList()) {
                arriendosListArriendosToAttach = em.getReference(arriendosListArriendosToAttach.getClass(), arriendosListArriendosToAttach.getIdArriendo());
                attachedArriendosList.add(arriendosListArriendosToAttach);
            }
            trabajadores.setArriendosList(attachedArriendosList);
            em.persist(trabajadores);
            for (Correos correosListCorreos : trabajadores.getCorreosList()) {
                correosListCorreos.getTrabajadoresList().add(trabajadores);
                correosListCorreos = em.merge(correosListCorreos);
            }
            for (Direcciones direccionesListDirecciones : trabajadores.getDireccionesList()) {
                direccionesListDirecciones.getTrabajadoresList().add(trabajadores);
                direccionesListDirecciones = em.merge(direccionesListDirecciones);
            }
            for (Telefonos telefonosListTelefonos : trabajadores.getTelefonosList()) {
                telefonosListTelefonos.getTrabajadoresList().add(trabajadores);
                telefonosListTelefonos = em.merge(telefonosListTelefonos);
            }
            for (Boletas boletasListBoletas : trabajadores.getBoletasList()) {
                Trabajadores oldTrabajadoresRutTrabajadorOfBoletasListBoletas = boletasListBoletas.getTrabajadoresRutTrabajador();
                boletasListBoletas.setTrabajadoresRutTrabajador(trabajadores);
                boletasListBoletas = em.merge(boletasListBoletas);
                if (oldTrabajadoresRutTrabajadorOfBoletasListBoletas != null) {
                    oldTrabajadoresRutTrabajadorOfBoletasListBoletas.getBoletasList().remove(boletasListBoletas);
                    oldTrabajadoresRutTrabajadorOfBoletasListBoletas = em.merge(oldTrabajadoresRutTrabajadorOfBoletasListBoletas);
                }
            }
            for (Arriendos arriendosListArriendos : trabajadores.getArriendosList()) {
                Trabajadores oldTrabajadoresRutTrabajadorOfArriendosListArriendos = arriendosListArriendos.getTrabajadoresRutTrabajador();
                arriendosListArriendos.setTrabajadoresRutTrabajador(trabajadores);
                arriendosListArriendos = em.merge(arriendosListArriendos);
                if (oldTrabajadoresRutTrabajadorOfArriendosListArriendos != null) {
                    oldTrabajadoresRutTrabajadorOfArriendosListArriendos.getArriendosList().remove(arriendosListArriendos);
                    oldTrabajadoresRutTrabajadorOfArriendosListArriendos = em.merge(oldTrabajadoresRutTrabajadorOfArriendosListArriendos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrabajadores(trabajadores.getRutTrabajador()) != null) {
                throw new PreexistingEntityException("Trabajadores " + trabajadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajadores trabajadores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajadores persistentTrabajadores = em.find(Trabajadores.class, trabajadores.getRutTrabajador());
            List<Correos> correosListOld = persistentTrabajadores.getCorreosList();
            List<Correos> correosListNew = trabajadores.getCorreosList();
            List<Direcciones> direccionesListOld = persistentTrabajadores.getDireccionesList();
            List<Direcciones> direccionesListNew = trabajadores.getDireccionesList();
            List<Telefonos> telefonosListOld = persistentTrabajadores.getTelefonosList();
            List<Telefonos> telefonosListNew = trabajadores.getTelefonosList();
            List<Boletas> boletasListOld = persistentTrabajadores.getBoletasList();
            List<Boletas> boletasListNew = trabajadores.getBoletasList();
            List<Arriendos> arriendosListOld = persistentTrabajadores.getArriendosList();
            List<Arriendos> arriendosListNew = trabajadores.getArriendosList();
            List<String> illegalOrphanMessages = null;
            for (Boletas boletasListOldBoletas : boletasListOld) {
                if (!boletasListNew.contains(boletasListOldBoletas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boletas " + boletasListOldBoletas + " since its trabajadoresRutTrabajador field is not nullable.");
                }
            }
            for (Arriendos arriendosListOldArriendos : arriendosListOld) {
                if (!arriendosListNew.contains(arriendosListOldArriendos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Arriendos " + arriendosListOldArriendos + " since its trabajadoresRutTrabajador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Correos> attachedCorreosListNew = new ArrayList<Correos>();
            for (Correos correosListNewCorreosToAttach : correosListNew) {
                correosListNewCorreosToAttach = em.getReference(correosListNewCorreosToAttach.getClass(), correosListNewCorreosToAttach.getIdcorreo());
                attachedCorreosListNew.add(correosListNewCorreosToAttach);
            }
            correosListNew = attachedCorreosListNew;
            trabajadores.setCorreosList(correosListNew);
            List<Direcciones> attachedDireccionesListNew = new ArrayList<Direcciones>();
            for (Direcciones direccionesListNewDireccionesToAttach : direccionesListNew) {
                direccionesListNewDireccionesToAttach = em.getReference(direccionesListNewDireccionesToAttach.getClass(), direccionesListNewDireccionesToAttach.getIddireccion());
                attachedDireccionesListNew.add(direccionesListNewDireccionesToAttach);
            }
            direccionesListNew = attachedDireccionesListNew;
            trabajadores.setDireccionesList(direccionesListNew);
            List<Telefonos> attachedTelefonosListNew = new ArrayList<Telefonos>();
            for (Telefonos telefonosListNewTelefonosToAttach : telefonosListNew) {
                telefonosListNewTelefonosToAttach = em.getReference(telefonosListNewTelefonosToAttach.getClass(), telefonosListNewTelefonosToAttach.getIdtelefono());
                attachedTelefonosListNew.add(telefonosListNewTelefonosToAttach);
            }
            telefonosListNew = attachedTelefonosListNew;
            trabajadores.setTelefonosList(telefonosListNew);
            List<Boletas> attachedBoletasListNew = new ArrayList<Boletas>();
            for (Boletas boletasListNewBoletasToAttach : boletasListNew) {
                boletasListNewBoletasToAttach = em.getReference(boletasListNewBoletasToAttach.getClass(), boletasListNewBoletasToAttach.getFolioBoleta());
                attachedBoletasListNew.add(boletasListNewBoletasToAttach);
            }
            boletasListNew = attachedBoletasListNew;
            trabajadores.setBoletasList(boletasListNew);
            List<Arriendos> attachedArriendosListNew = new ArrayList<Arriendos>();
            for (Arriendos arriendosListNewArriendosToAttach : arriendosListNew) {
                arriendosListNewArriendosToAttach = em.getReference(arriendosListNewArriendosToAttach.getClass(), arriendosListNewArriendosToAttach.getIdArriendo());
                attachedArriendosListNew.add(arriendosListNewArriendosToAttach);
            }
            arriendosListNew = attachedArriendosListNew;
            trabajadores.setArriendosList(arriendosListNew);
            trabajadores = em.merge(trabajadores);
            for (Correos correosListOldCorreos : correosListOld) {
                if (!correosListNew.contains(correosListOldCorreos)) {
                    correosListOldCorreos.getTrabajadoresList().remove(trabajadores);
                    correosListOldCorreos = em.merge(correosListOldCorreos);
                }
            }
            for (Correos correosListNewCorreos : correosListNew) {
                if (!correosListOld.contains(correosListNewCorreos)) {
                    correosListNewCorreos.getTrabajadoresList().add(trabajadores);
                    correosListNewCorreos = em.merge(correosListNewCorreos);
                }
            }
            for (Direcciones direccionesListOldDirecciones : direccionesListOld) {
                if (!direccionesListNew.contains(direccionesListOldDirecciones)) {
                    direccionesListOldDirecciones.getTrabajadoresList().remove(trabajadores);
                    direccionesListOldDirecciones = em.merge(direccionesListOldDirecciones);
                }
            }
            for (Direcciones direccionesListNewDirecciones : direccionesListNew) {
                if (!direccionesListOld.contains(direccionesListNewDirecciones)) {
                    direccionesListNewDirecciones.getTrabajadoresList().add(trabajadores);
                    direccionesListNewDirecciones = em.merge(direccionesListNewDirecciones);
                }
            }
            for (Telefonos telefonosListOldTelefonos : telefonosListOld) {
                if (!telefonosListNew.contains(telefonosListOldTelefonos)) {
                    telefonosListOldTelefonos.getTrabajadoresList().remove(trabajadores);
                    telefonosListOldTelefonos = em.merge(telefonosListOldTelefonos);
                }
            }
            for (Telefonos telefonosListNewTelefonos : telefonosListNew) {
                if (!telefonosListOld.contains(telefonosListNewTelefonos)) {
                    telefonosListNewTelefonos.getTrabajadoresList().add(trabajadores);
                    telefonosListNewTelefonos = em.merge(telefonosListNewTelefonos);
                }
            }
            for (Boletas boletasListNewBoletas : boletasListNew) {
                if (!boletasListOld.contains(boletasListNewBoletas)) {
                    Trabajadores oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas = boletasListNewBoletas.getTrabajadoresRutTrabajador();
                    boletasListNewBoletas.setTrabajadoresRutTrabajador(trabajadores);
                    boletasListNewBoletas = em.merge(boletasListNewBoletas);
                    if (oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas != null && !oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas.equals(trabajadores)) {
                        oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas.getBoletasList().remove(boletasListNewBoletas);
                        oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas = em.merge(oldTrabajadoresRutTrabajadorOfBoletasListNewBoletas);
                    }
                }
            }
            for (Arriendos arriendosListNewArriendos : arriendosListNew) {
                if (!arriendosListOld.contains(arriendosListNewArriendos)) {
                    Trabajadores oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos = arriendosListNewArriendos.getTrabajadoresRutTrabajador();
                    arriendosListNewArriendos.setTrabajadoresRutTrabajador(trabajadores);
                    arriendosListNewArriendos = em.merge(arriendosListNewArriendos);
                    if (oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos != null && !oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos.equals(trabajadores)) {
                        oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos.getArriendosList().remove(arriendosListNewArriendos);
                        oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos = em.merge(oldTrabajadoresRutTrabajadorOfArriendosListNewArriendos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = trabajadores.getRutTrabajador();
                if (findTrabajadores(id) == null) {
                    throw new NonexistentEntityException("The trabajadores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajadores trabajadores;
            try {
                trabajadores = em.getReference(Trabajadores.class, id);
                trabajadores.getRutTrabajador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajadores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Boletas> boletasListOrphanCheck = trabajadores.getBoletasList();
            for (Boletas boletasListOrphanCheckBoletas : boletasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajadores (" + trabajadores + ") cannot be destroyed since the Boletas " + boletasListOrphanCheckBoletas + " in its boletasList field has a non-nullable trabajadoresRutTrabajador field.");
            }
            List<Arriendos> arriendosListOrphanCheck = trabajadores.getArriendosList();
            for (Arriendos arriendosListOrphanCheckArriendos : arriendosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajadores (" + trabajadores + ") cannot be destroyed since the Arriendos " + arriendosListOrphanCheckArriendos + " in its arriendosList field has a non-nullable trabajadoresRutTrabajador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Correos> correosList = trabajadores.getCorreosList();
            for (Correos correosListCorreos : correosList) {
                correosListCorreos.getTrabajadoresList().remove(trabajadores);
                correosListCorreos = em.merge(correosListCorreos);
            }
            List<Direcciones> direccionesList = trabajadores.getDireccionesList();
            for (Direcciones direccionesListDirecciones : direccionesList) {
                direccionesListDirecciones.getTrabajadoresList().remove(trabajadores);
                direccionesListDirecciones = em.merge(direccionesListDirecciones);
            }
            List<Telefonos> telefonosList = trabajadores.getTelefonosList();
            for (Telefonos telefonosListTelefonos : telefonosList) {
                telefonosListTelefonos.getTrabajadoresList().remove(trabajadores);
                telefonosListTelefonos = em.merge(telefonosListTelefonos);
            }
            em.remove(trabajadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trabajadores> findTrabajadoresEntities() {
        return findTrabajadoresEntities(true, -1, -1);
    }

    public List<Trabajadores> findTrabajadoresEntities(int maxResults, int firstResult) {
        return findTrabajadoresEntities(false, maxResults, firstResult);
    }

    private List<Trabajadores> findTrabajadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajadores.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Trabajadores findTrabajadores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajadores> rt = cq.from(Trabajadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
