/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Leonardo
 */
@Embeddable
public class EjemplarLibrosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "n_serie")
    private int nSerie;
    @Basic(optional = false)
    @Column(name = "libros_id_libro")
    private int librosIdLibro;
    @Basic(optional = false)
    @Column(name = "estados_id_estado")
    private int estadosIdEstado;

    public EjemplarLibrosPK() {
    }

    public EjemplarLibrosPK(int nSerie, int librosIdLibro, int estadosIdEstado) {
        this.nSerie = nSerie;
        this.librosIdLibro = librosIdLibro;
        this.estadosIdEstado = estadosIdEstado;
    }

    public int getNSerie() {
        return nSerie;
    }

    public void setNSerie(int nSerie) {
        this.nSerie = nSerie;
    }

    public int getLibrosIdLibro() {
        return librosIdLibro;
    }

    public void setLibrosIdLibro(int librosIdLibro) {
        this.librosIdLibro = librosIdLibro;
    }

    public int getEstadosIdEstado() {
        return estadosIdEstado;
    }

    public void setEstadosIdEstado(int estadosIdEstado) {
        this.estadosIdEstado = estadosIdEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nSerie;
        hash += (int) librosIdLibro;
        hash += (int) estadosIdEstado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarLibrosPK)) {
            return false;
        }
        EjemplarLibrosPK other = (EjemplarLibrosPK) object;
        if (this.nSerie != other.nSerie) {
            return false;
        }
        if (this.librosIdLibro != other.librosIdLibro) {
            return false;
        }
        if (this.estadosIdEstado != other.estadosIdEstado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.EjemplarLibrosPK[ nSerie=" + nSerie + ", librosIdLibro=" + librosIdLibro + ", estadosIdEstado=" + estadosIdEstado + " ]";
    }
    
}
