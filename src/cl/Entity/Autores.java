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
@Table(name = "autores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autores.findAll", query = "SELECT a FROM Autores a")
    , @NamedQuery(name = "Autores.findByIdAutor", query = "SELECT a FROM Autores a WHERE a.idAutor = :idAutor")
    , @NamedQuery(name = "Autores.findByNombreAutor", query = "SELECT a FROM Autores a WHERE a.nombreAutor = :nombreAutor")
    , @NamedQuery(name = "Autores.findByApellidoPaternoAutor", query = "SELECT a FROM Autores a WHERE a.apellidoPaternoAutor = :apellidoPaternoAutor")
    , @NamedQuery(name = "Autores.findByApellidoMaternoAutor", query = "SELECT a FROM Autores a WHERE a.apellidoMaternoAutor = :apellidoMaternoAutor")})
public class Autores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_autor")
    private Integer idAutor;
    @Basic(optional = false)
    @Column(name = "nombre_autor")
    private String nombreAutor;
    @Basic(optional = false)
    @Column(name = "apellido_paterno_autor")
    private String apellidoPaternoAutor;
    @Basic(optional = false)
    @Column(name = "apellido_materno_autor")
    private String apellidoMaternoAutor;
    @JoinTable(name = "autores_de_libros", joinColumns = {
        @JoinColumn(name = "autores_id_autor", referencedColumnName = "id_autor")}, inverseJoinColumns = {
        @JoinColumn(name = "libros_num_serie_libro", referencedColumnName = "id_libro")})
    @ManyToMany
    private List<Libros> librosList;

    public Autores() {
    }

    public Autores(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Autores(Integer idAutor, String nombreAutor, String apellidoPaternoAutor, String apellidoMaternoAutor) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.apellidoPaternoAutor = apellidoPaternoAutor;
        this.apellidoMaternoAutor = apellidoMaternoAutor;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getApellidoPaternoAutor() {
        return apellidoPaternoAutor;
    }

    public void setApellidoPaternoAutor(String apellidoPaternoAutor) {
        this.apellidoPaternoAutor = apellidoPaternoAutor;
    }

    public String getApellidoMaternoAutor() {
        return apellidoMaternoAutor;
    }

    public void setApellidoMaternoAutor(String apellidoMaternoAutor) {
        this.apellidoMaternoAutor = apellidoMaternoAutor;
    }

    @XmlTransient
    public List<Libros> getLibrosList() {
        return librosList;
    }

    public void setLibrosList(List<Libros> librosList) {
        this.librosList = librosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAutor != null ? idAutor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autores)) {
            return false;
        }
        Autores other = (Autores) object;
        if ((this.idAutor == null && other.idAutor != null) || (this.idAutor != null && !this.idAutor.equals(other.idAutor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Autores[ idAutor=" + idAutor + " ]";
    }
    
}
