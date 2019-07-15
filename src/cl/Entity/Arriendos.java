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
@Table(name = "arriendos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arriendos.findAll", query = "SELECT a FROM Arriendos a")
    , @NamedQuery(name = "Arriendos.findByCostoTotal", query = "SELECT a FROM Arriendos a WHERE a.costoTotal = :costoTotal")
    , @NamedQuery(name = "Arriendos.findByFechaArriendo", query = "SELECT a FROM Arriendos a WHERE a.fechaArriendo = :fechaArriendo")
    , @NamedQuery(name = "Arriendos.findByFechaDevoluci\u00f3nEstimada", query = "SELECT a FROM Arriendos a WHERE a.fechaDevoluci\u00f3nEstimada = :fechaDevoluci\u00f3nEstimada")
    , @NamedQuery(name = "Arriendos.findByFechaEntregaReal", query = "SELECT a FROM Arriendos a WHERE a.fechaEntregaReal = :fechaEntregaReal")
    , @NamedQuery(name = "Arriendos.findByD\u00edasDeRetraso", query = "SELECT a FROM Arriendos a WHERE a.d\u00edasDeRetraso = :d\u00edasDeRetraso")
    , @NamedQuery(name = "Arriendos.findByCostoArriendo", query = "SELECT a FROM Arriendos a WHERE a.costoArriendo = :costoArriendo")
    , @NamedQuery(name = "Arriendos.findByIdArriendo", query = "SELECT a FROM Arriendos a WHERE a.idArriendo = :idArriendo")})
public class Arriendos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "costo_total")
    private int costoTotal;
    @Basic(optional = false)
    @Column(name = "fecha_arriendo")
    @Temporal(TemporalType.DATE)
    private Date fechaArriendo;
    @Basic(optional = false)
    @Column(name = "fecha_devoluci\u00f3n_estimada")
    @Temporal(TemporalType.DATE)
    private Date fechaDevoluciónEstimada;
    @Basic(optional = false)
    @Column(name = "fecha_entrega_real")
    @Temporal(TemporalType.DATE)
    private Date fechaEntregaReal;
    @Column(name = "d\u00edas_de_retraso")
    private Integer díasDeRetraso;
    @Basic(optional = false)
    @Column(name = "costo_arriendo")
    private int costoArriendo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_arriendo")
    private Integer idArriendo;
    @ManyToMany(mappedBy = "arriendosList")
    private List<EjemplarLibros> ejemplarLibrosList;
    @JoinColumn(name = "clientes_rut_cliente", referencedColumnName = "rut_cliente")
    @ManyToOne(optional = false)
    private Clientes clientesRutCliente;
    @JoinColumn(name = "trabajadores_rut_trabajador", referencedColumnName = "rut_trabajador")
    @ManyToOne(optional = false)
    private Trabajadores trabajadoresRutTrabajador;

    public Arriendos() {
    }

    public Arriendos(Integer idArriendo) {
        this.idArriendo = idArriendo;
    }

    public Arriendos(Integer idArriendo, int costoTotal, Date fechaArriendo, Date fechaDevoluciónEstimada, Date fechaEntregaReal, int costoArriendo) {
        this.idArriendo = idArriendo;
        this.costoTotal = costoTotal;
        this.fechaArriendo = fechaArriendo;
        this.fechaDevoluciónEstimada = fechaDevoluciónEstimada;
        this.fechaEntregaReal = fechaEntregaReal;
        this.costoArriendo = costoArriendo;
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Date getFechaArriendo() {
        return fechaArriendo;
    }

    public void setFechaArriendo(Date fechaArriendo) {
        this.fechaArriendo = fechaArriendo;
    }

    public Date getFechaDevoluciónEstimada() {
        return fechaDevoluciónEstimada;
    }

    public void setFechaDevoluciónEstimada(Date fechaDevoluciónEstimada) {
        this.fechaDevoluciónEstimada = fechaDevoluciónEstimada;
    }

    public Date getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(Date fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public Integer getDíasDeRetraso() {
        return díasDeRetraso;
    }

    public void setDíasDeRetraso(Integer díasDeRetraso) {
        this.díasDeRetraso = díasDeRetraso;
    }

    public int getCostoArriendo() {
        return costoArriendo;
    }

    public void setCostoArriendo(int costoArriendo) {
        this.costoArriendo = costoArriendo;
    }

    public Integer getIdArriendo() {
        return idArriendo;
    }

    public void setIdArriendo(Integer idArriendo) {
        this.idArriendo = idArriendo;
    }

    @XmlTransient
    public List<EjemplarLibros> getEjemplarLibrosList() {
        return ejemplarLibrosList;
    }

    public void setEjemplarLibrosList(List<EjemplarLibros> ejemplarLibrosList) {
        this.ejemplarLibrosList = ejemplarLibrosList;
    }

    public Clientes getClientesRutCliente() {
        return clientesRutCliente;
    }

    public void setClientesRutCliente(Clientes clientesRutCliente) {
        this.clientesRutCliente = clientesRutCliente;
    }

    public Trabajadores getTrabajadoresRutTrabajador() {
        return trabajadoresRutTrabajador;
    }

    public void setTrabajadoresRutTrabajador(Trabajadores trabajadoresRutTrabajador) {
        this.trabajadoresRutTrabajador = trabajadoresRutTrabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArriendo != null ? idArriendo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Arriendos)) {
            return false;
        }
        Arriendos other = (Arriendos) object;
        if ((this.idArriendo == null && other.idArriendo != null) || (this.idArriendo != null && !this.idArriendo.equals(other.idArriendo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Arriendos[ idArriendo=" + idArriendo + " ]";
    }
    
}
