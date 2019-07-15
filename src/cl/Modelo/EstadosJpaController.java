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
import cl.Entity.EjemplarLibros;
import cl.Entity.Estados;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class EstadosJpaController implements Serializable {

    public EstadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EstadosJpaController() {
    }

    public void create(Estados estados) {
        if (estados.getEjemplarLibrosList() == null) {
            estados.setEjemplarLibrosList(new ArrayList<EjemplarLibros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EjemplarLibros> attachedEjemplarLibrosList = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibrosToAttach : estados.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListEjemplarLibrosToAttach.getClass(), ejemplarLibrosListEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosList.add(ejemplarLibrosListEjemplarLibrosToAttach);
            }
            estados.setEjemplarLibrosList(attachedEjemplarLibrosList);
            em.persist(estados);
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : estados.getEjemplarLibrosList()) {
                Estados oldEstadosOfEjemplarLibrosListEjemplarLibros = ejemplarLibrosListEjemplarLibros.getEstados();
                ejemplarLibrosListEjemplarLibros.setEstados(estados);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
                if (oldEstadosOfEjemplarLibrosListEjemplarLibros != null) {
                    oldEstadosOfEjemplarLibrosListEjemplarLibros.getEjemplarLibrosList().remove(ejemplarLibrosListEjemplarLibros);
                    oldEstadosOfEjemplarLibrosListEjemplarLibros = em.merge(oldEstadosOfEjemplarLibrosListEjemplarLibros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estados estados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados persistentEstados = em.find(Estados.class, estados.getIdEstado());
            List<EjemplarLibros> ejemplarLibrosListOld = persistentEstados.getEjemplarLibrosList();
            List<EjemplarLibros> ejemplarLibrosListNew = estados.getEjemplarLibrosList();
            List<String> illegalOrphanMessages = null;
            for (EjemplarLibros ejemplarLibrosListOldEjemplarLibros : ejemplarLibrosListOld) {
                if (!ejemplarLibrosListNew.contains(ejemplarLibrosListOldEjemplarLibros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EjemplarLibros " + ejemplarLibrosListOldEjemplarLibros + " since its estados field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EjemplarLibros> attachedEjemplarLibrosListNew = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibrosToAttach : ejemplarLibrosListNew) {
                ejemplarLibrosListNewEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListNewEjemplarLibrosToAttach.getClass(), ejemplarLibrosListNewEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosListNew.add(ejemplarLibrosListNewEjemplarLibrosToAttach);
            }
            ejemplarLibrosListNew = attachedEjemplarLibrosListNew;
            estados.setEjemplarLibrosList(ejemplarLibrosListNew);
            estados = em.merge(estados);
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibros : ejemplarLibrosListNew) {
                if (!ejemplarLibrosListOld.contains(ejemplarLibrosListNewEjemplarLibros)) {
                    Estados oldEstadosOfEjemplarLibrosListNewEjemplarLibros = ejemplarLibrosListNewEjemplarLibros.getEstados();
                    ejemplarLibrosListNewEjemplarLibros.setEstados(estados);
                    ejemplarLibrosListNewEjemplarLibros = em.merge(ejemplarLibrosListNewEjemplarLibros);
                    if (oldEstadosOfEjemplarLibrosListNewEjemplarLibros != null && !oldEstadosOfEjemplarLibrosListNewEjemplarLibros.equals(estados)) {
                        oldEstadosOfEjemplarLibrosListNewEjemplarLibros.getEjemplarLibrosList().remove(ejemplarLibrosListNewEjemplarLibros);
                        oldEstadosOfEjemplarLibrosListNewEjemplarLibros = em.merge(oldEstadosOfEjemplarLibrosListNewEjemplarLibros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estados.getIdEstado();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EjemplarLibros> ejemplarLibrosListOrphanCheck = estados.getEjemplarLibrosList();
            for (EjemplarLibros ejemplarLibrosListOrphanCheckEjemplarLibros : ejemplarLibrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the EjemplarLibros " + ejemplarLibrosListOrphanCheckEjemplarLibros + " in its ejemplarLibrosList field has a non-nullable estados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
