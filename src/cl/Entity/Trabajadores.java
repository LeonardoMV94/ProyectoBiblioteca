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
@Table(name = "trabajadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajadores.findAll", query = "SELECT t FROM Trabajadores t")
    , @NamedQuery(name = "Trabajadores.findByNombre", query = "SELECT t FROM Trabajadores t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Trabajadores.findByApellidoPaterno", query = "SELECT t FROM Trabajadores t WHERE t.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Trabajadores.findByApellidoMaterno", query = "SELECT t FROM Trabajadores t WHERE t.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Trabajadores.findByFechaContrato", query = "SELECT t FROM Trabajadores t WHERE t.fechaContrato = :fechaContrato")
    , @NamedQuery(name = "Trabajadores.findByIdDireccionFk", query = "SELECT t FROM Trabajadores t WHERE t.idDireccionFk = :idDireccionFk")
    , @NamedQuery(name = "Trabajadores.findByIdTelefonoFk", query = "SELECT t FROM Trabajadores t WHERE t.idTelefonoFk = :idTelefonoFk")
    , @NamedQuery(name = "Trabajadores.findByIdCorreoFk", query = "SELECT t FROM Trabajadores t WHERE t.idCorreoFk = :idCorreoFk")
    , @NamedQuery(name = "Trabajadores.findByRutTrabajador", query = "SELECT t FROM Trabajadores t WHERE t.rutTrabajador = :rutTrabajador")
    , @NamedQuery(name = "Trabajadores.findByPassword", query = "SELECT t FROM Trabajadores t WHERE t.password = :password")})
public class Trabajadores implements Serializable {

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
    @Column(name = "fecha_contrato")
    @Temporal(TemporalType.DATE)
    private Date fechaContrato;
    @Column(name = "id_direccion_fk")
    private Integer idDireccionFk;
    @Column(name = "id_telefono_fk")
    private Integer idTelefonoFk;
    @Column(name = "id_correo_fk")
    private Integer idCorreoFk;
    @Id
    @Basic(optional = false)
    @Column(name = "rut_trabajador")
    private String rutTrabajador;
    @Column(name = "password")
    private String password;
    @ManyToMany(mappedBy = "trabajadoresList")
    private List<Correos> correosList;
    @ManyToMany(mappedBy = "trabajadoresList")
    private List<Direcciones> direccionesList;
    @ManyToMany(mappedBy = "trabajadoresList")
    private List<Telefonos> telefonosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajadoresRutTrabajador")
    private List<Boletas> boletasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajadoresRutTrabajador")
    private List<Arriendos> arriendosList;

    public Trabajadores() {
    }

    public Trabajadores(String rutTrabajador) {
        this.rutTrabajador = rutTrabajador;
    }

    public Trabajadores(String rutTrabajador, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.rutTrabajador = rutTrabajador;
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

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public Integer getIdDireccionFk() {
        return idDireccionFk;
    }

    public void setIdDireccionFk(Integer idDireccionFk) {
        this.idDireccionFk = idDireccionFk;
    }

    public Integer getIdTelefonoFk() {
        return idTelefonoFk;
    }

    public void setIdTelefonoFk(Integer idTelefonoFk) {
        this.idTelefonoFk = idTelefonoFk;
    }

    public Integer getIdCorreoFk() {
        return idCorreoFk;
    }

    public void setIdCorreoFk(Integer idCorreoFk) {
        this.idCorreoFk = idCorreoFk;
    }

    public String getRutTrabajador() {
        return rutTrabajador;
    }

    public void setRutTrabajador(String rutTrabajador) {
        this.rutTrabajador = rutTrabajador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Correos> getCorreosList() {
        return correosList;
    }

    public void setCorreosList(List<Correos> correosList) {
        this.correosList = correosList;
    }

    @XmlTransient
    public List<Direcciones> getDireccionesList() {
        return direccionesList;
    }

    public void setDireccionesList(List<Direcciones> direccionesList) {
        this.direccionesList = direccionesList;
    }

    @XmlTransient
    public List<Telefonos> getTelefonosList() {
        return telefonosList;
    }

    public void setTelefonosList(List<Telefonos> telefonosList) {
        this.telefonosList = telefonosList;
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
        hash += (rutTrabajador != null ? rutTrabajador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajadores)) {
            return false;
        }
        Trabajadores other = (Trabajadores) object;
        if ((this.rutTrabajador == null && other.rutTrabajador != null) || (this.rutTrabajador != null && !this.rutTrabajador.equals(other.rutTrabajador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Trabajadores[ rutTrabajador=" + rutTrabajador + " ]";
    }
    
}
