import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class learntree {
    public static boolean[][] condAnswer;
    public static int[] examplesLabels, initExamples, initLabels, validationIndexArr, mergedExamples, mergedExamplesLabels;
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println("number of arguments are not valid");
            System.exit(1);
        }
        List<Integer[]> examples = readCsv(args[3]);

        if (examples == null) {
            System.err.println("Could not load file \"" + args[3] + "\".");
            System.exit(1);
        }
        int opt = Integer.parseInt(args[0]);
        int P = Integer.parseInt(args[1]);
        int L = Integer.parseInt(args[2]);
        int validationNum = (int) (((double) P * (double) examples.size()) / 100.0);
        int[][] examplesData = new int[examples.size() - validationNum][];
        for (int i = 0; i < examplesData.length; i++) {
            Integer[] data = examples.remove((int) (Math.random() * (double) examples.size()));
            examplesData[i] = new int[data.length];
            for (int j = 0; j < examplesData[i].length; j++) {
                examplesData[i][j] = data[j];
            }
        }
        int[][] validationArr = new int[validationNum][];
        for (int i = 0; i < validationNum; i++) {
            Integer[] data = examples.get(i);
            validationArr[i] = new int[data.length];
            for (int j = 0; j < validationArr[i].length; j++) {
                validationArr[i][j] = data[j];
            }
        }

        List<cond> condList = opt == 1 ? generateDefaultConds() : generateImprovedConds();
        init(examplesData, validationArr, condList);
        int T = 1;
        int maxT = 1;
        int maxTRate = -1;
        condNode root = new condNode(initExamples, initLabels);
        root.calcMaxIG(condList);
        PriorityQueue<condNode> leaves = new PriorityQueue<>((condNode x, condNode y) -> x.getMaxIG() < y.getMaxIG() ? 1 : -1);
        leaves.add(root);
        for (int i = 0; i <= L; i++) {
            for (int j = 1; j <= T; j++) {
                condNode maxLeaf = leaves.poll();
                if(maxLeaf==null)
                    break;
                if(maxLeaf.addLeafes()) {
                    maxLeaf.getRight().calcMaxIG(condList);
                    maxLeaf.getLeft().calcMaxIG(condList);
                    leaves.add(maxLeaf.getRight());
                    leaves.add(maxLeaf.getLeft());
                }
            }

            int currRate = checkValidation(root, validationIndexArr);
            if (currRate > maxTRate) {
                maxTRate = currRate;
                maxT = (int) Math.pow(2, i);
            }
            if (i != 0)
                T = T * 2;
        }

        mergeExamples(examplesData.length + validationNum);
        condNode newRoot = new condNode(mergedExamples, mergedExamplesLabels);
        newRoot.calcMaxIG(condList);
        PriorityQueue<condNode> newLeaves = new PriorityQueue<>((condNode x, condNode y) -> x.getMaxIG() < y.getMaxIG() ? 1 : -1);
        newLeaves.add(newRoot);

        for (int i = 1; i <= maxT; i++) {
            condNode maxLeaf = newLeaves.poll();
            if(maxLeaf==null)
                break;
            if(maxLeaf.addLeafes()) {
                maxLeaf.getRight().calcMaxIG(condList);
                maxLeaf.getLeft().calcMaxIG(condList);
                newLeaves.add(maxLeaf.getRight());
                newLeaves.add(maxLeaf.getLeft());
            }
            maxLeaf.setExamplesArrivedSoFar(null);
            maxLeaf.setLabels(null);
        }


        for (condNode l : newLeaves) {
            l.setExamplesArrivedSoFar(null);
            l.setLabels(null);
        }
        try {
            condNodeReaderWriter.writeCondNodeToFile(newRoot, args[4]);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("num: " + mergedExamples.length);
        System.out.println("error: " + (int) (100 - ((double) checkValidation(newRoot, mergedExamples) / (double) (mergedExamples.length) * 100)));
        System.out.println("size: " + maxT);
    }

    public static void mergeExamples(int size) {
        mergedExamples = new int[size];
        mergedExamplesLabels = new int[10];
        for (int i = 0; i < size; i++) {
            mergedExamples[i] = i;
            mergedExamplesLabels[examplesLabels[mergedExamples[i]]]++;
        }
    }

    public static int checkValidation(condNode root, int[] validationIndexArr) {
        int counter = 0;
        for (int e : validationIndexArr) {
            if (checkExample(root, e))
                counter++;
        }
        return counter;
    }

    public static boolean checkExample(condNode root, int validationIndex) {
        condNode checkRoot = root;
        while (checkRoot.getRight() != null && checkRoot.getLeft() != null) {
            if (checkRoot.getCondition().getCondAns(validationIndex))
                checkRoot = checkRoot.getRight();
            else
                checkRoot = checkRoot.getLeft();
        }
        return checkRoot.getLabel() == examplesLabels[validationIndex];
    }

    public static void init(int[][] examples, int[][] validationArr, List<cond> condList) {
        condAnswer = new boolean[examples.length + validationArr.length][condList.size()];
        examplesLabels = new int[examples.length + validationArr.length];
        initExamples = new int[examples.length];
        initLabels = new int[10];
        validationIndexArr = new int[validationArr.length];
        for (int i = 0; i < examples.length; i++) {
            boolean[] exampleAns = condAnswer[i];
            initExamples[i] = i;
            examplesLabels[i] = examples[i][0];
            initLabels[examplesLabels[i]]++;
            for (int j = 0; j < condList.size(); j++) {
                exampleAns[j] = condList.get(j).checkCond(examples[i]);
            }
        }
        for (int i = 0; i < validationArr.length; i++) {
            boolean[] exampleAns = condAnswer[examples.length + i];
            examplesLabels[examples.length + i] = validationArr[i][0];
            validationIndexArr[i] = examples.length + i;
            for (int j = 0; j < condList.size(); j++) {
                exampleAns[j] = condList.get(j).checkCond(validationArr[i]);
            }
        }
        for(int i=0;i<condList.size();i++){
            condList.get(i).setIndex(i+1);
        }
    }

    public static List<cond> generateDefaultConds() {
        List<cond> ans = new ArrayList<>();
        for (int i = 1; i <= 28 * 28; i++) {
            ans.add(new DefaultCond(i));
        }
        return ans;
    }

    public static List<cond> generateImprovedConds() {
        List<cond> ans = new ArrayList<>();
        int colCounter = 0;
        int rowCounter = 0;

        for (int i = 1; i <= 28 * 28; i++) {
            int row = rowCounter % 28;
            int col = colCounter % 28;
            ans.add(new ImprovedCond(i-1,i));
            colCounter++;
            if (colCounter % 28 == 0)
                rowCounter++;

                if (row >= 0 && row < 28-2 && col >= 0 && col < 28-6)
                    ans.add(new blockCond(i-1,i-1,2,6,3));

        }
        for(int i=1;i<=28;i++){
                ans.add(new verticalNode(i-1,i,"row",1));
                ans.add(new verticalNode(i-1,i,"col",1));
        }

        return ans;
    }

    public static ArrayList<Integer[]> readCsv(String path) {
        ArrayList<Integer[]> ret = new ArrayList<Integer[]>();
        ret.ensureCapacity(1 << 16);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] sVals = line.split(",");
                ret.add(new Integer[sVals.length]);

                for (int j = 0; j < sVals.length; j++)
                    ret.get(ret.size() - 1)[j] = Integer.parseInt(sVals[j]);
            }
        } catch (Exception e) {
            return null;
        }

        return ret;
    }
}



