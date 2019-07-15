/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.IllegalOrphanException;
import cl.Modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Facturas;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Boletas;
import cl.Entity.MetodoPago;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class MetodoPagoJpaController implements Serializable {

    public MetodoPagoJpaController() {
    }

    public MetodoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MetodoPago metodoPago) {
        if (metodoPago.getFacturasList() == null) {
            metodoPago.setFacturasList(new ArrayList<Facturas>());
        }
        if (metodoPago.getBoletasList() == null) {
            metodoPago.setBoletasList(new ArrayList<Boletas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Facturas> attachedFacturasList = new ArrayList<Facturas>();
            for (Facturas facturasListFacturasToAttach : metodoPago.getFacturasList()) {
                facturasListFacturasToAttach = em.getReference(facturasListFacturasToAttach.getClass(), facturasListFacturasToAttach.getFolioFactura());
                attachedFacturasList.add(facturasListFacturasToAttach);
            }
            metodoPago.setFacturasList(attachedFacturasList);
            List<Boletas> attachedBoletasList = new ArrayList<Boletas>();
            for (Boletas boletasListBoletasToAttach : metodoPago.getBoletasList()) {
                boletasListBoletasToAttach = em.getReference(boletasListBoletasToAttach.getClass(), boletasListBoletasToAttach.getFolioBoleta());
                attachedBoletasList.add(boletasListBoletasToAttach);
            }
            metodoPago.setBoletasList(attachedBoletasList);
            em.persist(metodoPago);
            for (Facturas facturasListFacturas : metodoPago.getFacturasList()) {
                MetodoPago oldIdMetodoPagoOfFacturasListFacturas = facturasListFacturas.getIdMetodoPago();
                facturasListFacturas.setIdMetodoPago(metodoPago);
                facturasListFacturas = em.merge(facturasListFacturas);
                if (oldIdMetodoPagoOfFacturasListFacturas != null) {
                    oldIdMetodoPagoOfFacturasListFacturas.getFacturasList().remove(facturasListFacturas);
                    oldIdMetodoPagoOfFacturasListFacturas = em.merge(oldIdMetodoPagoOfFacturasListFacturas);
                }
            }
            for (Boletas boletasListBoletas : metodoPago.getBoletasList()) {
                MetodoPago oldMetodoPagoIdMetodoPagoOfBoletasListBoletas = boletasListBoletas.getMetodoPagoIdMetodoPago();
                boletasListBoletas.setMetodoPagoIdMetodoPago(metodoPago);
                boletasListBoletas = em.merge(boletasListBoletas);
                if (oldMetodoPagoIdMetodoPagoOfBoletasListBoletas != null) {
                    oldMetodoPagoIdMetodoPagoOfBoletasListBoletas.getBoletasList().remove(boletasListBoletas);
                    oldMetodoPagoIdMetodoPagoOfBoletasListBoletas = em.merge(oldMetodoPagoIdMetodoPagoOfBoletasListBoletas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MetodoPago metodoPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MetodoPago persistentMetodoPago = em.find(MetodoPago.class, metodoPago.getIdMetodoPago());
            List<Facturas> facturasListOld = persistentMetodoPago.getFacturasList();
            List<Facturas> facturasListNew = metodoPago.getFacturasList();
            List<Boletas> boletasListOld = persistentMetodoPago.getBoletasList();
            List<Boletas> boletasListNew = metodoPago.getBoletasList();
            List<String> illegalOrphanMessages = null;
            for (Facturas facturasListOldFacturas : facturasListOld) {
                if (!facturasListNew.contains(facturasListOldFacturas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Facturas " + facturasListOldFacturas + " since its idMetodoPago field is not nullable.");
                }
            }
            for (Boletas boletasListOldBoletas : boletasListOld) {
                if (!boletasListNew.contains(boletasListOldBoletas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boletas " + boletasListOldBoletas + " since its metodoPagoIdMetodoPago field is not nullable.");
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
            metodoPago.setFacturasList(facturasListNew);
            List<Boletas> attachedBoletasListNew = new ArrayList<Boletas>();
            for (Boletas boletasListNewBoletasToAttach : boletasListNew) {
                boletasListNewBoletasToAttach = em.getReference(boletasListNewBoletasToAttach.getClass(), boletasListNewBoletasToAttach.getFolioBoleta());
                attachedBoletasListNew.add(boletasListNewBoletasToAttach);
            }
            boletasListNew = attachedBoletasListNew;
            metodoPago.setBoletasList(boletasListNew);
            metodoPago = em.merge(metodoPago);
            for (Facturas facturasListNewFacturas : facturasListNew) {
                if (!facturasListOld.contains(facturasListNewFacturas)) {
                    MetodoPago oldIdMetodoPagoOfFacturasListNewFacturas = facturasListNewFacturas.getIdMetodoPago();
                    facturasListNewFacturas.setIdMetodoPago(metodoPago);
                    facturasListNewFacturas = em.merge(facturasListNewFacturas);
                    if (oldIdMetodoPagoOfFacturasListNewFacturas != null && !oldIdMetodoPagoOfFacturasListNewFacturas.equals(metodoPago)) {
                        oldIdMetodoPagoOfFacturasListNewFacturas.getFacturasList().remove(facturasListNewFacturas);
                        oldIdMetodoPagoOfFacturasListNewFacturas = em.merge(oldIdMetodoPagoOfFacturasListNewFacturas);
                    }
                }
            }
            for (Boletas boletasListNewBoletas : boletasListNew) {
                if (!boletasListOld.contains(boletasListNewBoletas)) {
                    MetodoPago oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas = boletasListNewBoletas.getMetodoPagoIdMetodoPago();
                    boletasListNewBoletas.setMetodoPagoIdMetodoPago(metodoPago);
                    boletasListNewBoletas = em.merge(boletasListNewBoletas);
                    if (oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas != null && !oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas.equals(metodoPago)) {
                        oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas.getBoletasList().remove(boletasListNewBoletas);
                        oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas = em.merge(oldMetodoPagoIdMetodoPagoOfBoletasListNewBoletas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = metodoPago.getIdMetodoPago();
                if (findMetodoPago(id) == null) {
                    throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MetodoPago metodoPago;
            try {
                metodoPago = em.getReference(MetodoPago.class, id);
                metodoPago.getIdMetodoPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Facturas> facturasListOrphanCheck = metodoPago.getFacturasList();
            for (Facturas facturasListOrphanCheckFacturas : facturasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MetodoPago (" + metodoPago + ") cannot be destroyed since the Facturas " + facturasListOrphanCheckFacturas + " in its facturasList field has a non-nullable idMetodoPago field.");
            }
            List<Boletas> boletasListOrphanCheck = metodoPago.getBoletasList();
            for (Boletas boletasListOrphanCheckBoletas : boletasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MetodoPago (" + metodoPago + ") cannot be destroyed since the Boletas " + boletasListOrphanCheckBoletas + " in its boletasList field has a non-nullable metodoPagoIdMetodoPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(metodoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MetodoPago> findMetodoPagoEntities() {
        return findMetodoPagoEntities(true, -1, -1);
    }

    public List<MetodoPago> findMetodoPagoEntities(int maxResults, int firstResult) {
        return findMetodoPagoEntities(false, maxResults, firstResult);
    }

    private List<MetodoPago> findMetodoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MetodoPago.class));
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

    public MetodoPago findMetodoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MetodoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getMetodoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MetodoPago> rt = cq.from(MetodoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
