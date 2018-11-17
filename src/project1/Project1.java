/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Project1 {

    public static SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Date format creation

    public static void main(String[] args) {

        Date date = new Date();

        if (args.length != 2) { // Not correct number of arguments
            System.out.println("Please provide valid arguments");
            System.out.println("argument 1: path to text file");
            System.out.println("argument 2: command to apply <wc or find>");
        } else if (args.length == 2 && new File(args[0]).exists()) { // Correct number of arguments and also existing file

            String path = args[0];

            String filename = null;
            int z = path.lastIndexOf("\\");
            filename = path.substring(z + 1); // Indication of the filename

            String extension = null;
            int i = path.lastIndexOf(".");
            extension = path.substring(i + 1); // Indication of the file type

            if (args[1].equals("wc") && (extension.equals("txt"))) { // Argument "wc" for a text file
                fileCount(path);
            } else if (args[1].equals("find") && (extension.equals("txt"))) { // Argument "find" for a text file
                System.out.println(f.format(date) + " File " + path + " found!");
                System.out.print("Enter the word you wish to search in the file: ");
                String input = System.console().readLine();
                wordCount(input, path, filename);
            } else if (extension.equals("txt")) { // Wrong arguments for a text file
                System.out.println("File " + path + " found!");
                System.out.println("Argument " + args[0] + " is invalid. Only wc and find are supported");

            } else // Not a text file
            {
                System.out.println("File " + filename + " is not a text file!");
            }
        } else // Invalid file path
        {
            System.out.print(args[0] + " is not a valid file path");
        }
    }

    public static void wordCount(String input, String path, String filename) { // Method to count a specific word in a
        // text file

        String line = null;
        int wordcount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            Pattern p = Pattern.compile(input);

            while ((line = br.readLine()) != null) {

                Matcher m = p.matcher(line);

                while (m.find()) {
                    wordcount++;
                }
            }

            PrintWriter output = null;
            Date date = new Date();

            System.out.println(f.format(date) + " Counting occurences of word " + input);
            System.out
                    .println(f.format(date) + " Count of word " + input + " finished. Occurences found: " + wordcount);

            output = new PrintWriter(new BufferedWriter(new FileWriter("wordcount.txt", true)));
            output.println(f.format(date) + " find " + filename + " " + input + ":" + wordcount + "\n");
            output.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void fileCount(String path) { // Method to count total words of a text file

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = null;
            int wordCount = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                } else {
                    String[] words = line.split("\\s+");

                    wordCount = wordCount + words.length;
                }
            }
            PrintWriter output = null;
            output = new PrintWriter(new BufferedWriter(new FileWriter("wordcount.txt", true)));

            Date date = new Date();

            System.out.println(f.format(date) + " File " + path + " found!");
            System.out.println(f.format(date) + " Total word count started");

            if (wordCount != 1) { // If there are 2 or more words in the file

                System.out.println(f.format(date) + " Word count finished. Counted " + wordCount + " words");

            } else { // If there is only 1 word in the file

                System.out.println(f.format(date) + " Word count finished. Counted " + wordCount + " word");
            }
            output.println(f.format(date) + " wc " + path + " " + wordCount + "\n");
            output.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
