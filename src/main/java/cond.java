import java.util.ArrayList;
import java.util.List;

public class cond {
    private int index;
    private int value;
    private List<List<Integer>> examples;
    public cond(List<List<Integer>> examples){
        this.examples=examples;
        this.index=-1;
        this.value=-1;
    }
    public boolean checkCond(List<Integer> pixel){
        return pixel.get(this.index)>128;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public List<List<Integer>> getExamples(){
        return examples;
    }
}
