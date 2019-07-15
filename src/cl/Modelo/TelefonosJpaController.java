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
import cl.Entity.Clientes;
import cl.Entity.Telefonos;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Trabajadores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class TelefonosJpaController implements Serializable {

    public TelefonosJpaController() {
    }

    public TelefonosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefonos telefonos) {
        if (telefonos.getClientesList() == null) {
            telefonos.setClientesList(new ArrayList<Clientes>());
        }
        if (telefonos.getTrabajadoresList() == null) {
            telefonos.setTrabajadoresList(new ArrayList<Trabajadores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : telefonos.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getRutCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            telefonos.setClientesList(attachedClientesList);
            List<Trabajadores> attachedTrabajadoresList = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListTrabajadoresToAttach : telefonos.getTrabajadoresList()) {
                trabajadoresListTrabajadoresToAttach = em.getReference(trabajadoresListTrabajadoresToAttach.getClass(), trabajadoresListTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresList.add(trabajadoresListTrabajadoresToAttach);
            }
            telefonos.setTrabajadoresList(attachedTrabajadoresList);
            em.persist(telefonos);
            for (Clientes clientesListClientes : telefonos.getClientesList()) {
                clientesListClientes.getTelefonosList().add(telefonos);
                clientesListClientes = em.merge(clientesListClientes);
            }
            for (Trabajadores trabajadoresListTrabajadores : telefonos.getTrabajadoresList()) {
                trabajadoresListTrabajadores.getTelefonosList().add(telefonos);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefonos telefonos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefonos persistentTelefonos = em.find(Telefonos.class, telefonos.getIdtelefono());
            List<Clientes> clientesListOld = persistentTelefonos.getClientesList();
            List<Clientes> clientesListNew = telefonos.getClientesList();
            List<Trabajadores> trabajadoresListOld = persistentTelefonos.getTrabajadoresList();
            List<Trabajadores> trabajadoresListNew = telefonos.getTrabajadoresList();
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getRutCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            telefonos.setClientesList(clientesListNew);
            List<Trabajadores> attachedTrabajadoresListNew = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListNewTrabajadoresToAttach : trabajadoresListNew) {
                trabajadoresListNewTrabajadoresToAttach = em.getReference(trabajadoresListNewTrabajadoresToAttach.getClass(), trabajadoresListNewTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresListNew.add(trabajadoresListNewTrabajadoresToAttach);
            }
            trabajadoresListNew = attachedTrabajadoresListNew;
            telefonos.setTrabajadoresList(trabajadoresListNew);
            telefonos = em.merge(telefonos);
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.getTelefonosList().remove(telefonos);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    clientesListNewClientes.getTelefonosList().add(telefonos);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                }
            }
            for (Trabajadores trabajadoresListOldTrabajadores : trabajadoresListOld) {
                if (!trabajadoresListNew.contains(trabajadoresListOldTrabajadores)) {
                    trabajadoresListOldTrabajadores.getTelefonosList().remove(telefonos);
                    trabajadoresListOldTrabajadores = em.merge(trabajadoresListOldTrabajadores);
                }
            }
            for (Trabajadores trabajadoresListNewTrabajadores : trabajadoresListNew) {
                if (!trabajadoresListOld.contains(trabajadoresListNewTrabajadores)) {
                    trabajadoresListNewTrabajadores.getTelefonosList().add(telefonos);
                    trabajadoresListNewTrabajadores = em.merge(trabajadoresListNewTrabajadores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefonos.getIdtelefono();
                if (findTelefonos(id) == null) {
                    throw new NonexistentEntityException("The telefonos with id " + id + " no longer exists.");
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
            Telefonos telefonos;
            try {
                telefonos = em.getReference(Telefonos.class, id);
                telefonos.getIdtelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonos with id " + id + " no longer exists.", enfe);
            }
            List<Clientes> clientesList = telefonos.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.getTelefonosList().remove(telefonos);
                clientesListClientes = em.merge(clientesListClientes);
            }
            List<Trabajadores> trabajadoresList = telefonos.getTrabajadoresList();
            for (Trabajadores trabajadoresListTrabajadores : trabajadoresList) {
                trabajadoresListTrabajadores.getTelefonosList().remove(telefonos);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            em.remove(telefonos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefonos> findTelefonosEntities() {
        return findTelefonosEntities(true, -1, -1);
    }

    public List<Telefonos> findTelefonosEntities(int maxResults, int firstResult) {
        return findTelefonosEntities(false, maxResults, firstResult);
    }

    private List<Telefonos> findTelefonosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefonos.class));
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

    public Telefonos findTelefonos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefonos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefonos> rt = cq.from(Telefonos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
