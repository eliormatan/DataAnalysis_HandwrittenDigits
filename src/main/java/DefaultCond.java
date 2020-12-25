public class DefaultCond extends cond {

    public DefaultCond(int index){
        this.setIndex(index);
    }
    @Override
    public boolean checkCond(int[] pixel) {
        return pixel[this.getIndex()]>128;
    }
}
