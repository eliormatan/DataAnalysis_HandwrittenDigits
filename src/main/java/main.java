import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public static void main(String[] args) throws FileNotFoundException {
        /*List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader("mnist_train.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        for(List<String> s: records){
            System.out.println(s.toString());
        }*/
        List<List<Integer>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("mnist_train.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        for(List<Integer> s: records){
            System.out.println(s.toString());
        }
    }
}

