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
@Table(name = "correos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correos.findAll", query = "SELECT c FROM Correos c")
    , @NamedQuery(name = "Correos.findByIdcorreo", query = "SELECT c FROM Correos c WHERE c.idcorreo = :idcorreo")
    , @NamedQuery(name = "Correos.findByCorreo", query = "SELECT c FROM Correos c WHERE c.correo = :correo")})
public class Correos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_correo")
    private Integer idcorreo;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @JoinTable(name = "trabajadores_has_correos", joinColumns = {
        @JoinColumn(name = "correos_Id_correo", referencedColumnName = "Id_correo")}, inverseJoinColumns = {
        @JoinColumn(name = "trabajadores_rut_trabajador", referencedColumnName = "rut_trabajador")})
    @ManyToMany
    private List<Trabajadores> trabajadoresList;
    @ManyToMany(mappedBy = "correosList")
    private List<Clientes> clientesList;

    public Correos() {
    }

    public Correos(Integer idcorreo) {
        this.idcorreo = idcorreo;
    }

    public Correos(Integer idcorreo, String correo) {
        this.idcorreo = idcorreo;
        this.correo = correo;
    }

    public Integer getIdcorreo() {
        return idcorreo;
    }

    public void setIdcorreo(Integer idcorreo) {
        this.idcorreo = idcorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public List<Trabajadores> getTrabajadoresList() {
        return trabajadoresList;
    }

    public void setTrabajadoresList(List<Trabajadores> trabajadoresList) {
        this.trabajadoresList = trabajadoresList;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorreo != null ? idcorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correos)) {
            return false;
        }
        Correos other = (Correos) object;
        if ((this.idcorreo == null && other.idcorreo != null) || (this.idcorreo != null && !this.idcorreo.equals(other.idcorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Correos[ idcorreo=" + idcorreo + " ]";
    }
    
}
