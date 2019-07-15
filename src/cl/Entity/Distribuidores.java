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
@Table(name = "distribuidores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Distribuidores.findAll", query = "SELECT d FROM Distribuidores d")
    , @NamedQuery(name = "Distribuidores.findByRutDistribuidor", query = "SELECT d FROM Distribuidores d WHERE d.rutDistribuidor = :rutDistribuidor")
    , @NamedQuery(name = "Distribuidores.findByNombreEmpresa", query = "SELECT d FROM Distribuidores d WHERE d.nombreEmpresa = :nombreEmpresa")
    , @NamedQuery(name = "Distribuidores.findByDireccion", query = "SELECT d FROM Distribuidores d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "Distribuidores.findByTelefono", query = "SELECT d FROM Distribuidores d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "Distribuidores.findByA\u00f1oAntig\u00fcedad", query = "SELECT d FROM Distribuidores d WHERE d.a\u00f1oAntig\u00fcedad = :a\u00f1oAntig\u00fcedad")})
public class Distribuidores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "rut_distribuidor")
    private String rutDistribuidor;
    @Basic(optional = false)
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "a\u00f1o_antig\u00fcedad")
    @Temporal(TemporalType.DATE)
    private Date añoAntigüedad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "distribuidoresRutDistribuidor")
    private List<Facturas> facturasList;

    public Distribuidores() {
    }

    public Distribuidores(String rutDistribuidor) {
        this.rutDistribuidor = rutDistribuidor;
    }

    public Distribuidores(String rutDistribuidor, String nombreEmpresa, String direccion, String telefono) {
        this.rutDistribuidor = rutDistribuidor;
        this.nombreEmpresa = nombreEmpresa;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getRutDistribuidor() {
        return rutDistribuidor;
    }

    public void setRutDistribuidor(String rutDistribuidor) {
        this.rutDistribuidor = rutDistribuidor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getAñoAntigüedad() {
        return añoAntigüedad;
    }

    public void setAñoAntigüedad(Date añoAntigüedad) {
        this.añoAntigüedad = añoAntigüedad;
    }

    @XmlTransient
    public List<Facturas> getFacturasList() {
        return facturasList;
    }

    public void setFacturasList(List<Facturas> facturasList) {
        this.facturasList = facturasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutDistribuidor != null ? rutDistribuidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Distribuidores)) {
            return false;
        }
        Distribuidores other = (Distribuidores) object;
        if ((this.rutDistribuidor == null && other.rutDistribuidor != null) || (this.rutDistribuidor != null && !this.rutDistribuidor.equals(other.rutDistribuidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Distribuidores[ rutDistribuidor=" + rutDistribuidor + " ]";
    }
    
}
