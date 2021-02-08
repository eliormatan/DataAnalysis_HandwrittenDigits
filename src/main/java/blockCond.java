public class blockCond extends cond {
    private int sizeRow;
    private int sizeCol;
    private int k;
    public blockCond(int unique, int index,int sizeRow,int sizeCol, int k) {
        this.uniqueIndex=unique;
        this.setIndex(index);
        this.sizeRow=sizeRow;
        this.sizeCol=sizeCol;
        this.k=k;
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
        int row=this.uniqueIndex/28;
        int col=this.uniqueIndex%28;

        for (int i = row; i <= row + sizeRow; i++) {
            for (int j = col; j <= col + sizeCol; j++) {
                if (pixel[28*i+j] > 0)
                    pixels++;

                if (pixels >= k)
                    return true;
            }
        }
        return false;
    }
}

