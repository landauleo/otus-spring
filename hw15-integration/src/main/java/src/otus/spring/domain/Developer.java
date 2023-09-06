package src.otus.spring.domain;


public abstract class Developer {

    private final short iq;

    public Developer(short iq) {
        this.iq = iq;
    }

    public short getIq() {
        return iq;
    }

}
