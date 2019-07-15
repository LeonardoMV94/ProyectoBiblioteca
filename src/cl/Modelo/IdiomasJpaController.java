/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Entity.Idiomas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Libros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class IdiomasJpaController implements Serializable {

    public IdiomasJpaController() {
    }

    public IdiomasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Idiomas idiomas) {
        if (idiomas.getLibrosList() == null) {
            idiomas.setLibrosList(new ArrayList<Libros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Libros> attachedLibrosList = new ArrayList<Libros>();
            for (Libros librosListLibrosToAttach : idiomas.getLibrosList()) {
                librosListLibrosToAttach = em.getReference(librosListLibrosToAttach.getClass(), librosListLibrosToAttach.getIdLibro());
                attachedLibrosList.add(librosListLibrosToAttach);
            }
            idiomas.setLibrosList(attachedLibrosList);
            em.persist(idiomas);
            for (Libros librosListLibros : idiomas.getLibrosList()) {
                librosListLibros.getIdiomasList().add(idiomas);
                librosListLibros = em.merge(librosListLibros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Idiomas idiomas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Idiomas persistentIdiomas = em.find(Idiomas.class, idiomas.getIdIdioma());
            List<Libros> librosListOld = persistentIdiomas.getLibrosList();
            List<Libros> librosListNew = idiomas.getLibrosList();
            List<Libros> attachedLibrosListNew = new ArrayList<Libros>();
            for (Libros librosListNewLibrosToAttach : librosListNew) {
                librosListNewLibrosToAttach = em.getReference(librosListNewLibrosToAttach.getClass(), librosListNewLibrosToAttach.getIdLibro());
                attachedLibrosListNew.add(librosListNewLibrosToAttach);
            }
            librosListNew = attachedLibrosListNew;
            idiomas.setLibrosList(librosListNew);
            idiomas = em.merge(idiomas);
            for (Libros librosListOldLibros : librosListOld) {
                if (!librosListNew.contains(librosListOldLibros)) {
                    librosListOldLibros.getIdiomasList().remove(idiomas);
                    librosListOldLibros = em.merge(librosListOldLibros);
                }
            }
            for (Libros librosListNewLibros : librosListNew) {
                if (!librosListOld.contains(librosListNewLibros)) {
                    librosListNewLibros.getIdiomasList().add(idiomas);
                    librosListNewLibros = em.merge(librosListNewLibros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = idiomas.getIdIdioma();
                if (findIdiomas(id) == null) {
                    throw new NonexistentEntityException("The idiomas with id " + id + " no longer exists.");
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
            Idiomas idiomas;
            try {
                idiomas = em.getReference(Idiomas.class, id);
                idiomas.getIdIdioma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The idiomas with id " + id + " no longer exists.", enfe);
            }
            List<Libros> librosList = idiomas.getLibrosList();
            for (Libros librosListLibros : librosList) {
                librosListLibros.getIdiomasList().remove(idiomas);
                librosListLibros = em.merge(librosListLibros);
            }
            em.remove(idiomas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Idiomas> findIdiomasEntities() {
        return findIdiomasEntities(true, -1, -1);
    }

    public List<Idiomas> findIdiomasEntities(int maxResults, int firstResult) {
        return findIdiomasEntities(false, maxResults, firstResult);
    }

    private List<Idiomas> findIdiomasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Idiomas.class));
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

    public Idiomas findIdiomas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Idiomas.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdiomasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Idiomas> rt = cq.from(Idiomas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
