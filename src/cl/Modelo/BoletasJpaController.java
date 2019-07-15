/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Entity.Boletas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Clientes;
import cl.Entity.MetodoPago;
import cl.Entity.Trabajadores;
import cl.Entity.EjemplarLibros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class BoletasJpaController implements Serializable {

    public BoletasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BoletasJpaController() {
    }

    public void create(Boletas boletas) {
        if (boletas.getEjemplarLibrosList() == null) {
            boletas.setEjemplarLibrosList(new ArrayList<EjemplarLibros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientesRutCliente = boletas.getClientesRutCliente();
            if (clientesRutCliente != null) {
                clientesRutCliente = em.getReference(clientesRutCliente.getClass(), clientesRutCliente.getRutCliente());
                boletas.setClientesRutCliente(clientesRutCliente);
            }
            MetodoPago metodoPagoIdMetodoPago = boletas.getMetodoPagoIdMetodoPago();
            if (metodoPagoIdMetodoPago != null) {
                metodoPagoIdMetodoPago = em.getReference(metodoPagoIdMetodoPago.getClass(), metodoPagoIdMetodoPago.getIdMetodoPago());
                boletas.setMetodoPagoIdMetodoPago(metodoPagoIdMetodoPago);
            }
            Trabajadores trabajadoresRutTrabajador = boletas.getTrabajadoresRutTrabajador();
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador = em.getReference(trabajadoresRutTrabajador.getClass(), trabajadoresRutTrabajador.getRutTrabajador());
                boletas.setTrabajadoresRutTrabajador(trabajadoresRutTrabajador);
            }
            List<EjemplarLibros> attachedEjemplarLibrosList = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibrosToAttach : boletas.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListEjemplarLibrosToAttach.getClass(), ejemplarLibrosListEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosList.add(ejemplarLibrosListEjemplarLibrosToAttach);
            }
            boletas.setEjemplarLibrosList(attachedEjemplarLibrosList);
            em.persist(boletas);
            if (clientesRutCliente != null) {
                clientesRutCliente.getBoletasList().add(boletas);
                clientesRutCliente = em.merge(clientesRutCliente);
            }
            if (metodoPagoIdMetodoPago != null) {
                metodoPagoIdMetodoPago.getBoletasList().add(boletas);
                metodoPagoIdMetodoPago = em.merge(metodoPagoIdMetodoPago);
            }
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador.getBoletasList().add(boletas);
                trabajadoresRutTrabajador = em.merge(trabajadoresRutTrabajador);
            }
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : boletas.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibros.getBoletasList().add(boletas);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boletas boletas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boletas persistentBoletas = em.find(Boletas.class, boletas.getFolioBoleta());
            Clientes clientesRutClienteOld = persistentBoletas.getClientesRutCliente();
            Clientes clientesRutClienteNew = boletas.getClientesRutCliente();
            MetodoPago metodoPagoIdMetodoPagoOld = persistentBoletas.getMetodoPagoIdMetodoPago();
            MetodoPago metodoPagoIdMetodoPagoNew = boletas.getMetodoPagoIdMetodoPago();
            Trabajadores trabajadoresRutTrabajadorOld = persistentBoletas.getTrabajadoresRutTrabajador();
            Trabajadores trabajadoresRutTrabajadorNew = boletas.getTrabajadoresRutTrabajador();
            List<EjemplarLibros> ejemplarLibrosListOld = persistentBoletas.getEjemplarLibrosList();
            List<EjemplarLibros> ejemplarLibrosListNew = boletas.getEjemplarLibrosList();
            if (clientesRutClienteNew != null) {
                clientesRutClienteNew = em.getReference(clientesRutClienteNew.getClass(), clientesRutClienteNew.getRutCliente());
                boletas.setClientesRutCliente(clientesRutClienteNew);
            }
            if (metodoPagoIdMetodoPagoNew != null) {
                metodoPagoIdMetodoPagoNew = em.getReference(metodoPagoIdMetodoPagoNew.getClass(), metodoPagoIdMetodoPagoNew.getIdMetodoPago());
                boletas.setMetodoPagoIdMetodoPago(metodoPagoIdMetodoPagoNew);
            }
            if (trabajadoresRutTrabajadorNew != null) {
                trabajadoresRutTrabajadorNew = em.getReference(trabajadoresRutTrabajadorNew.getClass(), trabajadoresRutTrabajadorNew.getRutTrabajador());
                boletas.setTrabajadoresRutTrabajador(trabajadoresRutTrabajadorNew);
            }
            List<EjemplarLibros> attachedEjemplarLibrosListNew = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibrosToAttach : ejemplarLibrosListNew) {
                ejemplarLibrosListNewEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListNewEjemplarLibrosToAttach.getClass(), ejemplarLibrosListNewEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosListNew.add(ejemplarLibrosListNewEjemplarLibrosToAttach);
            }
            ejemplarLibrosListNew = attachedEjemplarLibrosListNew;
            boletas.setEjemplarLibrosList(ejemplarLibrosListNew);
            boletas = em.merge(boletas);
            if (clientesRutClienteOld != null && !clientesRutClienteOld.equals(clientesRutClienteNew)) {
                clientesRutClienteOld.getBoletasList().remove(boletas);
                clientesRutClienteOld = em.merge(clientesRutClienteOld);
            }
            if (clientesRutClienteNew != null && !clientesRutClienteNew.equals(clientesRutClienteOld)) {
                clientesRutClienteNew.getBoletasList().add(boletas);
                clientesRutClienteNew = em.merge(clientesRutClienteNew);
            }
            if (metodoPagoIdMetodoPagoOld != null && !metodoPagoIdMetodoPagoOld.equals(metodoPagoIdMetodoPagoNew)) {
                metodoPagoIdMetodoPagoOld.getBoletasList().remove(boletas);
                metodoPagoIdMetodoPagoOld = em.merge(metodoPagoIdMetodoPagoOld);
            }
            if (metodoPagoIdMetodoPagoNew != null && !metodoPagoIdMetodoPagoNew.equals(metodoPagoIdMetodoPagoOld)) {
                metodoPagoIdMetodoPagoNew.getBoletasList().add(boletas);
                metodoPagoIdMetodoPagoNew = em.merge(metodoPagoIdMetodoPagoNew);
            }
            if (trabajadoresRutTrabajadorOld != null && !trabajadoresRutTrabajadorOld.equals(trabajadoresRutTrabajadorNew)) {
                trabajadoresRutTrabajadorOld.getBoletasList().remove(boletas);
                trabajadoresRutTrabajadorOld = em.merge(trabajadoresRutTrabajadorOld);
            }
            if (trabajadoresRutTrabajadorNew != null && !trabajadoresRutTrabajadorNew.equals(trabajadoresRutTrabajadorOld)) {
                trabajadoresRutTrabajadorNew.getBoletasList().add(boletas);
                trabajadoresRutTrabajadorNew = em.merge(trabajadoresRutTrabajadorNew);
            }
            for (EjemplarLibros ejemplarLibrosListOldEjemplarLibros : ejemplarLibrosListOld) {
                if (!ejemplarLibrosListNew.contains(ejemplarLibrosListOldEjemplarLibros)) {
                    ejemplarLibrosListOldEjemplarLibros.getBoletasList().remove(boletas);
                    ejemplarLibrosListOldEjemplarLibros = em.merge(ejemplarLibrosListOldEjemplarLibros);
                }
            }
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibros : ejemplarLibrosListNew) {
                if (!ejemplarLibrosListOld.contains(ejemplarLibrosListNewEjemplarLibros)) {
                    ejemplarLibrosListNewEjemplarLibros.getBoletasList().add(boletas);
                    ejemplarLibrosListNewEjemplarLibros = em.merge(ejemplarLibrosListNewEjemplarLibros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boletas.getFolioBoleta();
                if (findBoletas(id) == null) {
                    throw new NonexistentEntityException("The boletas with id " + id + " no longer exists.");
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
            Boletas boletas;
            try {
                boletas = em.getReference(Boletas.class, id);
                boletas.getFolioBoleta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boletas with id " + id + " no longer exists.", enfe);
            }
            Clientes clientesRutCliente = boletas.getClientesRutCliente();
            if (clientesRutCliente != null) {
                clientesRutCliente.getBoletasList().remove(boletas);
                clientesRutCliente = em.merge(clientesRutCliente);
            }
            MetodoPago metodoPagoIdMetodoPago = boletas.getMetodoPagoIdMetodoPago();
            if (metodoPagoIdMetodoPago != null) {
                metodoPagoIdMetodoPago.getBoletasList().remove(boletas);
                metodoPagoIdMetodoPago = em.merge(metodoPagoIdMetodoPago);
            }
            Trabajadores trabajadoresRutTrabajador = boletas.getTrabajadoresRutTrabajador();
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador.getBoletasList().remove(boletas);
                trabajadoresRutTrabajador = em.merge(trabajadoresRutTrabajador);
            }
            List<EjemplarLibros> ejemplarLibrosList = boletas.getEjemplarLibrosList();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : ejemplarLibrosList) {
                ejemplarLibrosListEjemplarLibros.getBoletasList().remove(boletas);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.remove(boletas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boletas> findBoletasEntities() {
        return findBoletasEntities(true, -1, -1);
    }

    public List<Boletas> findBoletasEntities(int maxResults, int firstResult) {
        return findBoletasEntities(false, maxResults, firstResult);
    }

    private List<Boletas> findBoletasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boletas.class));
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

    public Boletas findBoletas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boletas.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boletas> rt = cq.from(Boletas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
