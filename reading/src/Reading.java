import java.io.*;
public class Reading {
   public static void main(String[] args) {
      String FILENAME = "in.txt";
      try {
         FileInputStream myInputStream = 
         new FileInputStream(FILENAME);
         InputStreamReader myReader = 
         new InputStreamReader(myInputStream);
         BufferedReader myBufferedReader = 
         new BufferedReader(myReader);
         
         while (myBufferedReader.ready()) {
             String line = myBufferedReader.readLine();
             System.out.println(line);
          }
          myBufferedReader.close();
       }
       catch (FileNotFoundException e) {
          System.out.print("File missing!");
       }
       catch (IOException e) {
          System.out.print("Error in reading!");
       }
    }
 }
