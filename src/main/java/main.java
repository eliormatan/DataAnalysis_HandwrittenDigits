import com.sun.source.tree.BinaryTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class main {
    private  final String COMMA_DELIMITER = ",";

    private static List<Integer> getRecordFromLine(String line) {
        List<Integer> values = new ArrayList<Integer>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.parseInt(rowScanner.next()));
            }
        }
        return values;
    }

    private static List<List<Integer>> getExamples(String fileName) throws FileNotFoundException {
        List<List<Integer>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        catch(FileNotFoundException e){
                e.printStackTrace();
            }
        return records;
        }

        public static int [] getLabels(List<List<Integer>> examples){
          int [] histograma = new int[10];
          for(List<Integer> e : examples){
              histograma[e.get(0)]++;
          }
          return histograma;
        }
        public static int getMaxLabel(int [] histograma){
            int max = -1;
            int value = -1;
            for(int i=0;i<10;i++) {
                if (histograma[i] > max) {
                    max = histograma[i];
                    value = i;
                }
            }
            return value;
    }
       public static int calcEntropy(cond check){
        int [] labels = getLabels(check.getExamples());
        int size = check.getExamples().size();
        int result = 0;
        for(int i=0;i<10;i++){
            if(labels[i]>0){
                result+=(labels[i]/size)*Math.log(size/labels[i]);
            }
        }
        return result;
    }
        public static void main(String[] args) throws IOException {
            Logger logger = Logger.getLogger(main.class.getName());
            FileHandler fileHandler = new FileHandler(logger.getName() + ".txt");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.info("start time");
            List<List<Integer>> examples = getExamples(args[3]);
            int opt = Integer.parseInt(args[0]);
            int p = Integer.parseInt(args[1]);
            int l = Integer.parseInt(args[2]);
            Set<Integer> c = new HashSet<Integer>();
            for(int i=0;i<28*28;i++){
                c.add(i);
            }
            int t=1;
            for(int i=0;i<=l;i++){
                checkTree check = new checkTree();
                check.setRoot(new condNode(new cond(examples)));
                check.getRoot().getCond().setValue(getMaxLabel(getLabels(examples)));
                List<condNode> leaves = new ArrayList<>();
                leaves.add(check.getRoot());
                System.out.println("this is the label: "+check.getRoot().getCond().getValue());
              for(int j=1;j<=t;j++){

              }
              t=t*2;
            }
            logger.info("end time");
    }
}

