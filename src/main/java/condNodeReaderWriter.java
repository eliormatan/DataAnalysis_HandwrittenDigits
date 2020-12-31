import java.io.*;

public class condNodeReaderWriter {
    public static void writeCondNodeToFile(condNode root, String fileName){
        if(root == null || !(root instanceof Serializable)) {
            System.err.println("the instance root is null or not Serializable");
            System.exit(1);
        }

        //delete manually if already exists
        File file = new File(fileName);
        if(file.exists())
            file.delete();

        //write to file
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(root);
            out.close();
            fileOut.close();
        }
        catch ( IOException e) {
            System.err.println("IOException: " + e.getMessage());
            System.exit(1);
        }
    }

    public static condNode readCondNodeFromFile(String fileName){
        condNode root = null;

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            root = (condNode)ois.readObject();
            ois.close();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return root;
    }
}
