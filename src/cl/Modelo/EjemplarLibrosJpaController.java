/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Estados;
import cl.Entity.Libros;
import cl.Entity.Arriendos;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Boletas;
import cl.Entity.EjemplarLibros;
import cl.Entity.EjemplarLibrosPK;
import cl.Entity.Facturas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class EjemplarLibrosJpaController implements Serializable {

    public EjemplarLibrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EjemplarLibrosJpaController() {
    }

    public void create(EjemplarLibros ejemplarLibros) throws PreexistingEntityException, Exception {
        if (ejemplarLibros.getEjemplarLibrosPK() == null) {
            ejemplarLibros.setEjemplarLibrosPK(new EjemplarLibrosPK());
        }
        if (ejemplarLibros.getArriendosList() == null) {
            ejemplarLibros.setArriendosList(new ArrayList<Arriendos>());
        }
        if (ejemplarLibros.getBoletasList() == null) {
            ejemplarLibros.setBoletasList(new ArrayList<Boletas>());
        }
        if (ejemplarLibros.getFacturasList() == null) {
            ejemplarLibros.setFacturasList(new ArrayList<Facturas>());
        }
        ejemplarLibros.getEjemplarLibrosPK().setEstadosIdEstado(ejemplarLibros.getEstados().getIdEstado());
        ejemplarLibros.getEjemplarLibrosPK().setLibrosIdLibro(ejemplarLibros.getLibros().getIdLibro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estados = ejemplarLibros.getEstados();
            if (estados != null) {
                estados = em.getReference(estados.getClass(), estados.getIdEstado());
                ejemplarLibros.setEstados(estados);
            }
            Libros libros = ejemplarLibros.getLibros();
            if (libros != null) {
                libros = em.getReference(libros.getClass(), libros.getIdLibro());
                ejemplarLibros.setLibros(libros);
            }
            List<Arriendos> attachedArriendosList = new ArrayList<Arriendos>();
            for (Arriendos arriendosListArriendosToAttach : ejemplarLibros.getArriendosList()) {
                arriendosListArriendosToAttach = em.getReference(arriendosListArriendosToAttach.getClass(), arriendosListArriendosToAttach.getIdArriendo());
                attachedArriendosList.add(arriendosListArriendosToAttach);
            }
            ejemplarLibros.setArriendosList(attachedArriendosList);
            List<Boletas> attachedBoletasList = new ArrayList<Boletas>();
            for (Boletas boletasListBoletasToAttach : ejemplarLibros.getBoletasList()) {
                boletasListBoletasToAttach = em.getReference(boletasListBoletasToAttach.getClass(), boletasListBoletasToAttach.getFolioBoleta());
                attachedBoletasList.add(boletasListBoletasToAttach);
            }
            ejemplarLibros.setBoletasList(attachedBoletasList);
            List<Facturas> attachedFacturasList = new ArrayList<Facturas>();
            for (Facturas facturasListFacturasToAttach : ejemplarLibros.getFacturasList()) {
                facturasListFacturasToAttach = em.getReference(facturasListFacturasToAttach.getClass(), facturasListFacturasToAttach.getFolioFactura());
                attachedFacturasList.add(facturasListFacturasToAttach);
            }
            ejemplarLibros.setFacturasList(attachedFacturasList);
            em.persist(ejemplarLibros);
            if (estados != null) {
                estados.getEjemplarLibrosList().add(ejemplarLibros);
                estados = em.merge(estados);
            }
            if (libros != null) {
                libros.getEjemplarLibrosList().add(ejemplarLibros);
                libros = em.merge(libros);
            }
            for (Arriendos arriendosListArriendos : ejemplarLibros.getArriendosList()) {
                arriendosListArriendos.getEjemplarLibrosList().add(ejemplarLibros);
                arriendosListArriendos = em.merge(arriendosListArriendos);
            }
            for (Boletas boletasListBoletas : ejemplarLibros.getBoletasList()) {
                boletasListBoletas.getEjemplarLibrosList().add(ejemplarLibros);
                boletasListBoletas = em.merge(boletasListBoletas);
            }
            for (Facturas facturasListFacturas : ejemplarLibros.getFacturasList()) {
                facturasListFacturas.getEjemplarLibrosList().add(ejemplarLibros);
                facturasListFacturas = em.merge(facturasListFacturas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEjemplarLibros(ejemplarLibros.getEjemplarLibrosPK()) != null) {
                throw new PreexistingEntityException("EjemplarLibros " + ejemplarLibros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EjemplarLibros ejemplarLibros) throws NonexistentEntityException, Exception {
        ejemplarLibros.getEjemplarLibrosPK().setEstadosIdEstado(ejemplarLibros.getEstados().getIdEstado());
        ejemplarLibros.getEjemplarLibrosPK().setLibrosIdLibro(ejemplarLibros.getLibros().getIdLibro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EjemplarLibros persistentEjemplarLibros = em.find(EjemplarLibros.class, ejemplarLibros.getEjemplarLibrosPK());
            Estados estadosOld = persistentEjemplarLibros.getEstados();
            Estados estadosNew = ejemplarLibros.getEstados();
            Libros librosOld = persistentEjemplarLibros.getLibros();
            Libros librosNew = ejemplarLibros.getLibros();
            List<Arriendos> arriendosListOld = persistentEjemplarLibros.getArriendosList();
            List<Arriendos> arriendosListNew = ejemplarLibros.getArriendosList();
            List<Boletas> boletasListOld = persistentEjemplarLibros.getBoletasList();
            List<Boletas> boletasListNew = ejemplarLibros.getBoletasList();
            List<Facturas> facturasListOld = persistentEjemplarLibros.getFacturasList();
            List<Facturas> facturasListNew = ejemplarLibros.getFacturasList();
            if (estadosNew != null) {
                estadosNew = em.getReference(estadosNew.getClass(), estadosNew.getIdEstado());
                ejemplarLibros.setEstados(estadosNew);
            }
            if (librosNew != null) {
                librosNew = em.getReference(librosNew.getClass(), librosNew.getIdLibro());
                ejemplarLibros.setLibros(librosNew);
            }
            List<Arriendos> attachedArriendosListNew = new ArrayList<Arriendos>();
            for (Arriendos arriendosListNewArriendosToAttach : arriendosListNew) {
                arriendosListNewArriendosToAttach = em.getReference(arriendosListNewArriendosToAttach.getClass(), arriendosListNewArriendosToAttach.getIdArriendo());
                attachedArriendosListNew.add(arriendosListNewArriendosToAttach);
            }
            arriendosListNew = attachedArriendosListNew;
            ejemplarLibros.setArriendosList(arriendosListNew);
            List<Boletas> attachedBoletasListNew = new ArrayList<Boletas>();
            for (Boletas boletasListNewBoletasToAttach : boletasListNew) {
                boletasListNewBoletasToAttach = em.getReference(boletasListNewBoletasToAttach.getClass(), boletasListNewBoletasToAttach.getFolioBoleta());
                attachedBoletasListNew.add(boletasListNewBoletasToAttach);
            }
            boletasListNew = attachedBoletasListNew;
            ejemplarLibros.setBoletasList(boletasListNew);
            List<Facturas> attachedFacturasListNew = new ArrayList<Facturas>();
            for (Facturas facturasListNewFacturasToAttach : facturasListNew) {
                facturasListNewFacturasToAttach = em.getReference(facturasListNewFacturasToAttach.getClass(), facturasListNewFacturasToAttach.getFolioFactura());
                attachedFacturasListNew.add(facturasListNewFacturasToAttach);
            }
            facturasListNew = attachedFacturasListNew;
            ejemplarLibros.setFacturasList(facturasListNew);
            ejemplarLibros = em.merge(ejemplarLibros);
            if (estadosOld != null && !estadosOld.equals(estadosNew)) {
                estadosOld.getEjemplarLibrosList().remove(ejemplarLibros);
                estadosOld = em.merge(estadosOld);
            }
            if (estadosNew != null && !estadosNew.equals(estadosOld)) {
                estadosNew.getEjemplarLibrosList().add(ejemplarLibros);
                estadosNew = em.merge(estadosNew);
            }
            if (librosOld != null && !librosOld.equals(librosNew)) {
                librosOld.getEjemplarLibrosList().remove(ejemplarLibros);
                librosOld = em.merge(librosOld);
            }
            if (librosNew != null && !librosNew.equals(librosOld)) {
                librosNew.getEjemplarLibrosList().add(ejemplarLibros);
                librosNew = em.merge(librosNew);
            }
            for (Arriendos arriendosListOldArriendos : arriendosListOld) {
                if (!arriendosListNew.contains(arriendosListOldArriendos)) {
                    arriendosListOldArriendos.getEjemplarLibrosList().remove(ejemplarLibros);
                    arriendosListOldArriendos = em.merge(arriendosListOldArriendos);
                }
            }
            for (Arriendos arriendosListNewArriendos : arriendosListNew) {
                if (!arriendosListOld.contains(arriendosListNewArriendos)) {
                    arriendosListNewArriendos.getEjemplarLibrosList().add(ejemplarLibros);
                    arriendosListNewArriendos = em.merge(arriendosListNewArriendos);
                }
            }
            for (Boletas boletasListOldBoletas : boletasListOld) {
                if (!boletasListNew.contains(boletasListOldBoletas)) {
                    boletasListOldBoletas.getEjemplarLibrosList().remove(ejemplarLibros);
                    boletasListOldBoletas = em.merge(boletasListOldBoletas);
                }
            }
            for (Boletas boletasListNewBoletas : boletasListNew) {
                if (!boletasListOld.contains(boletasListNewBoletas)) {
                    boletasListNewBoletas.getEjemplarLibrosList().add(ejemplarLibros);
                    boletasListNewBoletas = em.merge(boletasListNewBoletas);
                }
            }
            for (Facturas facturasListOldFacturas : facturasListOld) {
                if (!facturasListNew.contains(facturasListOldFacturas)) {
                    facturasListOldFacturas.getEjemplarLibrosList().remove(ejemplarLibros);
                    facturasListOldFacturas = em.merge(facturasListOldFacturas);
                }
            }
            for (Facturas facturasListNewFacturas : facturasListNew) {
                if (!facturasListOld.contains(facturasListNewFacturas)) {
                    facturasListNewFacturas.getEjemplarLibrosList().add(ejemplarLibros);
                    facturasListNewFacturas = em.merge(facturasListNewFacturas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EjemplarLibrosPK id = ejemplarLibros.getEjemplarLibrosPK();
                if (findEjemplarLibros(id) == null) {
                    throw new NonexistentEntityException("The ejemplarLibros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EjemplarLibrosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EjemplarLibros ejemplarLibros;
            try {
                ejemplarLibros = em.getReference(EjemplarLibros.class, id);
                ejemplarLibros.getEjemplarLibrosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ejemplarLibros with id " + id + " no longer exists.", enfe);
            }
            Estados estados = ejemplarLibros.getEstados();
            if (estados != null) {
                estados.getEjemplarLibrosList().remove(ejemplarLibros);
                estados = em.merge(estados);
            }
            Libros libros = ejemplarLibros.getLibros();
            if (libros != null) {
                libros.getEjemplarLibrosList().remove(ejemplarLibros);
                libros = em.merge(libros);
            }
            List<Arriendos> arriendosList = ejemplarLibros.getArriendosList();
            for (Arriendos arriendosListArriendos : arriendosList) {
                arriendosListArriendos.getEjemplarLibrosList().remove(ejemplarLibros);
                arriendosListArriendos = em.merge(arriendosListArriendos);
            }
            List<Boletas> boletasList = ejemplarLibros.getBoletasList();
            for (Boletas boletasListBoletas : boletasList) {
                boletasListBoletas.getEjemplarLibrosList().remove(ejemplarLibros);
                boletasListBoletas = em.merge(boletasListBoletas);
            }
            List<Facturas> facturasList = ejemplarLibros.getFacturasList();
            for (Facturas facturasListFacturas : facturasList) {
                facturasListFacturas.getEjemplarLibrosList().remove(ejemplarLibros);
                facturasListFacturas = em.merge(facturasListFacturas);
            }
            em.remove(ejemplarLibros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EjemplarLibros> findEjemplarLibrosEntities() {
        return findEjemplarLibrosEntities(true, -1, -1);
    }

    public List<EjemplarLibros> findEjemplarLibrosEntities(int maxResults, int firstResult) {
        return findEjemplarLibrosEntities(false, maxResults, firstResult);
    }

    private List<EjemplarLibros> findEjemplarLibrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EjemplarLibros.class));
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

    public EjemplarLibros findEjemplarLibros(EjemplarLibrosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EjemplarLibros.class, id);
        } finally {
            em.close();
        }
    }

    public int getEjemplarLibrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EjemplarLibros> rt = cq.from(EjemplarLibros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
