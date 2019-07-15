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
import cl.Entity.Trabajadores;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Clientes;
import cl.Entity.Correos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class CorreosJpaController implements Serializable {

    public CorreosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CorreosJpaController() {
    }

    public void create(Correos correos) {
        if (correos.getTrabajadoresList() == null) {
            correos.setTrabajadoresList(new ArrayList<Trabajadores>());
        }
        if (correos.getClientesList() == null) {
            correos.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Trabajadores> attachedTrabajadoresList = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListTrabajadoresToAttach : correos.getTrabajadoresList()) {
                trabajadoresListTrabajadoresToAttach = em.getReference(trabajadoresListTrabajadoresToAttach.getClass(), trabajadoresListTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresList.add(trabajadoresListTrabajadoresToAttach);
            }
            correos.setTrabajadoresList(attachedTrabajadoresList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : correos.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getRutCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            correos.setClientesList(attachedClientesList);
            em.persist(correos);
            for (Trabajadores trabajadoresListTrabajadores : correos.getTrabajadoresList()) {
                trabajadoresListTrabajadores.getCorreosList().add(correos);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            for (Clientes clientesListClientes : correos.getClientesList()) {
                clientesListClientes.getCorreosList().add(correos);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Correos correos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Correos persistentCorreos = em.find(Correos.class, correos.getIdcorreo());
            List<Trabajadores> trabajadoresListOld = persistentCorreos.getTrabajadoresList();
            List<Trabajadores> trabajadoresListNew = correos.getTrabajadoresList();
            List<Clientes> clientesListOld = persistentCorreos.getClientesList();
            List<Clientes> clientesListNew = correos.getClientesList();
            List<Trabajadores> attachedTrabajadoresListNew = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListNewTrabajadoresToAttach : trabajadoresListNew) {
                trabajadoresListNewTrabajadoresToAttach = em.getReference(trabajadoresListNewTrabajadoresToAttach.getClass(), trabajadoresListNewTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresListNew.add(trabajadoresListNewTrabajadoresToAttach);
            }
            trabajadoresListNew = attachedTrabajadoresListNew;
            correos.setTrabajadoresList(trabajadoresListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getRutCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            correos.setClientesList(clientesListNew);
            correos = em.merge(correos);
            for (Trabajadores trabajadoresListOldTrabajadores : trabajadoresListOld) {
                if (!trabajadoresListNew.contains(trabajadoresListOldTrabajadores)) {
                    trabajadoresListOldTrabajadores.getCorreosList().remove(correos);
                    trabajadoresListOldTrabajadores = em.merge(trabajadoresListOldTrabajadores);
                }
            }
            for (Trabajadores trabajadoresListNewTrabajadores : trabajadoresListNew) {
                if (!trabajadoresListOld.contains(trabajadoresListNewTrabajadores)) {
                    trabajadoresListNewTrabajadores.getCorreosList().add(correos);
                    trabajadoresListNewTrabajadores = em.merge(trabajadoresListNewTrabajadores);
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.getCorreosList().remove(correos);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    clientesListNewClientes.getCorreosList().add(correos);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = correos.getIdcorreo();
                if (findCorreos(id) == null) {
                    throw new NonexistentEntityException("The correos with id " + id + " no longer exists.");
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
            Correos correos;
            try {
                correos = em.getReference(Correos.class, id);
                correos.getIdcorreo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The correos with id " + id + " no longer exists.", enfe);
            }
            List<Trabajadores> trabajadoresList = correos.getTrabajadoresList();
            for (Trabajadores trabajadoresListTrabajadores : trabajadoresList) {
                trabajadoresListTrabajadores.getCorreosList().remove(correos);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            List<Clientes> clientesList = correos.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.getCorreosList().remove(correos);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(correos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Correos> findCorreosEntities() {
        return findCorreosEntities(true, -1, -1);
    }

    public List<Correos> findCorreosEntities(int maxResults, int firstResult) {
        return findCorreosEntities(false, maxResults, firstResult);
    }

    private List<Correos> findCorreosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Correos.class));
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

    public Correos findCorreos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Correos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorreosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Correos> rt = cq.from(Correos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
