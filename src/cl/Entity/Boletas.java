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
@Table(name = "boletas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boletas.findAll", query = "SELECT b FROM Boletas b")
    , @NamedQuery(name = "Boletas.findByPrecioNeto", query = "SELECT b FROM Boletas b WHERE b.precioNeto = :precioNeto")
    , @NamedQuery(name = "Boletas.findByPrecioConIva", query = "SELECT b FROM Boletas b WHERE b.precioConIva = :precioConIva")
    , @NamedQuery(name = "Boletas.findByFechaVenta", query = "SELECT b FROM Boletas b WHERE b.fechaVenta = :fechaVenta")
    , @NamedQuery(name = "Boletas.findByHoraVenta", query = "SELECT b FROM Boletas b WHERE b.horaVenta = :horaVenta")
    , @NamedQuery(name = "Boletas.findByFolioBoleta", query = "SELECT b FROM Boletas b WHERE b.folioBoleta = :folioBoleta")})
public class Boletas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "precio_neto")
    private int precioNeto;
    @Basic(optional = false)
    @Column(name = "precio_con_iva")
    private int precioConIva;
    @Basic(optional = false)
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @Column(name = "hora_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaVenta;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folio_boleta")
    private Integer folioBoleta;
    @ManyToMany(mappedBy = "boletasList")
    private List<EjemplarLibros> ejemplarLibrosList;
    @JoinColumn(name = "clientes_rut_cliente", referencedColumnName = "rut_cliente")
    @ManyToOne(optional = false)
    private Clientes clientesRutCliente;
    @JoinColumn(name = "metodo_pago_id_metodo_pago", referencedColumnName = "id_metodo_pago")
    @ManyToOne(optional = false)
    private MetodoPago metodoPagoIdMetodoPago;
    @JoinColumn(name = "trabajadores_rut_trabajador", referencedColumnName = "rut_trabajador")
    @ManyToOne(optional = false)
    private Trabajadores trabajadoresRutTrabajador;

    public Boletas() {
    }

    public Boletas(Integer folioBoleta) {
        this.folioBoleta = folioBoleta;
    }

    public Boletas(Integer folioBoleta, int precioNeto, int precioConIva, Date fechaVenta) {
        this.folioBoleta = folioBoleta;
        this.precioNeto = precioNeto;
        this.precioConIva = precioConIva;
        this.fechaVenta = fechaVenta;
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

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(Date horaVenta) {
        this.horaVenta = horaVenta;
    }

    public Integer getFolioBoleta() {
        return folioBoleta;
    }

    public void setFolioBoleta(Integer folioBoleta) {
        this.folioBoleta = folioBoleta;
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

    public MetodoPago getMetodoPagoIdMetodoPago() {
        return metodoPagoIdMetodoPago;
    }

    public void setMetodoPagoIdMetodoPago(MetodoPago metodoPagoIdMetodoPago) {
        this.metodoPagoIdMetodoPago = metodoPagoIdMetodoPago;
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
        hash += (folioBoleta != null ? folioBoleta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boletas)) {
            return false;
        }
        Boletas other = (Boletas) object;
        if ((this.folioBoleta == null && other.folioBoleta != null) || (this.folioBoleta != null && !this.folioBoleta.equals(other.folioBoleta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.Entity.Boletas[ folioBoleta=" + folioBoleta + " ]";
    }
    
}
