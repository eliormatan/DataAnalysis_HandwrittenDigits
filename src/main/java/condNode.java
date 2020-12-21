import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class condNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double LOG_OF_2 = Math.log(2.0);
    cond condition;
    condNode left, right;   //left=yes,right=no
    int label, N;
    int [] labels;
    double entropy;
    double maxIG;
    List<Integer []> examplesArrivedSoFar;

    public condNode(List<Integer []> examplesArrivedSoFar) {
        this.examplesArrivedSoFar = examplesArrivedSoFar;
        this.right = null;
        this.left = null;
        this.N = examplesArrivedSoFar.size();
        this.labels = getLabels(this.examplesArrivedSoFar);
        this.label = getMaxLabel();
        this.entropy = calcEntropy(this.labels);
        this.maxIG =-1;

    }

    public condNode(condNode other){
        this.examplesArrivedSoFar = other.examplesArrivedSoFar;
        this.N = other.N;
        this.labels = other.labels;
        this.label = other.label;
        this.entropy = other.entropy;
        this.maxIG= other.maxIG;
        this.condition= other.condition;
        if(other.right==null)
            this.right=null;
        else
            this.right= new condNode(other.right);
        if(other.left==null)
            this.left=null;
        else
            this.left= new condNode(other.left);
    }

    public int[] getLabels(List<Integer []> examples) {
        int[] histograma = new int[10];
        for (Integer [] e : examples) {
            histograma[e[0]]++;
        }
        return histograma;
    }
    public int getMaxLabel() {
        int max = -1;
        int value = -1;
        for (int i = 0; i < 10; i++) {
            if (labels[i] > max) {
                max = labels[i];
                value = i;
            }
        }
        return value;
    }
    public double calcEntropy(int [] labels){
        double entropy = 0;
        for (int i = 0; i < 10; i++) {
            if (labels[i] > 0) {
                entropy += ((double) labels[i] / N) * Math.log(N/ labels[i]);
            }
        }
        return entropy;
    }
    public void calcMaxIG(cond [] C){
        cond bestCond = null;
        for(cond x: C){
          double Hx = Hx(x);
          double IG = this.entropy-Hx;
          if(IG>maxIG){
              this.maxIG=IG;
              this.condition=x;
          }
        }
        this.maxIG=this.maxIG*N;
    }
    private double Hx(cond x){
        int Na = 0;
        int Nb = 0;
        int [] labelsA = new int[10];
        int [] labelsB = new int[10];
        for(int i=0;i<N;i++){
          if(x.checkCond(examplesArrivedSoFar.get(i))){
              labelsA[examplesArrivedSoFar.get(i)[0]]++;
              Na++;
          }
          else{
              labelsB[examplesArrivedSoFar.get(i)[0]]++;
              Nb++;
          }
        }
        double entropyA = calcEntropy(labelsA);
        double entropyB = calcEntropy(labelsA);
        return ((double)Na/N)*entropyA+((double)Nb/N)*entropyB;
    }
    public void addLeafes(){
        List<Integer []> examplesLa = new ArrayList<>();
        List<Integer []> examplesLb = new ArrayList<>();
        for(int i=0;i<N;i++){
            if(condition.checkCond(examplesArrivedSoFar.get(i))){
                examplesLa.add(examplesArrivedSoFar.get(i));
            }
            else{
                examplesLb.add(examplesArrivedSoFar.get(i));
            }
        }
        this.right=new condNode(examplesLa);
        this.left=new condNode(examplesLb);
    }
    public double getEntropy() {
        return entropy;
    }

    public cond getCondition() {
        return condition;
    }

    public condNode getLeft() {
        return left;
    }

    public condNode getRight() {
        return right;
    }

    public int getLabel() {
        return label;
    }

    public int getN() {
        return N;
    }

    public double getMaxIG(){
        return this.maxIG;
    }


    public List<Integer []> getExamplesArrivedSoFar() {
        return examplesArrivedSoFar;
    }




    public boolean isLeaf() {
        return (left == null && right == null);
    }
}
