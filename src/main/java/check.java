import java.util.Arrays;
import java.util.List;

public class check {
    public static int[] pixel255Counter, pixel784Counter;
    public static int[][] column28Counter, row28Counter;

    public static void statistics(
            List<Integer[]> examples) {

        Integer[][] e = new Integer[examples.size()][785];
        for (int i = 0; i < examples.size(); i++) {
            e[i] = examples.remove(0);
        }
        row28Counter = new int[e.length][28];
        column28Counter = new int[e.length][28];
        pixel784Counter = new int[785];
        pixel255Counter = new int[256];
        for (int i = 0; i < e.length; i++) {
            int rowCounter = 0;
            int colCounter = 0;
            for (int j = 0; j < e[i].length; j++) {
                if (j == 0) continue;
                if (e[i][j] != null) {
                    row28Counter[i][rowCounter % 28] += e[i][j];
                    column28Counter[i][colCounter % 28] += e[i][j];
                    pixel784Counter[j] += e[i][j];
                    pixel255Counter[e[i][j]]++;
                }
                colCounter++;
                if (colCounter % 28 == 0)
                    rowCounter++;
            }
        }
        System.out.println("------------28row-------------");
        int[] r = new int[28];
        int[] v = new int[28];
        for (int i = 0; i < row28Counter.length; i++) {
            for (int j = 0; j < row28Counter[i].length; j++) {
                v[j] = row28Counter[i][j];
            }
            int vv = findMaxIndex(v);
            r[vv]++;
//            System.out.println("sample " + i + " row "+vv+ " times " + row28Counter[i][vv]);
        }
        int[] rr = new int[28];
        for (int j = 0; j < r.length; j++) {
            rr[j] = r[j];
        }
        System.out.println(Arrays.toString(rr));

        for (int i = 0; i < r.length; i++) {
            int x = findMaxIndex(rr);
            System.out.println(i + "row " + x + " times " + r[x]);
        }

        System.out.println("------------28col-------------");

        for (int i = 0; i < column28Counter.length; i++) {
            for (int j = 0; j < column28Counter[i].length; j++) {
                v[j] = column28Counter[i][j];
            }
            int vv = findMaxIndex(v);
            r[vv]++;
//            System.out.println("sample " + i + " row "+vv+ " times " + row28Counter[i][vv]);
        }
        for (int j = 0; j < r.length; j++) {
            rr[j] = r[j];
        }
        System.out.println(Arrays.toString(rr));

        for (int i = 0; i < r.length; i++) {
            int x = findMaxIndex(rr);
            System.out.println(i + "row " + x + " times " + r[x]);
        }

        System.out.println("------------255pixel-------------");
//        System.out.println(Arrays.toString(pixel255Counter));
        int[] t1 = new int[pixel255Counter.length];
        int[] t2 = new int[pixel784Counter.length];
        for (int i = 0; i < pixel255Counter.length; i++) {
            t1[i] = pixel255Counter[i];
        }
        for (int i = 0; i < pixel784Counter.length; i++) {
            t2[i] = pixel784Counter[i];
        }


        for (int i = 0; i < 28; i++) {
            int a = findMaxIndex(t1);
            System.out.println("pixel num: " + a + " pixel occur " + pixel255Counter[a] + " ");
        }

        System.out.println("------------784col-------------");

        for (int i = 0; i < 28; i++) {
            int a = findMaxIndex(t2);
            System.out.println("pixel num: " + a + " pixel occur " + pixel784Counter[a] + " ");
        }

    }

    public static int findMaxIndex(int[] l) {
        int max = -1;
        int index = -1;
        for (int i = 0; i < l.length; i++) {
//            if (i == 0) continue;
            if (l[i] > max) {
                max = l[i];
                index = i;
            }
        }
        l[index] = -1;
        return index;
    }

}
