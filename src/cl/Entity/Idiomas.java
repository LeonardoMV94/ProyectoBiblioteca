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
@Table(name = "idiomas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idiomas.findAll", query = "SELECT i FROM Idiomas i")
    , @NamedQuery(name = "Idiomas.findByIdIdioma", query = "SELECT i FROM Idiomas i WHERE i.idIdioma = :idIdioma")
    , @NamedQuery(name = "Idiomas.findByIdioma", query = "SELECT i FROM Idiomas i WHERE i.idioma = :idioma")})
public class Idiomas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_idioma")
    private Integer idIdioma;
    @Basic(optional = false)
    @Column(name = "idioma")
    private String idioma;
    @JoinTable(name = "idiomas_de_libros", joinColumns = {
        @JoinColumn(name = "idiomas_id_idioma", referencedColumnName = "id_idioma")}, inverseJoinColumns = {
        @JoinColumn(name = "libros_num_serie_libro", referencedColumnName = "id_libro")})
    @ManyToMany
    private List<Libros> librosList;

    public Idiomas() {
    }

    public Idiomas(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public Idiomas(Integer idIdioma, String idioma) {
        this.idIdioma = idIdioma;
        this.idioma = idioma;
    }

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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
        hash += (idIdioma != null ? idIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idiomas)) {
            return false;
        }
        Idiomas other = (Idiomas) object;
        if ((this.idIdioma == null && other.idIdioma != null) || (this.idIdioma != null && !this.idIdioma.equals(other.idIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Idiomas[ idIdioma=" + idIdioma + " ]";
    }
    
}
