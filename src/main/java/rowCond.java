public class rowCond extends cond {
    public rowCond(int index) {
        this.setIndex(index);
    }

    @Override
    public boolean checkCond(int[] pixel) {

        int pixels = 0;
        int darkRow = 4;
        int row = this.getIndex() / 28;

        for (int j = 0; j < 28; j++) {
            if (pixel[28 * row + j] > 0)
                pixels++;

            if (pixels >= darkRow)
                return true;
        }

        return false;
    }
}

