/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Modelo;

import cl.Modelo.exceptions.IllegalOrphanException;
import cl.Modelo.exceptions.NonexistentEntityException;
import cl.Modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.Entity.Telefonos;
import java.util.ArrayList;
import java.util.List;
import cl.Entity.Direcciones;
import cl.Entity.Correos;
import cl.Entity.Boletas;
import cl.Entity.Arriendos;
import cl.Entity.Clientes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FastDevelopPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ClientesJpaController() {
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getTelefonosList() == null) {
            clientes.setTelefonosList(new ArrayList<Telefonos>());
        }
        if (clientes.getDireccionesList() == null) {
            clientes.setDireccionesList(new ArrayList<Direcciones>());
        }
        if (clientes.getCorreosList() == null) {
            clientes.setCorreosList(new ArrayList<Correos>());
        }
        if (clientes.getBoletasList() == null) {
            clientes.setBoletasList(new ArrayList<Boletas>());
        }
        if (clientes.getArriendosList() == null) {
            clientes.setArriendosList(new ArrayList<Arriendos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Telefonos> attachedTelefonosList = new ArrayList<Telefonos>();
            for (Telefonos telefonosListTelefonosToAttach : clientes.getTelefonosList()) {
                telefonosListTelefonosToAttach = em.getReference(telefonosListTelefonosToAttach.getClass(), telefonosListTelefonosToAttach.getIdtelefono());
                attachedTelefonosList.add(telefonosListTelefonosToAttach);
            }
            clientes.setTelefonosList(attachedTelefonosList);
            List<Direcciones> attachedDireccionesList = new ArrayList<Direcciones>();
            for (Direcciones direccionesListDireccionesToAttach : clientes.getDireccionesList()) {
                direccionesListDireccionesToAttach = em.getReference(direccionesListDireccionesToAttach.getClass(), direccionesListDireccionesToAttach.getIddireccion());
                attachedDireccionesList.add(direccionesListDireccionesToAttach);
            }
            clientes.setDireccionesList(attachedDireccionesList);
            List<Correos> attachedCorreosList = new ArrayList<Correos>();
            for (Correos correosListCorreosToAttach : clientes.getCorreosList()) {
                correosListCorreosToAttach = em.getReference(correosListCorreosToAttach.getClass(), correosListCorreosToAttach.getIdcorreo());
                attachedCorreosList.add(correosListCorreosToAttach);
            }
            clientes.setCorreosList(attachedCorreosList);
            List<Boletas> attachedBoletasList = new ArrayList<Boletas>();
            for (Boletas boletasListBoletasToAttach : clientes.getBoletasList()) {
                boletasListBoletasToAttach = em.getReference(boletasListBoletasToAttach.getClass(), boletasListBoletasToAttach.getFolioBoleta());
                attachedBoletasList.add(boletasListBoletasToAttach);
            }
            clientes.setBoletasList(attachedBoletasList);
            List<Arriendos> attachedArriendosList = new ArrayList<Arriendos>();
            for (Arriendos arriendosListArriendosToAttach : clientes.getArriendosList()) {
                arriendosListArriendosToAttach = em.getReference(arriendosListArriendosToAttach.getClass(), arriendosListArriendosToAttach.getIdArriendo());
                attachedArriendosList.add(arriendosListArriendosToAttach);
            }
            clientes.setArriendosList(attachedArriendosList);
            em.persist(clientes);
            for (Telefonos telefonosListTelefonos : clientes.getTelefonosList()) {
                telefonosListTelefonos.getClientesList().add(clientes);
                telefonosListTelefonos = em.merge(telefonosListTelefonos);
            }
            for (Direcciones direccionesListDirecciones : clientes.getDireccionesList()) {
                direccionesListDirecciones.getClientesList().add(clientes);
                direccionesListDirecciones = em.merge(direccionesListDirecciones);
            }
            for (Correos correosListCorreos : clientes.getCorreosList()) {
                correosListCorreos.getClientesList().add(clientes);
                correosListCorreos = em.merge(correosListCorreos);
            }
            for (Boletas boletasListBoletas : clientes.getBoletasList()) {
                Clientes oldClientesRutClienteOfBoletasListBoletas = boletasListBoletas.getClientesRutCliente();
                boletasListBoletas.setClientesRutCliente(clientes);
                boletasListBoletas = em.merge(boletasListBoletas);
                if (oldClientesRutClienteOfBoletasListBoletas != null) {
                    oldClientesRutClienteOfBoletasListBoletas.getBoletasList().remove(boletasListBoletas);
                    oldClientesRutClienteOfBoletasListBoletas = em.merge(oldClientesRutClienteOfBoletasListBoletas);
                }
            }
            for (Arriendos arriendosListArriendos : clientes.getArriendosList()) {
                Clientes oldClientesRutClienteOfArriendosListArriendos = arriendosListArriendos.getClientesRutCliente();
                arriendosListArriendos.setClientesRutCliente(clientes);
                arriendosListArriendos = em.merge(arriendosListArriendos);
                if (oldClientesRutClienteOfArriendosListArriendos != null) {
                    oldClientesRutClienteOfArriendosListArriendos.getArriendosList().remove(arriendosListArriendos);
                    oldClientesRutClienteOfArriendosListArriendos = em.merge(oldClientesRutClienteOfArriendosListArriendos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getRutCliente()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getRutCliente());
            List<Telefonos> telefonosListOld = persistentClientes.getTelefonosList();
            List<Telefonos> telefonosListNew = clientes.getTelefonosList();
            List<Direcciones> direccionesListOld = persistentClientes.getDireccionesList();
            List<Direcciones> direccionesListNew = clientes.getDireccionesList();
            List<Correos> correosListOld = persistentClientes.getCorreosList();
            List<Correos> correosListNew = clientes.getCorreosList();
            List<Boletas> boletasListOld = persistentClientes.getBoletasList();
            List<Boletas> boletasListNew = clientes.getBoletasList();
            List<Arriendos> arriendosListOld = persistentClientes.getArriendosList();
            List<Arriendos> arriendosListNew = clientes.getArriendosList();
            List<String> illegalOrphanMessages = null;
            for (Boletas boletasListOldBoletas : boletasListOld) {
                if (!boletasListNew.contains(boletasListOldBoletas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boletas " + boletasListOldBoletas + " since its clientesRutCliente field is not nullable.");
                }
            }
            for (Arriendos arriendosListOldArriendos : arriendosListOld) {
                if (!arriendosListNew.contains(arriendosListOldArriendos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Arriendos " + arriendosListOldArriendos + " since its clientesRutCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Telefonos> attachedTelefonosListNew = new ArrayList<Telefonos>();
            for (Telefonos telefonosListNewTelefonosToAttach : telefonosListNew) {
                telefonosListNewTelefonosToAttach = em.getReference(telefonosListNewTelefonosToAttach.getClass(), telefonosListNewTelefonosToAttach.getIdtelefono());
                attachedTelefonosListNew.add(telefonosListNewTelefonosToAttach);
            }
            telefonosListNew = attachedTelefonosListNew;
            clientes.setTelefonosList(telefonosListNew);
            List<Direcciones> attachedDireccionesListNew = new ArrayList<Direcciones>();
            for (Direcciones direccionesListNewDireccionesToAttach : direccionesListNew) {
                direccionesListNewDireccionesToAttach = em.getReference(direccionesListNewDireccionesToAttach.getClass(), direccionesListNewDireccionesToAttach.getIddireccion());
                attachedDireccionesListNew.add(direccionesListNewDireccionesToAttach);
            }
            direccionesListNew = attachedDireccionesListNew;
            clientes.setDireccionesList(direccionesListNew);
            List<Correos> attachedCorreosListNew = new ArrayList<Correos>();
            for (Correos correosListNewCorreosToAttach : correosListNew) {
                correosListNewCorreosToAttach = em.getReference(correosListNewCorreosToAttach.getClass(), correosListNewCorreosToAttach.getIdcorreo());
                attachedCorreosListNew.add(correosListNewCorreosToAttach);
            }
            correosListNew = attachedCorreosListNew;
            clientes.setCorreosList(correosListNew);
            List<Boletas> attachedBoletasListNew = new ArrayList<Boletas>();
            for (Boletas boletasListNewBoletasToAttach : boletasListNew) {
                boletasListNewBoletasToAttach = em.getReference(boletasListNewBoletasToAttach.getClass(), boletasListNewBoletasToAttach.getFolioBoleta());
                attachedBoletasListNew.add(boletasListNewBoletasToAttach);
            }
            boletasListNew = attachedBoletasListNew;
            clientes.setBoletasList(boletasListNew);
            List<Arriendos> attachedArriendosListNew = new ArrayList<Arriendos>();
            for (Arriendos arriendosListNewArriendosToAttach : arriendosListNew) {
                arriendosListNewArriendosToAttach = em.getReference(arriendosListNewArriendosToAttach.getClass(), arriendosListNewArriendosToAttach.getIdArriendo());
                attachedArriendosListNew.add(arriendosListNewArriendosToAttach);
            }
            arriendosListNew = attachedArriendosListNew;
            clientes.setArriendosList(arriendosListNew);
            clientes = em.merge(clientes);
            for (Telefonos telefonosListOldTelefonos : telefonosListOld) {
                if (!telefonosListNew.contains(telefonosListOldTelefonos)) {
                    telefonosListOldTelefonos.getClientesList().remove(clientes);
                    telefonosListOldTelefonos = em.merge(telefonosListOldTelefonos);
                }
            }
            for (Telefonos telefonosListNewTelefonos : telefonosListNew) {
                if (!telefonosListOld.contains(telefonosListNewTelefonos)) {
                    telefonosListNewTelefonos.getClientesList().add(clientes);
                    telefonosListNewTelefonos = em.merge(telefonosListNewTelefonos);
                }
            }
            for (Direcciones direccionesListOldDirecciones : direccionesListOld) {
                if (!direccionesListNew.contains(direccionesListOldDirecciones)) {
                    direccionesListOldDirecciones.getClientesList().remove(clientes);
                    direccionesListOldDirecciones = em.merge(direccionesListOldDirecciones);
                }
            }
            for (Direcciones direccionesListNewDirecciones : direccionesListNew) {
                if (!direccionesListOld.contains(direccionesListNewDirecciones)) {
                    direccionesListNewDirecciones.getClientesList().add(clientes);
                    direccionesListNewDirecciones = em.merge(direccionesListNewDirecciones);
                }
            }
            for (Correos correosListOldCorreos : correosListOld) {
                if (!correosListNew.contains(correosListOldCorreos)) {
                    correosListOldCorreos.getClientesList().remove(clientes);
                    correosListOldCorreos = em.merge(correosListOldCorreos);
                }
            }
            for (Correos correosListNewCorreos : correosListNew) {
                if (!correosListOld.contains(correosListNewCorreos)) {
                    correosListNewCorreos.getClientesList().add(clientes);
                    correosListNewCorreos = em.merge(correosListNewCorreos);
                }
            }
            for (Boletas boletasListNewBoletas : boletasListNew) {
                if (!boletasListOld.contains(boletasListNewBoletas)) {
                    Clientes oldClientesRutClienteOfBoletasListNewBoletas = boletasListNewBoletas.getClientesRutCliente();
                    boletasListNewBoletas.setClientesRutCliente(clientes);
                    boletasListNewBoletas = em.merge(boletasListNewBoletas);
                    if (oldClientesRutClienteOfBoletasListNewBoletas != null && !oldClientesRutClienteOfBoletasListNewBoletas.equals(clientes)) {
                        oldClientesRutClienteOfBoletasListNewBoletas.getBoletasList().remove(boletasListNewBoletas);
                        oldClientesRutClienteOfBoletasListNewBoletas = em.merge(oldClientesRutClienteOfBoletasListNewBoletas);
                    }
                }
            }
            for (Arriendos arriendosListNewArriendos : arriendosListNew) {
                if (!arriendosListOld.contains(arriendosListNewArriendos)) {
                    Clientes oldClientesRutClienteOfArriendosListNewArriendos = arriendosListNewArriendos.getClientesRutCliente();
                    arriendosListNewArriendos.setClientesRutCliente(clientes);
                    arriendosListNewArriendos = em.merge(arriendosListNewArriendos);
                    if (oldClientesRutClienteOfArriendosListNewArriendos != null && !oldClientesRutClienteOfArriendosListNewArriendos.equals(clientes)) {
                        oldClientesRutClienteOfArriendosListNewArriendos.getArriendosList().remove(arriendosListNewArriendos);
                        oldClientesRutClienteOfArriendosListNewArriendos = em.merge(oldClientesRutClienteOfArriendosListNewArriendos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clientes.getRutCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getRutCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Boletas> boletasListOrphanCheck = clientes.getBoletasList();
            for (Boletas boletasListOrphanCheckBoletas : boletasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Boletas " + boletasListOrphanCheckBoletas + " in its boletasList field has a non-nullable clientesRutCliente field.");
            }
            List<Arriendos> arriendosListOrphanCheck = clientes.getArriendosList();
            for (Arriendos arriendosListOrphanCheckArriendos : arriendosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Arriendos " + arriendosListOrphanCheckArriendos + " in its arriendosList field has a non-nullable clientesRutCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Telefonos> telefonosList = clientes.getTelefonosList();
            for (Telefonos telefonosListTelefonos : telefonosList) {
                telefonosListTelefonos.getClientesList().remove(clientes);
                telefonosListTelefonos = em.merge(telefonosListTelefonos);
            }
            List<Direcciones> direccionesList = clientes.getDireccionesList();
            for (Direcciones direccionesListDirecciones : direccionesList) {
                direccionesListDirecciones.getClientesList().remove(clientes);
                direccionesListDirecciones = em.merge(direccionesListDirecciones);
            }
            List<Correos> correosList = clientes.getCorreosList();
            for (Correos correosListCorreos : correosList) {
                correosListCorreos.getClientesList().remove(clientes);
                correosListCorreos = em.merge(correosListCorreos);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
