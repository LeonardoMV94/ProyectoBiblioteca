/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.IllegalOrphanException;
import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Modelo.exceptions.PreexistingEntityException;
import cl.Entity.Distribuidores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Facturas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class DistribuidoresJpaController implements Serializable {

    public DistribuidoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DistribuidoresJpaController() {
    }

    public void create(Distribuidores distribuidores) throws PreexistingEntityException, Exception {
        if (distribuidores.getFacturasList() == null) {
            distribuidores.setFacturasList(new ArrayList<Facturas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Facturas> attachedFacturasList = new ArrayList<Facturas>();
            for (Facturas facturasListFacturasToAttach : distribuidores.getFacturasList()) {
                facturasListFacturasToAttach = em.getReference(facturasListFacturasToAttach.getClass(), facturasListFacturasToAttach.getFolioFactura());
                attachedFacturasList.add(facturasListFacturasToAttach);
            }
            distribuidores.setFacturasList(attachedFacturasList);
            em.persist(distribuidores);
            for (Facturas facturasListFacturas : distribuidores.getFacturasList()) {
                Distribuidores oldDistribuidoresRutDistribuidorOfFacturasListFacturas = facturasListFacturas.getDistribuidoresRutDistribuidor();
                facturasListFacturas.setDistribuidoresRutDistribuidor(distribuidores);
                facturasListFacturas = em.merge(facturasListFacturas);
                if (oldDistribuidoresRutDistribuidorOfFacturasListFacturas != null) {
                    oldDistribuidoresRutDistribuidorOfFacturasListFacturas.getFacturasList().remove(facturasListFacturas);
                    oldDistribuidoresRutDistribuidorOfFacturasListFacturas = em.merge(oldDistribuidoresRutDistribuidorOfFacturasListFacturas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDistribuidores(distribuidores.getRutDistribuidor()) != null) {
                throw new PreexistingEntityException("Distribuidores " + distribuidores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Distribuidores distribuidores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distribuidores persistentDistribuidores = em.find(Distribuidores.class, distribuidores.getRutDistribuidor());
            List<Facturas> facturasListOld = persistentDistribuidores.getFacturasList();
            List<Facturas> facturasListNew = distribuidores.getFacturasList();
            List<String> illegalOrphanMessages = null;
            for (Facturas facturasListOldFacturas : facturasListOld) {
                if (!facturasListNew.contains(facturasListOldFacturas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Facturas " + facturasListOldFacturas + " since its distribuidoresRutDistribuidor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Facturas> attachedFacturasListNew = new ArrayList<Facturas>();
            for (Facturas facturasListNewFacturasToAttach : facturasListNew) {
                facturasListNewFacturasToAttach = em.getReference(facturasListNewFacturasToAttach.getClass(), facturasListNewFacturasToAttach.getFolioFactura());
                attachedFacturasListNew.add(facturasListNewFacturasToAttach);
            }
            facturasListNew = attachedFacturasListNew;
            distribuidores.setFacturasList(facturasListNew);
            distribuidores = em.merge(distribuidores);
            for (Facturas facturasListNewFacturas : facturasListNew) {
                if (!facturasListOld.contains(facturasListNewFacturas)) {
                    Distribuidores oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas = facturasListNewFacturas.getDistribuidoresRutDistribuidor();
                    facturasListNewFacturas.setDistribuidoresRutDistribuidor(distribuidores);
                    facturasListNewFacturas = em.merge(facturasListNewFacturas);
                    if (oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas != null && !oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas.equals(distribuidores)) {
                        oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas.getFacturasList().remove(facturasListNewFacturas);
                        oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas = em.merge(oldDistribuidoresRutDistribuidorOfFacturasListNewFacturas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = distribuidores.getRutDistribuidor();
                if (findDistribuidores(id) == null) {
                    throw new NonexistentEntityException("The distribuidores with id " + id + " no longer exists.");
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
            Distribuidores distribuidores;
            try {
                distribuidores = em.getReference(Distribuidores.class, id);
                distribuidores.getRutDistribuidor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The distribuidores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Facturas> facturasListOrphanCheck = distribuidores.getFacturasList();
            for (Facturas facturasListOrphanCheckFacturas : facturasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Distribuidores (" + distribuidores + ") cannot be destroyed since the Facturas " + facturasListOrphanCheckFacturas + " in its facturasList field has a non-nullable distribuidoresRutDistribuidor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(distribuidores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Distribuidores> findDistribuidoresEntities() {
        return findDistribuidoresEntities(true, -1, -1);
    }

    public List<Distribuidores> findDistribuidoresEntities(int maxResults, int firstResult) {
        return findDistribuidoresEntities(false, maxResults, firstResult);
    }

    private List<Distribuidores> findDistribuidoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Distribuidores.class));
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

    public Distribuidores findDistribuidores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Distribuidores.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistribuidoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Distribuidores> rt = cq.from(Distribuidores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
