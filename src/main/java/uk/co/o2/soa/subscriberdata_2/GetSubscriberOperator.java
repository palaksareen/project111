
package uk.co.o2.soa.subscriberdata_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * gets Operator for MSISDN
 * 
 * <p>Java class for getSubscriberOperator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSubscriberOperator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SubscriberID" type="{http://soa.o2.co.uk/coredata_1}msisdnType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSubscriberOperator", propOrder = {
    "subscriberID"
})
public class GetSubscriberOperator {

    @XmlElement(name = "SubscriberID", required = true)
    protected String subscriberID;

    /**
     * Gets the value of the subscriberID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriberID() {
        return subscriberID;
    }

    /**
     * Sets the value of the subscriberID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriberID(String value) {
        this.subscriberID = value;
    }

}
