public class ImprovedCond extends cond {

    public ImprovedCond(int unique, int index) {
        this.uniqueIndex=unique;
        this.setIndex(index);
    }

    @Override
    public boolean checkCond(int[] pixel) {
        return pixel[this.uniqueIndex]>5;
    }
}
