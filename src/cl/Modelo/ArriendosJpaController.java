/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Entity.Arriendos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Clientes;
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
public class ArriendosJpaController implements Serializable {

    public ArriendosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ArriendosJpaController() {
    }

    public void create(Arriendos arriendos) {
        if (arriendos.getEjemplarLibrosList() == null) {
            arriendos.setEjemplarLibrosList(new ArrayList<EjemplarLibros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientesRutCliente = arriendos.getClientesRutCliente();
            if (clientesRutCliente != null) {
                clientesRutCliente = em.getReference(clientesRutCliente.getClass(), clientesRutCliente.getRutCliente());
                arriendos.setClientesRutCliente(clientesRutCliente);
            }
            Trabajadores trabajadoresRutTrabajador = arriendos.getTrabajadoresRutTrabajador();
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador = em.getReference(trabajadoresRutTrabajador.getClass(), trabajadoresRutTrabajador.getRutTrabajador());
                arriendos.setTrabajadoresRutTrabajador(trabajadoresRutTrabajador);
            }
            List<EjemplarLibros> attachedEjemplarLibrosList = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibrosToAttach : arriendos.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListEjemplarLibrosToAttach.getClass(), ejemplarLibrosListEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosList.add(ejemplarLibrosListEjemplarLibrosToAttach);
            }
            arriendos.setEjemplarLibrosList(attachedEjemplarLibrosList);
            em.persist(arriendos);
            if (clientesRutCliente != null) {
                clientesRutCliente.getArriendosList().add(arriendos);
                clientesRutCliente = em.merge(clientesRutCliente);
            }
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador.getArriendosList().add(arriendos);
                trabajadoresRutTrabajador = em.merge(trabajadoresRutTrabajador);
            }
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : arriendos.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibros.getArriendosList().add(arriendos);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Arriendos arriendos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Arriendos persistentArriendos = em.find(Arriendos.class, arriendos.getIdArriendo());
            Clientes clientesRutClienteOld = persistentArriendos.getClientesRutCliente();
            Clientes clientesRutClienteNew = arriendos.getClientesRutCliente();
            Trabajadores trabajadoresRutTrabajadorOld = persistentArriendos.getTrabajadoresRutTrabajador();
            Trabajadores trabajadoresRutTrabajadorNew = arriendos.getTrabajadoresRutTrabajador();
            List<EjemplarLibros> ejemplarLibrosListOld = persistentArriendos.getEjemplarLibrosList();
            List<EjemplarLibros> ejemplarLibrosListNew = arriendos.getEjemplarLibrosList();
            if (clientesRutClienteNew != null) {
                clientesRutClienteNew = em.getReference(clientesRutClienteNew.getClass(), clientesRutClienteNew.getRutCliente());
                arriendos.setClientesRutCliente(clientesRutClienteNew);
            }
            if (trabajadoresRutTrabajadorNew != null) {
                trabajadoresRutTrabajadorNew = em.getReference(trabajadoresRutTrabajadorNew.getClass(), trabajadoresRutTrabajadorNew.getRutTrabajador());
                arriendos.setTrabajadoresRutTrabajador(trabajadoresRutTrabajadorNew);
            }
            List<EjemplarLibros> attachedEjemplarLibrosListNew = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibrosToAttach : ejemplarLibrosListNew) {
                ejemplarLibrosListNewEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListNewEjemplarLibrosToAttach.getClass(), ejemplarLibrosListNewEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosListNew.add(ejemplarLibrosListNewEjemplarLibrosToAttach);
            }
            ejemplarLibrosListNew = attachedEjemplarLibrosListNew;
            arriendos.setEjemplarLibrosList(ejemplarLibrosListNew);
            arriendos = em.merge(arriendos);
            if (clientesRutClienteOld != null && !clientesRutClienteOld.equals(clientesRutClienteNew)) {
                clientesRutClienteOld.getArriendosList().remove(arriendos);
                clientesRutClienteOld = em.merge(clientesRutClienteOld);
            }
            if (clientesRutClienteNew != null && !clientesRutClienteNew.equals(clientesRutClienteOld)) {
                clientesRutClienteNew.getArriendosList().add(arriendos);
                clientesRutClienteNew = em.merge(clientesRutClienteNew);
            }
            if (trabajadoresRutTrabajadorOld != null && !trabajadoresRutTrabajadorOld.equals(trabajadoresRutTrabajadorNew)) {
                trabajadoresRutTrabajadorOld.getArriendosList().remove(arriendos);
                trabajadoresRutTrabajadorOld = em.merge(trabajadoresRutTrabajadorOld);
            }
            if (trabajadoresRutTrabajadorNew != null && !trabajadoresRutTrabajadorNew.equals(trabajadoresRutTrabajadorOld)) {
                trabajadoresRutTrabajadorNew.getArriendosList().add(arriendos);
                trabajadoresRutTrabajadorNew = em.merge(trabajadoresRutTrabajadorNew);
            }
            for (EjemplarLibros ejemplarLibrosListOldEjemplarLibros : ejemplarLibrosListOld) {
                if (!ejemplarLibrosListNew.contains(ejemplarLibrosListOldEjemplarLibros)) {
                    ejemplarLibrosListOldEjemplarLibros.getArriendosList().remove(arriendos);
                    ejemplarLibrosListOldEjemplarLibros = em.merge(ejemplarLibrosListOldEjemplarLibros);
                }
            }
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibros : ejemplarLibrosListNew) {
                if (!ejemplarLibrosListOld.contains(ejemplarLibrosListNewEjemplarLibros)) {
                    ejemplarLibrosListNewEjemplarLibros.getArriendosList().add(arriendos);
                    ejemplarLibrosListNewEjemplarLibros = em.merge(ejemplarLibrosListNewEjemplarLibros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = arriendos.getIdArriendo();
                if (findArriendos(id) == null) {
                    throw new NonexistentEntityException("The arriendos with id " + id + " no longer exists.");
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
            Arriendos arriendos;
            try {
                arriendos = em.getReference(Arriendos.class, id);
                arriendos.getIdArriendo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arriendos with id " + id + " no longer exists.", enfe);
            }
            Clientes clientesRutCliente = arriendos.getClientesRutCliente();
            if (clientesRutCliente != null) {
                clientesRutCliente.getArriendosList().remove(arriendos);
                clientesRutCliente = em.merge(clientesRutCliente);
            }
            Trabajadores trabajadoresRutTrabajador = arriendos.getTrabajadoresRutTrabajador();
            if (trabajadoresRutTrabajador != null) {
                trabajadoresRutTrabajador.getArriendosList().remove(arriendos);
                trabajadoresRutTrabajador = em.merge(trabajadoresRutTrabajador);
            }
            List<EjemplarLibros> ejemplarLibrosList = arriendos.getEjemplarLibrosList();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : ejemplarLibrosList) {
                ejemplarLibrosListEjemplarLibros.getArriendosList().remove(arriendos);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
            }
            em.remove(arriendos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Arriendos> findArriendosEntities() {
        return findArriendosEntities(true, -1, -1);
    }

    public List<Arriendos> findArriendosEntities(int maxResults, int firstResult) {
        return findArriendosEntities(false, maxResults, firstResult);
    }

    private List<Arriendos> findArriendosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arriendos.class));
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

    public Arriendos findArriendos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arriendos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArriendosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arriendos> rt = cq.from(Arriendos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
