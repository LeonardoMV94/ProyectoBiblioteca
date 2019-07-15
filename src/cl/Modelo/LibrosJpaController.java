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
import cl.Entity.Editoriales;
import cl.Entity.Idiomas;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Autores;
import cl.Entity.Categorias;
import cl.Entity.EjemplarLibros;
import cl.Entity.Libros;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class LibrosJpaController implements Serializable {

    public LibrosJpaController() {
    }

    public LibrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Libros libros) {
        if (libros.getIdiomasList() == null) {
            libros.setIdiomasList(new ArrayList<Idiomas>());
        }
        if (libros.getAutoresList() == null) {
            libros.setAutoresList(new ArrayList<Autores>());
        }
        if (libros.getCategoriasList() == null) {
            libros.setCategoriasList(new ArrayList<Categorias>());
        }
        if (libros.getEjemplarLibrosList() == null) {
            libros.setEjemplarLibrosList(new ArrayList<EjemplarLibros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Editoriales editorialesIdeditorial = libros.getEditorialesIdeditorial();
            if (editorialesIdeditorial != null) {
                editorialesIdeditorial = em.getReference(editorialesIdeditorial.getClass(), editorialesIdeditorial.getIdeditorial());
                libros.setEditorialesIdeditorial(editorialesIdeditorial);
            }
            List<Idiomas> attachedIdiomasList = new ArrayList<Idiomas>();
            for (Idiomas idiomasListIdiomasToAttach : libros.getIdiomasList()) {
                idiomasListIdiomasToAttach = em.getReference(idiomasListIdiomasToAttach.getClass(), idiomasListIdiomasToAttach.getIdIdioma());
                attachedIdiomasList.add(idiomasListIdiomasToAttach);
            }
            libros.setIdiomasList(attachedIdiomasList);
            List<Autores> attachedAutoresList = new ArrayList<Autores>();
            for (Autores autoresListAutoresToAttach : libros.getAutoresList()) {
                autoresListAutoresToAttach = em.getReference(autoresListAutoresToAttach.getClass(), autoresListAutoresToAttach.getIdAutor());
                attachedAutoresList.add(autoresListAutoresToAttach);
            }
            libros.setAutoresList(attachedAutoresList);
            List<Categorias> attachedCategoriasList = new ArrayList<Categorias>();
            for (Categorias categoriasListCategoriasToAttach : libros.getCategoriasList()) {
                categoriasListCategoriasToAttach = em.getReference(categoriasListCategoriasToAttach.getClass(), categoriasListCategoriasToAttach.getIdCategoria());
                attachedCategoriasList.add(categoriasListCategoriasToAttach);
            }
            libros.setCategoriasList(attachedCategoriasList);
            List<EjemplarLibros> attachedEjemplarLibrosList = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListEjemplarLibrosToAttach : libros.getEjemplarLibrosList()) {
                ejemplarLibrosListEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListEjemplarLibrosToAttach.getClass(), ejemplarLibrosListEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosList.add(ejemplarLibrosListEjemplarLibrosToAttach);
            }
            libros.setEjemplarLibrosList(attachedEjemplarLibrosList);
            em.persist(libros);
            if (editorialesIdeditorial != null) {
                editorialesIdeditorial.getLibrosList().add(libros);
                editorialesIdeditorial = em.merge(editorialesIdeditorial);
            }
            for (Idiomas idiomasListIdiomas : libros.getIdiomasList()) {
                idiomasListIdiomas.getLibrosList().add(libros);
                idiomasListIdiomas = em.merge(idiomasListIdiomas);
            }
            for (Autores autoresListAutores : libros.getAutoresList()) {
                autoresListAutores.getLibrosList().add(libros);
                autoresListAutores = em.merge(autoresListAutores);
            }
            for (Categorias categoriasListCategorias : libros.getCategoriasList()) {
                categoriasListCategorias.getLibrosList().add(libros);
                categoriasListCategorias = em.merge(categoriasListCategorias);
            }
            for (EjemplarLibros ejemplarLibrosListEjemplarLibros : libros.getEjemplarLibrosList()) {
                Libros oldLibrosOfEjemplarLibrosListEjemplarLibros = ejemplarLibrosListEjemplarLibros.getLibros();
                ejemplarLibrosListEjemplarLibros.setLibros(libros);
                ejemplarLibrosListEjemplarLibros = em.merge(ejemplarLibrosListEjemplarLibros);
                if (oldLibrosOfEjemplarLibrosListEjemplarLibros != null) {
                    oldLibrosOfEjemplarLibrosListEjemplarLibros.getEjemplarLibrosList().remove(ejemplarLibrosListEjemplarLibros);
                    oldLibrosOfEjemplarLibrosListEjemplarLibros = em.merge(oldLibrosOfEjemplarLibrosListEjemplarLibros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Libros libros) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libros persistentLibros = em.find(Libros.class, libros.getIdLibro());
            Editoriales editorialesIdeditorialOld = persistentLibros.getEditorialesIdeditorial();
            Editoriales editorialesIdeditorialNew = libros.getEditorialesIdeditorial();
            List<Idiomas> idiomasListOld = persistentLibros.getIdiomasList();
            List<Idiomas> idiomasListNew = libros.getIdiomasList();
            List<Autores> autoresListOld = persistentLibros.getAutoresList();
            List<Autores> autoresListNew = libros.getAutoresList();
            List<Categorias> categoriasListOld = persistentLibros.getCategoriasList();
            List<Categorias> categoriasListNew = libros.getCategoriasList();
            List<EjemplarLibros> ejemplarLibrosListOld = persistentLibros.getEjemplarLibrosList();
            List<EjemplarLibros> ejemplarLibrosListNew = libros.getEjemplarLibrosList();
            List<String> illegalOrphanMessages = null;
            for (EjemplarLibros ejemplarLibrosListOldEjemplarLibros : ejemplarLibrosListOld) {
                if (!ejemplarLibrosListNew.contains(ejemplarLibrosListOldEjemplarLibros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EjemplarLibros " + ejemplarLibrosListOldEjemplarLibros + " since its libros field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (editorialesIdeditorialNew != null) {
                editorialesIdeditorialNew = em.getReference(editorialesIdeditorialNew.getClass(), editorialesIdeditorialNew.getIdeditorial());
                libros.setEditorialesIdeditorial(editorialesIdeditorialNew);
            }
            List<Idiomas> attachedIdiomasListNew = new ArrayList<Idiomas>();
            for (Idiomas idiomasListNewIdiomasToAttach : idiomasListNew) {
                idiomasListNewIdiomasToAttach = em.getReference(idiomasListNewIdiomasToAttach.getClass(), idiomasListNewIdiomasToAttach.getIdIdioma());
                attachedIdiomasListNew.add(idiomasListNewIdiomasToAttach);
            }
            idiomasListNew = attachedIdiomasListNew;
            libros.setIdiomasList(idiomasListNew);
            List<Autores> attachedAutoresListNew = new ArrayList<Autores>();
            for (Autores autoresListNewAutoresToAttach : autoresListNew) {
                autoresListNewAutoresToAttach = em.getReference(autoresListNewAutoresToAttach.getClass(), autoresListNewAutoresToAttach.getIdAutor());
                attachedAutoresListNew.add(autoresListNewAutoresToAttach);
            }
            autoresListNew = attachedAutoresListNew;
            libros.setAutoresList(autoresListNew);
            List<Categorias> attachedCategoriasListNew = new ArrayList<Categorias>();
            for (Categorias categoriasListNewCategoriasToAttach : categoriasListNew) {
                categoriasListNewCategoriasToAttach = em.getReference(categoriasListNewCategoriasToAttach.getClass(), categoriasListNewCategoriasToAttach.getIdCategoria());
                attachedCategoriasListNew.add(categoriasListNewCategoriasToAttach);
            }
            categoriasListNew = attachedCategoriasListNew;
            libros.setCategoriasList(categoriasListNew);
            List<EjemplarLibros> attachedEjemplarLibrosListNew = new ArrayList<EjemplarLibros>();
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibrosToAttach : ejemplarLibrosListNew) {
                ejemplarLibrosListNewEjemplarLibrosToAttach = em.getReference(ejemplarLibrosListNewEjemplarLibrosToAttach.getClass(), ejemplarLibrosListNewEjemplarLibrosToAttach.getEjemplarLibrosPK());
                attachedEjemplarLibrosListNew.add(ejemplarLibrosListNewEjemplarLibrosToAttach);
            }
            ejemplarLibrosListNew = attachedEjemplarLibrosListNew;
            libros.setEjemplarLibrosList(ejemplarLibrosListNew);
            libros = em.merge(libros);
            if (editorialesIdeditorialOld != null && !editorialesIdeditorialOld.equals(editorialesIdeditorialNew)) {
                editorialesIdeditorialOld.getLibrosList().remove(libros);
                editorialesIdeditorialOld = em.merge(editorialesIdeditorialOld);
            }
            if (editorialesIdeditorialNew != null && !editorialesIdeditorialNew.equals(editorialesIdeditorialOld)) {
                editorialesIdeditorialNew.getLibrosList().add(libros);
                editorialesIdeditorialNew = em.merge(editorialesIdeditorialNew);
            }
            for (Idiomas idiomasListOldIdiomas : idiomasListOld) {
                if (!idiomasListNew.contains(idiomasListOldIdiomas)) {
                    idiomasListOldIdiomas.getLibrosList().remove(libros);
                    idiomasListOldIdiomas = em.merge(idiomasListOldIdiomas);
                }
            }
            for (Idiomas idiomasListNewIdiomas : idiomasListNew) {
                if (!idiomasListOld.contains(idiomasListNewIdiomas)) {
                    idiomasListNewIdiomas.getLibrosList().add(libros);
                    idiomasListNewIdiomas = em.merge(idiomasListNewIdiomas);
                }
            }
            for (Autores autoresListOldAutores : autoresListOld) {
                if (!autoresListNew.contains(autoresListOldAutores)) {
                    autoresListOldAutores.getLibrosList().remove(libros);
                    autoresListOldAutores = em.merge(autoresListOldAutores);
                }
            }
            for (Autores autoresListNewAutores : autoresListNew) {
                if (!autoresListOld.contains(autoresListNewAutores)) {
                    autoresListNewAutores.getLibrosList().add(libros);
                    autoresListNewAutores = em.merge(autoresListNewAutores);
                }
            }
            for (Categorias categoriasListOldCategorias : categoriasListOld) {
                if (!categoriasListNew.contains(categoriasListOldCategorias)) {
                    categoriasListOldCategorias.getLibrosList().remove(libros);
                    categoriasListOldCategorias = em.merge(categoriasListOldCategorias);
                }
            }
            for (Categorias categoriasListNewCategorias : categoriasListNew) {
                if (!categoriasListOld.contains(categoriasListNewCategorias)) {
                    categoriasListNewCategorias.getLibrosList().add(libros);
                    categoriasListNewCategorias = em.merge(categoriasListNewCategorias);
                }
            }
            for (EjemplarLibros ejemplarLibrosListNewEjemplarLibros : ejemplarLibrosListNew) {
                if (!ejemplarLibrosListOld.contains(ejemplarLibrosListNewEjemplarLibros)) {
                    Libros oldLibrosOfEjemplarLibrosListNewEjemplarLibros = ejemplarLibrosListNewEjemplarLibros.getLibros();
                    ejemplarLibrosListNewEjemplarLibros.setLibros(libros);
                    ejemplarLibrosListNewEjemplarLibros = em.merge(ejemplarLibrosListNewEjemplarLibros);
                    if (oldLibrosOfEjemplarLibrosListNewEjemplarLibros != null && !oldLibrosOfEjemplarLibrosListNewEjemplarLibros.equals(libros)) {
                        oldLibrosOfEjemplarLibrosListNewEjemplarLibros.getEjemplarLibrosList().remove(ejemplarLibrosListNewEjemplarLibros);
                        oldLibrosOfEjemplarLibrosListNewEjemplarLibros = em.merge(oldLibrosOfEjemplarLibrosListNewEjemplarLibros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = libros.getIdLibro();
                if (findLibros(id) == null) {
                    throw new NonexistentEntityException("The libros with id " + id + " no longer exists.");
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
            Libros libros;
            try {
                libros = em.getReference(Libros.class, id);
                libros.getIdLibro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libros with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EjemplarLibros> ejemplarLibrosListOrphanCheck = libros.getEjemplarLibrosList();
            for (EjemplarLibros ejemplarLibrosListOrphanCheckEjemplarLibros : ejemplarLibrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Libros (" + libros + ") cannot be destroyed since the EjemplarLibros " + ejemplarLibrosListOrphanCheckEjemplarLibros + " in its ejemplarLibrosList field has a non-nullable libros field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Editoriales editorialesIdeditorial = libros.getEditorialesIdeditorial();
            if (editorialesIdeditorial != null) {
                editorialesIdeditorial.getLibrosList().remove(libros);
                editorialesIdeditorial = em.merge(editorialesIdeditorial);
            }
            List<Idiomas> idiomasList = libros.getIdiomasList();
            for (Idiomas idiomasListIdiomas : idiomasList) {
                idiomasListIdiomas.getLibrosList().remove(libros);
                idiomasListIdiomas = em.merge(idiomasListIdiomas);
            }
            List<Autores> autoresList = libros.getAutoresList();
            for (Autores autoresListAutores : autoresList) {
                autoresListAutores.getLibrosList().remove(libros);
                autoresListAutores = em.merge(autoresListAutores);
            }
            List<Categorias> categoriasList = libros.getCategoriasList();
            for (Categorias categoriasListCategorias : categoriasList) {
                categoriasListCategorias.getLibrosList().remove(libros);
                categoriasListCategorias = em.merge(categoriasListCategorias);
            }
            em.remove(libros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Libros> findLibrosEntities() {
        return findLibrosEntities(true, -1, -1);
    }

    public List<Libros> findLibrosEntities(int maxResults, int firstResult) {
        return findLibrosEntities(false, maxResults, firstResult);
    }

    private List<Libros> findLibrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Libros.class));
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

    public Libros findLibros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Libros.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Libros> rt = cq.from(Libros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
