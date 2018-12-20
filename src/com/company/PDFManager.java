package com.company;

import com.csvreader.CsvWriter;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PDFManager
{

    private PDFParser parser;
    private PDFTextStripper pdfStripper;
    private PDDocument pdDoc ;
    private COSDocument cosDoc ;

    private String Text ;
    private String filePath;
    private File file;

    public PDFManager() throws IOException {

    }
    public String ToText(String path) throws IOException
    {
        this.pdfStripper = null;
        this.pdDoc = null;
        this.cosDoc = null;


        file = new File(path);
        parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0

        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        pdDoc.getNumberOfPages();
        pdfStripper.setStartPage(2);
        pdfStripper.setEndPage(3);

        PDDocument document = PDDocument.load(new File(path));
        PDDocumentInformation info = document.getDocumentInformation();
        /*System.out.println( "Page Count=" + document.getNumberOfPages() );
        System.out.println( "Title=" + info.getTitle() );
        System.out.println( "Author=" + info.getAuthor() );
        System.out.println( "Subject=" + info.getSubject() );
        System.out.println( "Keywords=" + info.getKeywords() );
        System.out.println( "Creator=" + info.getCreator() );
        System.out.println( "Producer=" + info.getProducer() );
        System.out.println( "Trapped=" + info.getTrapped() );*/

        int count = document.getNumberOfPages();
        String Author = info.getAuthor();
        String Title = info.getTitle();
        String keywords = info.getKeywords();

        String metaFile = "Meta.csv";
        String key = "Keywords.csv";

        boolean alreadyExists = new File(metaFile).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(metaFile, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
//                csvOutput.write("Page Count");
                csvOutput.write("Title");
                csvOutput.write("Author");
                csvOutput.write("Path");

                csvOutput.write("Keywords");

                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            // write out a few records
//            csvOutput.write(count);
            csvOutput.write(Title);
            csvOutput.write(Author);
            csvOutput.write(path);

            csvOutput.write(keywords);
            csvOutput.endRecord();

            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // reading text from page 1 to 10
        // if you want to get text from full pdf file use this code
        // pdfStripper.setEndPage(pdDoc.getNumberOfPages());


        Text = pdfStripper.getText(pdDoc);
        return Text;
    }
}
