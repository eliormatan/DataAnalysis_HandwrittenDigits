public class ImprovedCond extends cond {

    public ImprovedCond(int index) {
        this.setIndex(index);
    }

    @Override
    public boolean checkCond(int[] pixel) {
//        return pixel[this.getIndex()] > 0;
        return pixel[this.getIndex()]>0;
    }
}
