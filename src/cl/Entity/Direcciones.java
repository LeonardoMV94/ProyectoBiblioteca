/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direcciones.findAll", query = "SELECT d FROM Direcciones d")
    , @NamedQuery(name = "Direcciones.findByIddireccion", query = "SELECT d FROM Direcciones d WHERE d.iddireccion = :iddireccion")
    , @NamedQuery(name = "Direcciones.findByDireccionTrabajador", query = "SELECT d FROM Direcciones d WHERE d.direccionTrabajador = :direccionTrabajador")})
public class Direcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_direccion")
    private Integer iddireccion;
    @Basic(optional = false)
    @Column(name = "direccion_trabajador")
    private String direccionTrabajador;
    @ManyToMany(mappedBy = "direccionesList")
    private List<Clientes> clientesList;
    @JoinTable(name = "trabajadores_has_direcciones", joinColumns = {
        @JoinColumn(name = "direcciones_Id_direccion", referencedColumnName = "Id_direccion")}, inverseJoinColumns = {
        @JoinColumn(name = "trabajadores_rut_trabajador", referencedColumnName = "rut_trabajador")})
    @ManyToMany
    private List<Trabajadores> trabajadoresList;

    public Direcciones() {
    }

    public Direcciones(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Direcciones(Integer iddireccion, String direccionTrabajador) {
        this.iddireccion = iddireccion;
        this.direccionTrabajador = direccionTrabajador;
    }

    public Integer getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getDireccionTrabajador() {
        return direccionTrabajador;
    }

    public void setDireccionTrabajador(String direccionTrabajador) {
        this.direccionTrabajador = direccionTrabajador;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @XmlTransient
    public List<Trabajadores> getTrabajadoresList() {
        return trabajadoresList;
    }

    public void setTrabajadoresList(List<Trabajadores> trabajadoresList) {
        this.trabajadoresList = trabajadoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddireccion != null ? iddireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direcciones)) {
            return false;
        }
        Direcciones other = (Direcciones) object;
        if ((this.iddireccion == null && other.iddireccion != null) || (this.iddireccion != null && !this.iddireccion.equals(other.iddireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Direcciones[ iddireccion=" + iddireccion + " ]";
    }
    
}
