public class cubeCond extends cond {
    private int blockSize;
    public cubeCond(int index,int blockSize) {
        this.setIndex(index);
        this.blockSize=blockSize;
    }

    @Override
    //start=1
//        end=4
//        1     2     3
//        28*i+j=29=2  30=2  31=3       32 , 33 , 34
//        57=3    2=    3=              60 , 61 , 62
    //                              88 , 89 , 90
    public boolean checkCond(int[] pixel) {
    int pixels = 0;
//    int dark = 6;
//    int blockSize = 5;
    int dark=blockSize+1;
    int row=this.getIndex()/28;
    int col=this.getIndex()%28;

        for (int i = row; i < row + blockSize; i++) {
        for (int j = col; j < col + blockSize; j++) {
            if (pixel[28*i+j] > 0)
                pixels++;

            if (pixels >= dark)
                return true;
        }
    }
        return false;
}
}
