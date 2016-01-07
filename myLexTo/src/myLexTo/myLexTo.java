/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myLexTo;

import LexTo.LongLexTo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author phisan
 */
public class myLexTo {

    public static void main(String args[]) throws IOException {
        LongLexTo tokenizer = new LongLexTo(new File("/Users/phisan/ResearchCode/thaiword/myLexTo/src/myLexTo/lexitron.txt"));
        File unknownFile = new File("/Users/phisan/ResearchCode/thaiword/myLexTo/src/myLexTo/unknown.txt");
        if (unknownFile.exists()) {
            tokenizer.addDict(unknownFile);
        }

        String line;
        int begin, end;

        File inFile, outFile;
        FileReader fr;
        BufferedReader br;
        FileWriter fw;

        String baseDirectory = "/Users/phisan/Desktop/Test";
        String targetDirectory = baseDirectory+"/lexto";

        File theDir = new File(targetDirectory);
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                System.out.println(se);
            }
        }

        final File folder = new File(baseDirectory);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                System.out.println("Processing File : " + fileEntry.getName());
                inFile = new File(baseDirectory + "//" + fileEntry.getName());
                outFile = new File(targetDirectory + "//"+fileEntry.getName());

                fr = new FileReader(inFile);
                br = new BufferedReader(fr);
                fw = new FileWriter(outFile);

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0) {
                        tokenizer.wordInstance(line);
                        begin = tokenizer.first();
                        int i = 0;
                        while (tokenizer.hasNext()) {
                            end = tokenizer.next();
                            fw.write(line.substring(begin, end));
                            fw.write("|");
                            begin = end;
                        }
                        fw.write(System.lineSeparator());
                        fw.write(System.lineSeparator());
                    }
                }
                
                fr.close();
                fw.close();
            }
        }
    }
}