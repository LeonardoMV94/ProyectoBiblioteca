/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByApellidoPaterno", query = "SELECT c FROM Clientes c WHERE c.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Clientes.findByApellidoMaterno", query = "SELECT c FROM Clientes c WHERE c.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Clientes.findByFechaNacimiento", query = "SELECT c FROM Clientes c WHERE c.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Clientes.findByRutCliente", query = "SELECT c FROM Clientes c WHERE c.rutCliente = :rutCliente")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Id
    @Basic(optional = false)
    @Column(name = "rut_cliente")
    private String rutCliente;
    @JoinTable(name = "clientes_has_telefonos", joinColumns = {
        @JoinColumn(name = "clientes_rut_cliente", referencedColumnName = "rut_cliente")}, inverseJoinColumns = {
        @JoinColumn(name = "telefonos_Id_telefono", referencedColumnName = "Id_telefono")})
    @ManyToMany
    private List<Telefonos> telefonosList;
    @JoinTable(name = "direcciones_has_clientes", joinColumns = {
        @JoinColumn(name = "clientes_rut_cliente", referencedColumnName = "rut_cliente")}, inverseJoinColumns = {
        @JoinColumn(name = "direcciones_Id_direccion", referencedColumnName = "Id_direccion")})
    @ManyToMany
    private List<Direcciones> direccionesList;
    @JoinTable(name = "clientes_has_correos", joinColumns = {
        @JoinColumn(name = "clientes_rut_cliente", referencedColumnName = "rut_cliente")}, inverseJoinColumns = {
        @JoinColumn(name = "correos_Id_correo", referencedColumnName = "Id_correo")})
    @ManyToMany
    private List<Correos> correosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientesRutCliente")
    private List<Boletas> boletasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientesRutCliente")
    private List<Arriendos> arriendosList;

    public Clientes() {
    }

    public Clientes(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public Clientes(String rutCliente, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.rutCliente = rutCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    @XmlTransient
    public List<Telefonos> getTelefonosList() {
        return telefonosList;
    }

    public void setTelefonosList(List<Telefonos> telefonosList) {
        this.telefonosList = telefonosList;
    }

    @XmlTransient
    public List<Direcciones> getDireccionesList() {
        return direccionesList;
    }

    public void setDireccionesList(List<Direcciones> direccionesList) {
        this.direccionesList = direccionesList;
    }

    @XmlTransient
    public List<Correos> getCorreosList() {
        return correosList;
    }

    public void setCorreosList(List<Correos> correosList) {
        this.correosList = correosList;
    }

    @XmlTransient
    public List<Boletas> getBoletasList() {
        return boletasList;
    }

    public void setBoletasList(List<Boletas> boletasList) {
        this.boletasList = boletasList;
    }

    @XmlTransient
    public List<Arriendos> getArriendosList() {
        return arriendosList;
    }

    public void setArriendosList(List<Arriendos> arriendosList) {
        this.arriendosList = arriendosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutCliente != null ? rutCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.rutCliente == null && other.rutCliente != null) || (this.rutCliente != null && !this.rutCliente.equals(other.rutCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Clientes[ rutCliente=" + rutCliente + " ]";
    }
    
}
