package iTextPdf5;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PageNumberExample {

    public static void main(String[] args) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("9HeaderFooterTexla.pdf"));
            writer.setPageEvent(new AddHeaderAndFooter());

            document.open();
            // Add your content here
            Paragraph paragraph = new Paragraph("TexlaCulture page addition");
            document.add(paragraph);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        
        System.out.println("pdf created");
    }
}
