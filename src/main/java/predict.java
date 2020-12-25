import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class predict {
    private static Logger logger = Logger.getLogger(predict.class.getName());

    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler(logger.getName() + ".txt");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        logger.info("start time");
        if (args.length != 2) {
            System.err.println("number of arguments are not valid");
            System.exit(1);
        }
        condNode root = null;
        try {
            root = condNodeReaderWriter.readCondNodeFromFile(args[0]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (root == null) {
            System.err.println("node from input file is not valid");
            System.exit(1);
        }
        learntree.printNode(root);
        List<Integer[]> csvData = learntree.readCsv(args[1]);
        int [] histograma = getLabels(csvData);
        if (csvData == null) {
            System.err.println("test file is not valid");
            System.exit(1);
        }

        for(Integer[] testline: csvData)
            System.out.println(predictLabel(testline,root));

        for(int num: histograma)
            System.out.print(" "+num+" ");
        logger.info("end time");

    }

    public static int[] getLabels(List<Integer []> examples) {
        int[] histograma = new int[10];
        for (Integer [] e : examples) {
            histograma[e[0]]++;
        }
        return histograma;
    }

    private static int predictLabel(Integer[] testline,condNode node) {
//        logger.info("root label"+node.getLabel());
        while (!node.isLeaf()){
//            logger.info("curr cond index"+node.getCondition().getIndex());
            int [] test = new int[testline.length];
            for(int i=0;i<testline.length;i++){
                test[i]=testline[i];
            }
            boolean condResult=node.getCondition().checkCond(test);
            /*logger.info("condresult "+condResult);*/

            if(condResult) {
                node = node.getRight();
            }
            else {
                node = node.getLeft();
            }
//            logger.info("current label"+node.getLabel());
        }
        return node.getLabel();
    }
}
