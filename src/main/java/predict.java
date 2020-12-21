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

        List<Integer[]> csvData = learntree.readCsv(args[1]);

        if (csvData == null) {
            System.err.println("test file is not valid");
            System.exit(1);
        }

        for(Integer[] testline: csvData)
            System.out.println(predictLabel(testline,root));

        logger.info("end time");

    }

    private static int predictLabel(Integer[] testline,condNode node) {
//        logger.info("root label"+node.getLabel());
        while (!node.isLeaf()){
//            logger.info("curr cond index"+node.getCondition().getIndex());
            boolean condResult=node.getCondition().checkCond(testline);
            logger.info("condresult "+condResult);

            if(condResult) {
                node = node.getLeft();
            }
            else {
                node = node.getRight();
            }
//            logger.info("current label"+node.getLabel());
        }
        return node.getLabel();
    }
}
