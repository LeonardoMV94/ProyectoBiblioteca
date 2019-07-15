/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "editoriales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Editoriales.findAll", query = "SELECT e FROM Editoriales e")
    , @NamedQuery(name = "Editoriales.findByIdeditorial", query = "SELECT e FROM Editoriales e WHERE e.ideditorial = :ideditorial")
    , @NamedQuery(name = "Editoriales.findByNomEditorial", query = "SELECT e FROM Editoriales e WHERE e.nomEditorial = :nomEditorial")})
public class Editoriales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_editorial")
    private Integer ideditorial;
    @Column(name = "nom_editorial")
    private String nomEditorial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editorialesIdeditorial")
    private List<Libros> librosList;

    public Editoriales() {
    }

    public Editoriales(Integer ideditorial) {
        this.ideditorial = ideditorial;
    }

    public Integer getIdeditorial() {
        return ideditorial;
    }

    public void setIdeditorial(Integer ideditorial) {
        this.ideditorial = ideditorial;
    }

    public String getNomEditorial() {
        return nomEditorial;
    }

    public void setNomEditorial(String nomEditorial) {
        this.nomEditorial = nomEditorial;
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
        hash += (ideditorial != null ? ideditorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Editoriales)) {
            return false;
        }
        Editoriales other = (Editoriales) object;
        if ((this.ideditorial == null && other.ideditorial != null) || (this.ideditorial != null && !this.ideditorial.equals(other.ideditorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Editoriales[ ideditorial=" + ideditorial + " ]";
    }
    
}
