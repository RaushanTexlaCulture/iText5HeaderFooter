package iTextPdf5;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import jdk.internal.org.jline.utils.Log;

public class AddHeaderAndFooter  extends PdfPageEventHelper{
private PdfTemplate t;
private Image total;

public void onOpenDocument(PdfWriter writer, Document document) {
    t = writer.getDirectContent().createTemplate(30, 16);
    try {
        total = Image.getInstance(t);
        total.setRole(PdfName.ARTIFACT);
    } catch (DocumentException de) {
        throw new ExceptionConverter(de);
    }
}

@Override
public void onEndPage(PdfWriter writer, Document document) {
    addHeader(writer);
    addFooter(writer);
}

private void addHeader(PdfWriter writer){
    PdfPTable  header = new PdfPTable(1);
    try {
        // set defaults
        header.setWidths(new int[]{100});
        header.setTotalWidth(525);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(65);
        header.getDefaultCell().setBorder(Rectangle.OUT_BOTTOM);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);

        // add image
        Image logo = Image.getInstance(AddHeaderAndFooter.class.getResource("/texla_logo7.png"));
        header.addCell(logo);


        // write content
        header.writeSelectedRows(0, -1, 34, 823, writer.getDirectContent());
    } catch(DocumentException de) {
        throw new ExceptionConverter(de);
    } catch (MalformedURLException e) {
        throw new ExceptionConverter(e);
    } catch (IOException e) {
        throw new ExceptionConverter(e);
    }
}


private void addFooter(PdfWriter writer){
    PdfPTable footer = new PdfPTable(1);
    try {

        footer.setWidths(new int[]{100});
        footer.setTotalWidth(527);
        footer.setLockedWidth(false);
        footer.getDefaultCell().setFixedHeight(40);
        footer.getDefaultCell().setBorder(Rectangle.OUT_TOP);
        footer.getDefaultCell().setBorderColor(BaseColor.WHITE);

        // add current page count
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(new Phrase(String.format( writer.getPageNumber()+ " | Page  | TexlaCulture, Gurugram, Haryana") , new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));

        // write page
        PdfContentByte canvas = writer.getDirectContent();
        canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
        footer.writeSelectedRows(0, -1, 34, 50, canvas);
        canvas.endMarkedContentSequence();
    } catch(DocumentException de) {
        throw new ExceptionConverter(de);
    }
}

public void onCloseDocument(PdfWriter writer, Document document) {
    int totalLength = String.valueOf(writer.getPageNumber()).length();
    int totalWidth = totalLength * 5;
    ColumnText.showTextAligned(t, Element.ALIGN_LEFT,
            new Phrase(String.valueOf(writer.getPageNumber()),new Font(Font.FontFamily.HELVETICA, 12)),
            totalWidth, 6, 0);
  }
}