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
@Table(name = "facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturas.findAll", query = "SELECT f FROM Facturas f")
    , @NamedQuery(name = "Facturas.findByFolioFactura", query = "SELECT f FROM Facturas f WHERE f.folioFactura = :folioFactura")
    , @NamedQuery(name = "Facturas.findByPrecioNeto", query = "SELECT f FROM Facturas f WHERE f.precioNeto = :precioNeto")
    , @NamedQuery(name = "Facturas.findByPrecioConIva", query = "SELECT f FROM Facturas f WHERE f.precioConIva = :precioConIva")
    , @NamedQuery(name = "Facturas.findByFechaCompra", query = "SELECT f FROM Facturas f WHERE f.fechaCompra = :fechaCompra")
    , @NamedQuery(name = "Facturas.findByHoraCompra", query = "SELECT f FROM Facturas f WHERE f.horaCompra = :horaCompra")})
public class Facturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folio_factura")
    private Integer folioFactura;
    @Basic(optional = false)
    @Column(name = "precio_neto")
    private int precioNeto;
    @Basic(optional = false)
    @Column(name = "precio_con_iva")
    private int precioConIva;
    @Basic(optional = false)
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @Column(name = "hora_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaCompra;
    @ManyToMany(mappedBy = "facturasList")
    private List<EjemplarLibros> ejemplarLibrosList;
    @JoinColumn(name = "_id_metodo_pago", referencedColumnName = "id_metodo_pago")
    @ManyToOne(optional = false)
    private MetodoPago idMetodoPago;
    @JoinColumn(name = "distribuidores_rut_distribuidor", referencedColumnName = "rut_distribuidor")
    @ManyToOne(optional = false)
    private Distribuidores distribuidoresRutDistribuidor;

    public Facturas() {
    }

    public Facturas(Integer folioFactura) {
        this.folioFactura = folioFactura;
    }

    public Facturas(Integer folioFactura, int precioNeto, int precioConIva, Date fechaCompra, Date horaCompra) {
        this.folioFactura = folioFactura;
        this.precioNeto = precioNeto;
        this.precioConIva = precioConIva;
        this.fechaCompra = fechaCompra;
        this.horaCompra = horaCompra;
    }

    public Integer getFolioFactura() {
        return folioFactura;
    }

    public void setFolioFactura(Integer folioFactura) {
        this.folioFactura = folioFactura;
    }

    public int getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(int precioNeto) {
        this.precioNeto = precioNeto;
    }

    public int getPrecioConIva() {
        return precioConIva;
    }

    public void setPrecioConIva(int precioConIva) {
        this.precioConIva = precioConIva;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(Date horaCompra) {
        this.horaCompra = horaCompra;
    }

    @XmlTransient
    public List<EjemplarLibros> getEjemplarLibrosList() {
        return ejemplarLibrosList;
    }

    public void setEjemplarLibrosList(List<EjemplarLibros> ejemplarLibrosList) {
        this.ejemplarLibrosList = ejemplarLibrosList;
    }

    public MetodoPago getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(MetodoPago idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public Distribuidores getDistribuidoresRutDistribuidor() {
        return distribuidoresRutDistribuidor;
    }

    public void setDistribuidoresRutDistribuidor(Distribuidores distribuidoresRutDistribuidor) {
        this.distribuidoresRutDistribuidor = distribuidoresRutDistribuidor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioFactura != null ? folioFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturas)) {
            return false;
        }
        Facturas other = (Facturas) object;
        if ((this.folioFactura == null && other.folioFactura != null) || (this.folioFactura != null && !this.folioFactura.equals(other.folioFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Facturas[ folioFactura=" + folioFactura + " ]";
    }
    
}
