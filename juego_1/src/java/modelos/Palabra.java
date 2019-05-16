/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eliel
 */
@Entity
@Table(name = "PALABRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palabra.findAll", query = "SELECT p FROM Palabra p")
    , @NamedQuery(name = "Palabra.findByIdpalabra", query = "SELECT p FROM Palabra p WHERE p.idpalabra = :idpalabra")
    , @NamedQuery(name = "Palabra.findByPalabra", query = "SELECT p FROM Palabra p WHERE p.palabra = :palabra")
    , @NamedQuery(name = "Palabra.findByNivel", query = "SELECT p FROM Palabra p WHERE p.nivel = :nivel")})
public class Palabra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPALABRA")
    private Integer idpalabra;
    @Basic(optional = false)
    @Column(name = "PALABRA")
    private String palabra;
    @Basic(optional = false)
    @Column(name = "NIVEL")
    private int nivel;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tipo tipo;

    public Palabra() {
    }

    public Palabra(Integer idpalabra) {
        this.idpalabra = idpalabra;
    }

    public Palabra(Integer idpalabra, String palabra, int nivel) {
        this.idpalabra = idpalabra;
        this.palabra = palabra;
        this.nivel = nivel;
    }

    public Integer getIdpalabra() {
        return idpalabra;
    }

    public void setIdpalabra(Integer idpalabra) {
        this.idpalabra = idpalabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpalabra != null ? idpalabra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palabra)) {
            return false;
        }
        Palabra other = (Palabra) object;
        if ((this.idpalabra == null && other.idpalabra != null) || (this.idpalabra != null && !this.idpalabra.equals(other.idpalabra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Palabra[ idpalabra=" + idpalabra + " ]";
    }
    
}
