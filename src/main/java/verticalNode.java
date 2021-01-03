public class verticalNode extends cond{
    private String type;
    private int dark;

    public verticalNode(int index,String type,int dark) {
        this.setIndex(index);
        this.type=type;
        this.dark=dark;
    }

    @Override
    public boolean checkCond(int[] pixel) {


        int pixels = 0;
//        int darkLine = 4;

        if(this.type=="col") {
            int col = this.getIndex() % 28;
            for (int j = 0; j < 28; j++) {
                if (pixel[28 * j + col] > 0)
                    pixels++;

                if (pixels >= dark)
                    return true;
            }
        }
        else{
            int row = this.getIndex() / 28;
            for (int j = 0; j < 28; j++) {
                if (pixel[28 * row + j] > 0)
                    pixels++;

                if (pixels >= dark)
                    return true;
            }
        }

        return false;
    }
}
