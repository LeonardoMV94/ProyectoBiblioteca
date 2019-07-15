/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.IllegalOrphanException;
import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Entity.Editoriales;
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
public class EditorialesJpaController implements Serializable {

    public EditorialesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EditorialesJpaController() {
    }

    public void create(Editoriales editoriales) {
        if (editoriales.getLibrosList() == null) {
            editoriales.setLibrosList(new ArrayList<Libros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Libros> attachedLibrosList = new ArrayList<Libros>();
            for (Libros librosListLibrosToAttach : editoriales.getLibrosList()) {
                librosListLibrosToAttach = em.getReference(librosListLibrosToAttach.getClass(), librosListLibrosToAttach.getIdLibro());
                attachedLibrosList.add(librosListLibrosToAttach);
            }
            editoriales.setLibrosList(attachedLibrosList);
            em.persist(editoriales);
            for (Libros librosListLibros : editoriales.getLibrosList()) {
                Editoriales oldEditorialesIdeditorialOfLibrosListLibros = librosListLibros.getEditorialesIdeditorial();
                librosListLibros.setEditorialesIdeditorial(editoriales);
                librosListLibros = em.merge(librosListLibros);
                if (oldEditorialesIdeditorialOfLibrosListLibros != null) {
                    oldEditorialesIdeditorialOfLibrosListLibros.getLibrosList().remove(librosListLibros);
                    oldEditorialesIdeditorialOfLibrosListLibros = em.merge(oldEditorialesIdeditorialOfLibrosListLibros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Editoriales editoriales) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Editoriales persistentEditoriales = em.find(Editoriales.class, editoriales.getIdeditorial());
            List<Libros> librosListOld = persistentEditoriales.getLibrosList();
            List<Libros> librosListNew = editoriales.getLibrosList();
            List<String> illegalOrphanMessages = null;
            for (Libros librosListOldLibros : librosListOld) {
                if (!librosListNew.contains(librosListOldLibros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Libros " + librosListOldLibros + " since its editorialesIdeditorial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Libros> attachedLibrosListNew = new ArrayList<Libros>();
            for (Libros librosListNewLibrosToAttach : librosListNew) {
                librosListNewLibrosToAttach = em.getReference(librosListNewLibrosToAttach.getClass(), librosListNewLibrosToAttach.getIdLibro());
                attachedLibrosListNew.add(librosListNewLibrosToAttach);
            }
            librosListNew = attachedLibrosListNew;
            editoriales.setLibrosList(librosListNew);
            editoriales = em.merge(editoriales);
            for (Libros librosListNewLibros : librosListNew) {
                if (!librosListOld.contains(librosListNewLibros)) {
                    Editoriales oldEditorialesIdeditorialOfLibrosListNewLibros = librosListNewLibros.getEditorialesIdeditorial();
                    librosListNewLibros.setEditorialesIdeditorial(editoriales);
                    librosListNewLibros = em.merge(librosListNewLibros);
                    if (oldEditorialesIdeditorialOfLibrosListNewLibros != null && !oldEditorialesIdeditorialOfLibrosListNewLibros.equals(editoriales)) {
                        oldEditorialesIdeditorialOfLibrosListNewLibros.getLibrosList().remove(librosListNewLibros);
                        oldEditorialesIdeditorialOfLibrosListNewLibros = em.merge(oldEditorialesIdeditorialOfLibrosListNewLibros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = editoriales.getIdeditorial();
                if (findEditoriales(id) == null) {
                    throw new NonexistentEntityException("The editoriales with id " + id + " no longer exists.");
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
            Editoriales editoriales;
            try {
                editoriales = em.getReference(Editoriales.class, id);
                editoriales.getIdeditorial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The editoriales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Libros> librosListOrphanCheck = editoriales.getLibrosList();
            for (Libros librosListOrphanCheckLibros : librosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Editoriales (" + editoriales + ") cannot be destroyed since the Libros " + librosListOrphanCheckLibros + " in its librosList field has a non-nullable editorialesIdeditorial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(editoriales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Editoriales> findEditorialesEntities() {
        return findEditorialesEntities(true, -1, -1);
    }

    public List<Editoriales> findEditorialesEntities(int maxResults, int firstResult) {
        return findEditorialesEntities(false, maxResults, firstResult);
    }

    private List<Editoriales> findEditorialesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Editoriales.class));
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

    public Editoriales findEditoriales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Editoriales.class, id);
        } finally {
            em.close();
        }
    }

    public int getEditorialesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Editoriales> rt = cq.from(Editoriales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
