
package uk.co.o2.soa.subscriberdata_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingProfileType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BillingProfileType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CPW"/>
 *     &lt;enumeration value="CUK"/>
 *     &lt;enumeration value="Prepay"/>
 *     &lt;enumeration value="DISE"/>
 *     &lt;enumeration value="Unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BillingProfileType")
@XmlEnum
public enum BillingProfileType {

    CPW("CPW"),
    CUK("CUK"),
    @XmlEnumValue("Prepay")
    PREPAY("Prepay"),
    DISE("DISE"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");
    private final String value;

    BillingProfileType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillingProfileType fromValue(String v) {
        for (BillingProfileType c: BillingProfileType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
