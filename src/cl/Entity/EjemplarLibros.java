/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ejemplar_libros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EjemplarLibros.findAll", query = "SELECT e FROM EjemplarLibros e")
    , @NamedQuery(name = "EjemplarLibros.findByNSerie", query = "SELECT e FROM EjemplarLibros e WHERE e.ejemplarLibrosPK.nSerie = :nSerie")
    , @NamedQuery(name = "EjemplarLibros.findByLibrosIdLibro", query = "SELECT e FROM EjemplarLibros e WHERE e.ejemplarLibrosPK.librosIdLibro = :librosIdLibro")
    , @NamedQuery(name = "EjemplarLibros.findByEstadosIdEstado", query = "SELECT e FROM EjemplarLibros e WHERE e.ejemplarLibrosPK.estadosIdEstado = :estadosIdEstado")})
public class EjemplarLibros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EjemplarLibrosPK ejemplarLibrosPK;
    @JoinTable(name = "arriendos_has_ejemplar_libros", joinColumns = {
        @JoinColumn(name = "Ejemplar_Libros_n_serie", referencedColumnName = "n_serie")
        , @JoinColumn(name = "Ejemplar_Libros_libros_id_libro", referencedColumnName = "libros_id_libro")
        , @JoinColumn(name = "Ejemplar_Libros_estados_id_estado", referencedColumnName = "estados_id_estado")}, inverseJoinColumns = {
        @JoinColumn(name = "arriendos_id_arriendo", referencedColumnName = "id_arriendo")})
    @ManyToMany
    private List<Arriendos> arriendosList;
    @JoinTable(name = "boletas_has_ejemplar_libros", joinColumns = {
        @JoinColumn(name = "Ejemplar_Libros_n_serie", referencedColumnName = "n_serie")
        , @JoinColumn(name = "Ejemplar_Libros_libros_id_libro", referencedColumnName = "libros_id_libro")
        , @JoinColumn(name = "Ejemplar_Libros_estados_id_estado", referencedColumnName = "estados_id_estado")}, inverseJoinColumns = {
        @JoinColumn(name = "boletas_folio_boleta", referencedColumnName = "folio_boleta")})
    @ManyToMany
    private List<Boletas> boletasList;
    @JoinTable(name = "facturas_has_ejemplar_libros", joinColumns = {
        @JoinColumn(name = "Ejemplar_Libros_n_serie", referencedColumnName = "n_serie")
        , @JoinColumn(name = "Ejemplar_Libros_libros_id_libro", referencedColumnName = "libros_id_libro")
        , @JoinColumn(name = "Ejemplar_Libros_estados_id_estado", referencedColumnName = "estados_id_estado")}, inverseJoinColumns = {
        @JoinColumn(name = "facturas_folio_factura", referencedColumnName = "folio_factura")})
    @ManyToMany
    private List<Facturas> facturasList;
    @JoinColumn(name = "estados_id_estado", referencedColumnName = "id_estado", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estados estados;
    @JoinColumn(name = "libros_id_libro", referencedColumnName = "id_libro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Libros libros;

    public EjemplarLibros() {
    }

    public EjemplarLibros(EjemplarLibrosPK ejemplarLibrosPK) {
        this.ejemplarLibrosPK = ejemplarLibrosPK;
    }

    public EjemplarLibros(int nSerie, int librosIdLibro, int estadosIdEstado) {
        this.ejemplarLibrosPK = new EjemplarLibrosPK(nSerie, librosIdLibro, estadosIdEstado);
    }

    public EjemplarLibrosPK getEjemplarLibrosPK() {
        return ejemplarLibrosPK;
    }

    public void setEjemplarLibrosPK(EjemplarLibrosPK ejemplarLibrosPK) {
        this.ejemplarLibrosPK = ejemplarLibrosPK;
    }

    @XmlTransient
    public List<Arriendos> getArriendosList() {
        return arriendosList;
    }

    public void setArriendosList(List<Arriendos> arriendosList) {
        this.arriendosList = arriendosList;
    }

    @XmlTransient
    public List<Boletas> getBoletasList() {
        return boletasList;
    }

    public void setBoletasList(List<Boletas> boletasList) {
        this.boletasList = boletasList;
    }

    @XmlTransient
    public List<Facturas> getFacturasList() {
        return facturasList;
    }

    public void setFacturasList(List<Facturas> facturasList) {
        this.facturasList = facturasList;
    }

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }

    public Libros getLibros() {
        return libros;
    }

    public void setLibros(Libros libros) {
        this.libros = libros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ejemplarLibrosPK != null ? ejemplarLibrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarLibros)) {
            return false;
        }
        EjemplarLibros other = (EjemplarLibros) object;
        if ((this.ejemplarLibrosPK == null && other.ejemplarLibrosPK != null) || (this.ejemplarLibrosPK != null && !this.ejemplarLibrosPK.equals(other.ejemplarLibrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.EjemplarLibros[ ejemplarLibrosPK=" + ejemplarLibrosPK + " ]";
    }
    
}
