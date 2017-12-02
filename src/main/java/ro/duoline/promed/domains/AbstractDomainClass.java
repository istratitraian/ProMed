package ro.duoline.promed.domains;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.Version;

/**
 * @author I.T.W764
 */
@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date dateCreated;
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date lastUpdated;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

//    public Date getDateCreated() {
//        return dateCreated;
//    }
//
//    public Date getLastUpdated() {
//        return lastUpdated;
//    }
//
//    @PreUpdate
//    @PrePersist
//    public void updateTimeStamps() {
//        lastUpdated = new Date();
//        if (dateCreated == null) {
//            dateCreated = new Date();
//        }
//    }
}