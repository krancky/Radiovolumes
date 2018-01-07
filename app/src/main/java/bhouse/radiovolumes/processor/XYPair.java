package bhouse.radiovolumes.processor;

import java.io.Serializable;

/**
 * Created by kranck on 12/21/2017.
 */

public class XYPair<F, S> implements Serializable{
    private F first;
    private S second;

    public XYPair(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }
}
