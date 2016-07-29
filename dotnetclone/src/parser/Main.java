package parser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    /**
     * @param args
     * @throws Exception
     */
    static ArrayList<String> filesName = new ArrayList<String>();  // .exe files list
    static ArrayList<String> filesFullPath = new ArrayList<String>();
    static int nof = 0;
  

    public static void main(String[] args) throws Exception {
        try {
            getFiles("C:/Users/faa634/Documents/Visual Studio 2010/Projects/asxgui/"); // path of system to study
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        String command;
        String destinationPath ="C:/Users/faa634/Documents/ilfiles/" ;
        

        for ( int i=0;i<filesName.size();i++) {

             System.out.println(filesFullPath.get(i));
             command = "ildasm \""+filesFullPath.get(i)+"\"  /text /source";
             runCommandWithOutput(command, destinationPath+filesName.get(i)+".il");
        }

        //iman test
        //Farouq Test
     //   runCommandWithOutput("ildasm \"C:/Users/faa634/Documents/Visual Studio 2010/Projects/ConsoleApplication3/ConsoleApplication3/bin/Debug/ConsoleApplication3.exe \" /text /source", "C:/Users/faa634/Documents/farouq.txt");
    }

    private static void runCommandWithOutput(String command, String outputFile) throws Exception {
        ArrayList<String> output = new ArrayList<String>();
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);

        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        String line = null;

        while ((line = input.readLine()) != null) {
        //    System.out.println(line);
            output.add(line);
        }

        int exitVal = pr.waitFor();
        /* if(exitVal!=0)
        {
        System.out.println("Exited with error code "+exitVal +"    command: "+command);
        }*/
        writeToFile(output, outputFile);
    }

    private static void writeToFile(ArrayList<String> lines, String fileAddress) throws Exception {
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new FileWriter(fileAddress));

        for (String s : lines) {


            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();
    }


      private  static void getFiles(String path) throws IOException {
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();




        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                if (files.endsWith(".exe")){// || files.endsWith(".dll")) {
                    filesFullPath.add(path+files);
                    filesName.add( files);
                    nof++;
                }
            }
            if (listOfFiles[i].isDirectory()) {
                files = listOfFiles[i].getName();
                getFiles(path + files + "/");
            }
        }
    }
}
