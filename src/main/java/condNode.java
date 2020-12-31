import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class condNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double LOG_OF_2 = Math.log(2.0);
    private cond condition;
    private condNode left, right;   //left=no,right=yes
    private int label, N,Na,Nb;
    private double entropy;
    private double maxIG;
    private int [] labels,labelsA,labelsB;
    private int [] examplesArrivedSoFar;

    public condNode(int [] examples, int [] labels) {
        this.examplesArrivedSoFar = examples;
        this.right = null;
        this.left = null;
        this.N = examplesArrivedSoFar.length;
        this.labels = labels;
        this.label = getMaxLabel();
        this.entropy = calcEntropy(this.labels, this.N);
        this.maxIG =0.0;
        this.Na=0;
        this.Nb=0;
        this.labelsA = new int[Na];
        this.labelsA = new int[Nb];
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
    public double calcEntropy(int [] labels, int size){
        double entropy = 0.0;
        if(size==0)
            return 0.0;
        for (int i = 0; i < 10; i++) {
            if (labels[i] > 0) {
                entropy += ((double) labels[i] /(double)size) * (Math.log((double)size/ (double)labels[i])/LOG_OF_2);
            }
        }
        return entropy;
    }
    public void calcMaxIG(List<cond> condList){
        if(N==0) {
            this.maxIG=0.0;
            this.condition= condList.get((int)(Math.random()*condList.size()));
            return;
        }
        for(cond x: condList){
            int Numa = 0;
            int Numb = 0;
            int [] labelsA = new int[10];
            int [] labelsB = new int[10];
            for(int i=0;i<N;i++){
                if(x.getCondAns(examplesArrivedSoFar[i])){
                    labelsA[learntree.examplesLabels[examplesArrivedSoFar[i]]]++;
                    Numa++;
                }
                else{
                    labelsB[learntree.examplesLabels[examplesArrivedSoFar[i]]]++;
                    Numb++;
                }
            }
            if(Numa==0 || Numb==0)
                continue;
            double entropyA = calcEntropy(labelsA, Numa);
            double entropyB = calcEntropy(labelsB, Numb);
          double Hx = ((double)Numa/(double)N)*entropyA+((double)Numb/(double)N)*entropyB;
          double IG = this.entropy-Hx;
          if(IG>this.maxIG){
              this.maxIG=IG;
              this.condition=x;
              this.Na=Numa;
              this.Nb=Numb;
              this.labelsA=labelsA;
              this.labelsB=labelsB;
          }
        }
        this.maxIG=this.maxIG*(double)N;
    }

    public void addLeafes(){
        int [] examplesLa = new int[Na];
        int [] examplesLb = new int[Nb];
        int countA = 0;
        int countB = 0;
        for(int i=0;i<N;i++){
            if(condition.getCondAns(examplesArrivedSoFar[i])){
                examplesLa[countA] = examplesArrivedSoFar[i];
                countA++;
            }
            else{
                examplesLb[countB] = examplesArrivedSoFar[i];
                countB++;
            }
        }
        this.right=new condNode(examplesLa,this.labelsA);
        this.left=new condNode(examplesLb,this.labelsB);
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


    public int[] getExamplesArrivedSoFar() {
        return examplesArrivedSoFar;
    }

    public void setExamplesArrivedSoFar(int[] examplesArrivedSoFar) {
        this.examplesArrivedSoFar = examplesArrivedSoFar;
    }

    public void setLabels(int[] labels) {
        this.labels = labels;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }
}
