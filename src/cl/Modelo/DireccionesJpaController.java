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
import cl.Entity.Direcciones;
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
public class DireccionesJpaController implements Serializable {

    public DireccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DireccionesJpaController() {
    }

    public void create(Direcciones direcciones) {
        if (direcciones.getClientesList() == null) {
            direcciones.setClientesList(new ArrayList<Clientes>());
        }
        if (direcciones.getTrabajadoresList() == null) {
            direcciones.setTrabajadoresList(new ArrayList<Trabajadores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : direcciones.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getRutCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            direcciones.setClientesList(attachedClientesList);
            List<Trabajadores> attachedTrabajadoresList = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListTrabajadoresToAttach : direcciones.getTrabajadoresList()) {
                trabajadoresListTrabajadoresToAttach = em.getReference(trabajadoresListTrabajadoresToAttach.getClass(), trabajadoresListTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresList.add(trabajadoresListTrabajadoresToAttach);
            }
            direcciones.setTrabajadoresList(attachedTrabajadoresList);
            em.persist(direcciones);
            for (Clientes clientesListClientes : direcciones.getClientesList()) {
                clientesListClientes.getDireccionesList().add(direcciones);
                clientesListClientes = em.merge(clientesListClientes);
            }
            for (Trabajadores trabajadoresListTrabajadores : direcciones.getTrabajadoresList()) {
                trabajadoresListTrabajadores.getDireccionesList().add(direcciones);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direcciones direcciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones persistentDirecciones = em.find(Direcciones.class, direcciones.getIddireccion());
            List<Clientes> clientesListOld = persistentDirecciones.getClientesList();
            List<Clientes> clientesListNew = direcciones.getClientesList();
            List<Trabajadores> trabajadoresListOld = persistentDirecciones.getTrabajadoresList();
            List<Trabajadores> trabajadoresListNew = direcciones.getTrabajadoresList();
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getRutCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            direcciones.setClientesList(clientesListNew);
            List<Trabajadores> attachedTrabajadoresListNew = new ArrayList<Trabajadores>();
            for (Trabajadores trabajadoresListNewTrabajadoresToAttach : trabajadoresListNew) {
                trabajadoresListNewTrabajadoresToAttach = em.getReference(trabajadoresListNewTrabajadoresToAttach.getClass(), trabajadoresListNewTrabajadoresToAttach.getRutTrabajador());
                attachedTrabajadoresListNew.add(trabajadoresListNewTrabajadoresToAttach);
            }
            trabajadoresListNew = attachedTrabajadoresListNew;
            direcciones.setTrabajadoresList(trabajadoresListNew);
            direcciones = em.merge(direcciones);
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.getDireccionesList().remove(direcciones);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    clientesListNewClientes.getDireccionesList().add(direcciones);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                }
            }
            for (Trabajadores trabajadoresListOldTrabajadores : trabajadoresListOld) {
                if (!trabajadoresListNew.contains(trabajadoresListOldTrabajadores)) {
                    trabajadoresListOldTrabajadores.getDireccionesList().remove(direcciones);
                    trabajadoresListOldTrabajadores = em.merge(trabajadoresListOldTrabajadores);
                }
            }
            for (Trabajadores trabajadoresListNewTrabajadores : trabajadoresListNew) {
                if (!trabajadoresListOld.contains(trabajadoresListNewTrabajadores)) {
                    trabajadoresListNewTrabajadores.getDireccionesList().add(direcciones);
                    trabajadoresListNewTrabajadores = em.merge(trabajadoresListNewTrabajadores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direcciones.getIddireccion();
                if (findDirecciones(id) == null) {
                    throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.");
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
            Direcciones direcciones;
            try {
                direcciones = em.getReference(Direcciones.class, id);
                direcciones.getIddireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.", enfe);
            }
            List<Clientes> clientesList = direcciones.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.getDireccionesList().remove(direcciones);
                clientesListClientes = em.merge(clientesListClientes);
            }
            List<Trabajadores> trabajadoresList = direcciones.getTrabajadoresList();
            for (Trabajadores trabajadoresListTrabajadores : trabajadoresList) {
                trabajadoresListTrabajadores.getDireccionesList().remove(direcciones);
                trabajadoresListTrabajadores = em.merge(trabajadoresListTrabajadores);
            }
            em.remove(direcciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direcciones> findDireccionesEntities() {
        return findDireccionesEntities(true, -1, -1);
    }

    public List<Direcciones> findDireccionesEntities(int maxResults, int firstResult) {
        return findDireccionesEntities(false, maxResults, firstResult);
    }

    private List<Direcciones> findDireccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direcciones.class));
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

    public Direcciones findDirecciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direcciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direcciones> rt = cq.from(Direcciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
