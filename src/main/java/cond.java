import java.io.Serializable;
import java.util.List;

public class cond implements Serializable {
    private static final long serialVersionUID = 1L;
    private int index;

    public cond(int index) {
        this.index = index;
    }

    public boolean checkCond(Integer [] pixel) {
        return pixel[this.index] > 128;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
