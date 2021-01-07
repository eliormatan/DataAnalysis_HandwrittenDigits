public class colCond extends cond {
    public colCond(int index) {
        this.setIndex(index);
    }

    @Override
    public boolean checkCond(int[] pixel) {

        int pixels = 0;
        int darkCol = 4;
        int col = this.getIndex() % 28;

        for (int j = 0; j < 28; j++) {
            if (pixel[28 * j + col] > 0)
                pixels++;

            if (pixels >= darkCol)
                return true;
        }

        return false;
    }
}

