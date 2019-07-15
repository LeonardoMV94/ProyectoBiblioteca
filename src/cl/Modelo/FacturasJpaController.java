/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.MetodoPago;
import cl.Entity.Distribuidores;
import cl.Entity.EjemplarLibros;
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
public class FacturasJpaController implements Serializable {

    public FacturasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public FacturasJpaController() {
    }

    public void create(Facturas facturas) {
        if (facturas.getEjemplarLibrosList() == null) {
            facturas.setEjemplarLibrosList(new ArrayList<EjemplarLibros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MetodoPago idMetodoPago = facturas.getIdMetodoPago();
            if (idMetodoPago != null) {
                idMetodoPago = em.getReference(idMetodoPago.getClass(), idMetodoPago.getIdMetodoPago());
                facturas.setIdMetodoPago(idMetodoPago);
            }
            Distribuidores distribuidoresRutDistribuidor = facturas.getDistribuidoresRutDistribuidor();
            if (distribuidoresRutDistribuidor != null) {
                distribuidoresRutDistribuidor = em.getReference(distribuidoresRutDistribuidor.getClass(), distribuidoresRutDistribuidor.getRutDistribuidor());
                facturas.setDistribuidoresRutDistribuidor(distribuidoresRutDistribuidor);
            }
            List<EjemplarLibros> attachedEjemplarLibrosList = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibrosToAttach : facturas.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListEjemplarLibrosToAttach.getClass(), ejemplarLibrosListEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosList.add(ejemplarLibrosListEjemplarLibrosToAttach);
            }
            facturas.setEjemplarLibrosList(attachedEjemplarLibrosList);
            em.persist(facturas);
            if (idMetodoPago != null) {
                idMetodoPago.getFacturasList().add(facturas);
                idMetodoPago = em.merge(idMetodoPago);
            }
            if (distribuidoresRutDistribuidor != null) {
                distribuidoresRutDistribuidor.getFacturasList().add(facturas);
                distribuidoresRutDistribuidor = em.merge(distribuidoresRutDistribuidor);
            }
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : facturas.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibros.getFacturasList().add(facturas);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturas facturas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas persistentFacturas = em.find(Facturas.class, facturas.getFolioFactura());
            MetodoPago idMetodoPagoOld = persistentFacturas.getIdMetodoPago();
            MetodoPago idMetodoPagoNew = facturas.getIdMetodoPago();
            Distribuidores distribuidoresRutDistribuidorOld = persistentFacturas.getDistribuidoresRutDistribuidor();
            Distribuidores distribuidoresRutDistribuidorNew = facturas.getDistribuidoresRutDistribuidor();
            List<EjemplarLibros> ejemplarLibrosListOld = persistentFacturas.getEjemplarLibrosList();
            List<EjemplarLibros> ejemplarLibrosListNew = facturas.getEjemplarLibrosList();
            if (idMetodoPagoNew != null) {
                idMetodoPagoNew = em.getReference(idMetodoPagoNew.getClass(), idMetodoPagoNew.getIdMetodoPago());
                facturas.setIdMetodoPago(idMetodoPagoNew);
            }
            if (distribuidoresRutDistribuidorNew != null) {
                distribuidoresRutDistribuidorNew = em.getReference(distribuidoresRutDistribuidorNew.getClass(), distribuidoresRutDistribuidorNew.getRutDistribuidor());
                facturas.setDistribuidoresRutDistribuidor(distribuidoresRutDistribuidorNew);
            }
            List<EjemplarLibros> attachedEjemplarLibrosListNew = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibrosToAttach : ejemplarLibrosListNew) {
                ejemplarLibrosListNewEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListNewEjemplarLibrosToAttach.getClass(), ejemplarLibrosListNewEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosListNew.add(ejemplarLibrosListNewEjemplarLibrosToAttach);
            }
            ejemplarLibrosListNew = attachedEjemplarLibrosListNew;
            facturas.setEjemplarLibrosList(ejemplarLibrosListNew);
            facturas = em.merge(facturas);
            if (idMetodoPagoOld != null && !idMetodoPagoOld.equals(idMetodoPagoNew)) {
                idMetodoPagoOld.getFacturasList().remove(facturas);
                idMetodoPagoOld = em.merge(idMetodoPagoOld);
            }
            if (idMetodoPagoNew != null && !idMetodoPagoNew.equals(idMetodoPagoOld)) {
                idMetodoPagoNew.getFacturasList().add(facturas);
                idMetodoPagoNew = em.merge(idMetodoPagoNew);
            }
            if (distribuidoresRutDistribuidorOld != null && !distribuidoresRutDistribuidorOld.equals(distribuidoresRutDistribuidorNew)) {
                distribuidoresRutDistribuidorOld.getFacturasList().remove(facturas);
                distribuidoresRutDistribuidorOld = em.merge(distribuidoresRutDistribuidorOld);
            }
            if (distribuidoresRutDistribuidorNew != null && !distribuidoresRutDistribuidorNew.equals(distribuidoresRutDistribuidorOld)) {
                distribuidoresRutDistribuidorNew.getFacturasList().add(facturas);
                distribuidoresRutDistribuidorNew = em.merge(distribuidoresRutDistribuidorNew);
            }
            for (EjemplarLibros ejemplarLibrosListOldEjemplarLibros : ejemplarLibrosListOld) {
                if (!ejemplarLibrosListNew.contains(ejemplarLibrosListOldEjemplarLibros)) {
                    ejemplarLibrosListOldEjemplarLibros.getFacturasList().remove(facturas);
                    ejemplarLibrosListOldEjemplarLibros = em.merge(ejemplarLibrosListOldEjemplarLibros);
                }
            }
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibros : ejemplarLibrosListNew) {
                if (!ejemplarLibrosListOld.contains(ejemplarLibrosListNewEjemplarLibros)) {
                    ejemplarLibrosListNewEjemplarLibros.getFacturasList().add(facturas);
                    ejemplarLibrosListNewEjemplarLibros = em.merge(ejemplarLibrosListNewEjemplarLibros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturas.getFolioFactura();
                if (findFacturas(id) == null) {
                    throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas facturas;
            try {
                facturas = em.getReference(Facturas.class, id);
                facturas.getFolioFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.", enfe);
            }
            MetodoPago idMetodoPago = facturas.getIdMetodoPago();
            if (idMetodoPago != null) {
                idMetodoPago.getFacturasList().remove(facturas);
                idMetodoPago = em.merge(idMetodoPago);
            }
            Distribuidores distribuidoresRutDistribuidor = facturas.getDistribuidoresRutDistribuidor();
            if (distribuidoresRutDistribuidor != null) {
                distribuidoresRutDistribuidor.getFacturasList().remove(facturas);
                distribuidoresRutDistribuidor = em.merge(distribuidoresRutDistribuidor);
            }
            List<EjemplarLibros> ejemplarLibrosList = facturas.getEjemplarLibrosList();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : ejemplarLibrosList) {
                ejemplarLibrosListEjemplarLibros.getFacturasList().remove(facturas);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.remove(facturas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturas> findFacturasEntities() {
        return findFacturasEntities(true, -1, -1);
    }

    public List<Facturas> findFacturasEntities(int maxResults, int firstResult) {
        return findFacturasEntities(false, maxResults, firstResult);
    }

    private List<Facturas> findFacturasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturas.class));
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

    public Facturas findFacturas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturas.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturas> rt = cq.from(Facturas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
