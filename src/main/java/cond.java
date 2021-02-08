import java.io.Serializable;
import java.util.List;

public abstract class cond implements Serializable {
    private static final long serialVersionUID = 1L;

    private int index;
    public int uniqueIndex;

    public abstract boolean checkCond(int [] pixel);

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean getCondAns(int exampleIndex){
        return learntree.condAnswer[exampleIndex][this.index-1];
    }
}
