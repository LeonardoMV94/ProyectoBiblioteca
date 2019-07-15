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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "libros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libros.findAll", query = "SELECT l FROM Libros l")
    , @NamedQuery(name = "Libros.findByIdLibro", query = "SELECT l FROM Libros l WHERE l.idLibro = :idLibro")
    , @NamedQuery(name = "Libros.findByIsbn", query = "SELECT l FROM Libros l WHERE l.isbn = :isbn")
    , @NamedQuery(name = "Libros.findByTitulo", query = "SELECT l FROM Libros l WHERE l.titulo = :titulo")
    , @NamedQuery(name = "Libros.findByNumPag", query = "SELECT l FROM Libros l WHERE l.numPag = :numPag")
    , @NamedQuery(name = "Libros.findByPrecio", query = "SELECT l FROM Libros l WHERE l.precio = :precio")
    , @NamedQuery(name = "Libros.findByA\u00f1oPublicado", query = "SELECT l FROM Libros l WHERE l.a\u00f1oPublicado = :a\u00f1oPublicado")
    , @NamedQuery(name = "Libros.findByCantidadLibros", query = "SELECT l FROM Libros l WHERE l.cantidadLibros = :cantidadLibros")})
public class Libros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_libro")
    private Integer idLibro;
    @Basic(optional = false)
    @Column(name = "ISBN")
    private String isbn;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "num_pag")
    private int numPag;
    @Basic(optional = false)
    @Column(name = "precio")
    private int precio;
    @Column(name = "a\u00f1o_publicado")
    @Temporal(TemporalType.DATE)
    private Date añoPublicado;
    @Column(name = "cantidad_libros")
    private String cantidadLibros;
    @ManyToMany(mappedBy = "librosList")
    private List<Idiomas> idiomasList;
    @ManyToMany(mappedBy = "librosList")
    private List<Autores> autoresList;
    @ManyToMany(mappedBy = "librosList")
    private List<Categorias> categoriasList;
    @JoinColumn(name = "editoriales_Id_editorial", referencedColumnName = "Id_editorial")
    @ManyToOne(optional = false)
    private Editoriales editorialesIdeditorial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libros")
    private List<EjemplarLibros> ejemplarLibrosList;

    public Libros() {
    }

    public Libros(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Libros(Integer idLibro, String isbn, String titulo, int numPag, int precio) {
        this.idLibro = idLibro;
        this.isbn = isbn;
        this.titulo = titulo;
        this.numPag = numPag;
        this.precio = precio;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Date getAñoPublicado() {
        return añoPublicado;
    }

    public void setAñoPublicado(Date añoPublicado) {
        this.añoPublicado = añoPublicado;
    }

    public String getCantidadLibros() {
        return cantidadLibros;
    }

    public void setCantidadLibros(String cantidadLibros) {
        this.cantidadLibros = cantidadLibros;
    }

    @XmlTransient
    public List<Idiomas> getIdiomasList() {
        return idiomasList;
    }

    public void setIdiomasList(List<Idiomas> idiomasList) {
        this.idiomasList = idiomasList;
    }

    @XmlTransient
    public List<Autores> getAutoresList() {
        return autoresList;
    }

    public void setAutoresList(List<Autores> autoresList) {
        this.autoresList = autoresList;
    }

    @XmlTransient
    public List<Categorias> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<Categorias> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public Editoriales getEditorialesIdeditorial() {
        return editorialesIdeditorial;
    }

    public void setEditorialesIdeditorial(Editoriales editorialesIdeditorial) {
        this.editorialesIdeditorial = editorialesIdeditorial;
    }

    @XmlTransient
    public List<EjemplarLibros> getEjemplarLibrosList() {
        return ejemplarLibrosList;
    }

    public void setEjemplarLibrosList(List<EjemplarLibros> ejemplarLibrosList) {
        this.ejemplarLibrosList = ejemplarLibrosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLibro != null ? idLibro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libros)) {
            return false;
        }
        Libros other = (Libros) object;
        if ((this.idLibro == null && other.idLibro != null) || (this.idLibro != null && !this.idLibro.equals(other.idLibro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Libros[ idLibro=" + idLibro + " ]";
    }
    
}
