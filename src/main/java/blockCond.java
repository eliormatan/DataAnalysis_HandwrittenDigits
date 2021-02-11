public class blockCond extends cond {
    private int sizeRow;
    private int sizeCol;
    private int w;
    public blockCond(int unique, int index,int sizeRow,int sizeCol, int w) {
        this.uniqueIndex=unique;
        this.setIndex(index);
        this.sizeRow=sizeRow;
        this.sizeCol=sizeCol;
        this.w=w;
    }

    @Override
    public boolean checkCond(int[] pixel) {

        int pixels = 0;
        int row=this.uniqueIndex/28;
        int col=this.uniqueIndex%28;

        for (int i = row; i <= row + sizeRow; i++) {
            for (int j = col; j <= col + sizeCol; j++) {
                if (pixel[28*i+j] > 5)
                    pixels++;

                if (pixels >= w)
                    return true;
            }
        }
        return false;
    }
}

