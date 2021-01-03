public class blockCond extends cond {
    private String type;
    private int size;
    public blockCond(int index,String type,int size) {
        this.setIndex(index);
        this.type=type;
        this.size=size;
    }

    @Override
    public boolean checkCond(int[] pixel) {
        //start=1
//        end=4
//        1     2     3
//        28*i+j=29=2  30=2  31=3       32 , 33 , 34
//        57=3    2=    3=              60 , 61 , 62
        //                              88 , 89 , 90

        int pixels = 0;
        int dark =4;
        int rowSize = type=="r" ? size : size+4;
        int colSize = type=="r" ? size+4 : size;
        int row=this.getIndex()/28;
        int col=this.getIndex()%28;

        for (int i = row; i <= row + rowSize; i++) {
            for (int j = col; j <= col + colSize; j++) {
                if (pixel[28*i+j] > 0)
                    pixels++;

                if (pixels >= dark)
                    return true;
            }
        }
        return false;
    }
}

