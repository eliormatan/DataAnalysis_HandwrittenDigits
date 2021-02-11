public class verticalNode extends cond{
    private String type;
    private int w;

    public verticalNode(int unique, int index,String type,int w) {
        this.uniqueIndex=unique;
        this.setIndex(index);
        this.type=type;
        this.w=w;
    }

    @Override
    public boolean checkCond(int[] pixel) {


        int pixels = 0;

        if(this.type.equals("col")) {
            int col = this.uniqueIndex % 28;
            for (int j = 0; j < 28; j++) {
                if (pixel[28 * j + col] > 5)
                    pixels++;

                if (pixels >= w)
                    return true;
            }
        }
        else{
            int row = this.uniqueIndex / 28;
            for (int j = 0; j < 28; j++) {
                if (pixel[28 * row + j] > 5)
                    pixels++;

                if (pixels >= w)
                    return true;
            }
        }

        return false;
    }
}
