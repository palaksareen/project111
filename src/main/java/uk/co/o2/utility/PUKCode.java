package uk.co.o2.utility;

public class PUKCode {
    private String pukCode;
    private long accesstime;

    public long getAccesstime() {
        return this.accesstime;
    }

    public void setAccesstime(long accesstime) {
        this.accesstime = accesstime;
    }

    public PUKCode(String pukCode) {
        this.pukCode = pukCode;
    }

    public PUKCode() {
    }

    public String getPukCode() {
        return this.pukCode;
    }

    public void setPukCode(String pukCode) {
        this.pukCode = pukCode;
    }
}
