package ro.duoline.promed.domains;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author I.T.W764
 */
@Entity
@Table(name = "SERVICES")
public class Service extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 333L;

    private String specification;
    private Double price;
    private int duration;

    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialization specialization;

    public Service() {
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        return Objects.equals(this.getId(), other.getId());
    }

}
