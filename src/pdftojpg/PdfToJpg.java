/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftojpg;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
/**
 *
 * @author cle-135-nb
 */
public class PdfToJpg {

    /**
     * @param args the command line arguments
     */
    static void pageNumbers(String fileName){
        String Book = "";
        int[] arr = {0};
        String text = "";
        String[] segments = new String[2];
        //  String[] pageNo = new String[10];
        //  int[] page = new int[10];
        int lineNumber;
        try {
            FileReader readfile = new FileReader(fileName);
            BufferedReader readbuffer = new BufferedReader(readfile);
            text = readbuffer.readLine();
            for (lineNumber = 1; text != null; lineNumber++) {
                if (lineNumber >= 2) {

                    segments = text.split("\t");
                    text = readbuffer.readLine();
                    try{
                        Book = segments[0];
                        String[] pageNo = segments[1].split(",");
                        int[] page = new int[pageNo.length];
                        for(int i = 0; i < pageNo.length; i++){
                            if(pageNo[i].isEmpty()){
                                continue;
                            }
                            else{
                                page[i] = Integer.parseInt(pageNo[i]);
                            }
                        }
                        Extract_Images(page, Book);
                        // return page;
                    } catch (Exception e) {
                        System.out.println("Error"+e +"Enter page Numbers");
                        //   e.printStackTrace();
                    }

                } else
                    text = readbuffer.readLine();
                // System.out.println("line 1:"+ readbuffer.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(" The specific Line is: " + text);

       // return arr;

    }
    
    
    
    
    static int count_NOnZero(int[] array){
        int j = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] != 0){
                j = j + 1;
            }
        }
        return j;
    }
    
    static  void Extract_Images(int[] Read_pages, String Book){
       // final String[] Book = new String[1];
       // int[] Read_pages = pageNumbers("pages.txt", Book);
        int m = count_NOnZero(Read_pages);
        // String book = book_Name("pages.txt");

        try {
            String sourceDir1 = "Input\\";
            String destinationDir1 = "Output\\";

            File PDF_Folder = new File(sourceDir1);
            File PDF_Files[] = PDF_Folder.listFiles();
            for (int f_ind = 0; f_ind < PDF_Files.length; f_ind++) {
                String File_Name = Book;
                // String File_Name = PDF_Files[f_ind].getName();
                String File_Name1 = File_Name.replace(".pdf", "");
                String src_Complete_Path = sourceDir1 + File_Name +".pdf";
                String Des_Complete_Path = destinationDir1 + File_Name1 + "\\";
                System.out.println("src_Complete_Path=" + src_Complete_Path);
                File sourceFile = new File(src_Complete_Path);
                File destinationFile = new File(Des_Complete_Path);
                if (!destinationFile.exists()) {
                    destinationFile.mkdir();
                    System.out.println("Folder Created -> " + destinationFile.getAbsolutePath());
                }
                if (sourceFile.exists()) {
                    System.out.println("Images copied to Folder Location: " + destinationFile.getAbsolutePath());
                    String fileName = sourceFile.getName().replace(".pdf", "");
                    String fileExtension = "jpg";
                    PDDocument document = PDDocument.load(sourceFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(document);
                    int numberOfPages = document.getNumberOfPages();
                    int dpi = 300;
                    System.out.println("Des_Complete_Path= " + Des_Complete_Path);
                    for (int i = 0; i < numberOfPages; ++i) {
                        /*File outPutFile = new File(destinationFile + "\\" + fileName + "_" + (i + 1) + "." + fileExtension);
                        BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.GRAY);*/
                        int k = i + 1;
//                        DataBuffer dataBuffer = bImage.getData().getDataBuffer();
                        for(int j = 0; j < Read_pages.length; j++){
                            if(Read_pages[j] == k){
                                File outPutFile = new File(destinationFile + "\\" + fileName + "_" + (i + 1) + "." + fileExtension);
                                BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.GRAY);
                                ImageIO.write(bImage, fileExtension, outPutFile);
                                System.out.println("Converted Images are saved at -> " + destinationFile.getAbsolutePath());
                                m--;
                            }

                        }
                        if(m == 0){
                            break;
                        }


                    }
                    document.close();
                }
                break;
            }

        } catch (IOException e) {
            System.out.println("error occured" + e);
        }

    }
    
    
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
     pageNumbers("Pages_Information.txt");
   }
    
}
