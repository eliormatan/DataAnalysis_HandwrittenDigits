import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class learntree {

    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(learntree.class.getName());
        FileHandler fileHandler = new FileHandler(logger.getName() + ".txt");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        logger.info("start time");
        List<Integer[]> examples = readCsv(args[3]);
        logger.info("finish loading");
        int opt = Integer.parseInt(args[0]);
        int P = Integer.parseInt(args[1]);
        int L = Integer.parseInt(args[2]);
        int validationNum = (P / 100) * examples.size();
        List<Integer[]> validationList = new ArrayList<>();
        for (int i = 0; i < validationNum; i++) {
            validationList.add(examples.remove((int) Math.random() * examples.size()));
        }
//        /*

        cond[] C = new cond[28 * 28];
        for (int i = 1; i <= 28 * 28; i++) {
            C[i - 1] = new cond(i);
        }
        logger.info("finish load conds");
        int T = 1;
        int maxT = 1;
        int maxTRate = -1;
        logger.info("finish first Node");
        condNode root = new condNode(examples);
        root.calcMaxIG(C);
        logger.info("finish first IG");
        PriorityQueue<condNode> leaves = new PriorityQueue<>((condNode x, condNode y) -> x.getMaxIG() > y.getMaxIG() ? 1 : -1);
        leaves.add(root);
        for (int i = 0; i <= L; i++) {
            for (int j = 1; j <= T; j++) {
                condNode maxLeaf = leaves.poll();
                //substitute max leaf in Node with 2 children
                maxLeaf.addLeafes();
                logger.info("split leaves");
                maxLeaf.getRight().calcMaxIG(C);
                maxLeaf.getLeft().calcMaxIG(C);
                logger.info("calc new leaves");
                leaves.add(maxLeaf.getRight());
                leaves.add(maxLeaf.getLeft());
                logger.info("finish iteration");
            }
            //todo: check success rate on validation set and save save value of T if it max

            int currRate = checkValidation(root, validationList);
            if (currRate > maxTRate) {
                maxTRate = currRate;
                maxT = T;
            }
            if (i != 0)
                T = T * 2;
        }
        //todo: create new tree based on best T including validation & training sets
        // ...
//*/
        examples.addAll(validationList);
        condNode newRoot = new condNode(examples);
        newRoot.calcMaxIG(C);
        PriorityQueue<condNode> newLeaves = new PriorityQueue<>((condNode x, condNode y) -> x.getMaxIG() > y.getMaxIG() ? 1 : -1);
        newLeaves.add(newRoot);
        for (int i = 1; i <= maxT; i++) {
            condNode maxLeaf = newLeaves.poll();
            //substitute max leaf in Node with 2 children
            logger.info("split leaves" + i);
            maxLeaf.addLeafes();
            maxLeaf.examplesArrivedSoFar = null;
            maxLeaf.labels = null;
            maxLeaf.getRight().calcMaxIG(C);
            maxLeaf.getLeft().calcMaxIG(C);
            logger.info("calc new leaves");
            newLeaves.add(maxLeaf.getRight());
            newLeaves.add(maxLeaf.getLeft());
            logger.info("finish iteration");
        }
        for (condNode l : newLeaves) {
            l.examplesArrivedSoFar = null;
            l.labels = null;
        }
        condNodeReaderWriter.writeCondNodeToFile(newRoot, args[4]);
        logger.info("end time");
    }

    public static int checkValidation(condNode root, List<Integer[]> validationList) {
        int counter = 0;
        for (Integer[] e : validationList) {
            if (checkExample(root, e))
                counter++;
        }
        return counter;
    }

    public static boolean checkExample(condNode root, Integer[] validationExample) {
        condNode checkRoot = root;
        while (checkRoot.getRight() != null && checkRoot.getLeft() != null) {
            if (checkRoot.getCondition().checkCond(validationExample))
                checkRoot = checkRoot.getRight();
            else
                checkRoot = checkRoot.getLeft();
        }
        return checkRoot.getLabel() == validationExample[0];
    }

    private static Integer[] getRecordFromLine(String line) {
        Integer[] values = new Integer[785];
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            int i = 0;
            while (rowScanner.hasNext()) {
                values[i] = Integer.parseInt(rowScanner.next());
                i++;
            }
        }
        return values;
    }

    private static List<Integer[]> getExamples(String fileName) throws FileNotFoundException {
        List<Integer[]> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
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


