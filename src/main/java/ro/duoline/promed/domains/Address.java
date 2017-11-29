package ro.duoline.promed.domains;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * @author I.T.W764
 */
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    public Address() {
    }

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    private String zipCode;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
