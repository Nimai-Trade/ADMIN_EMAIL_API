package com.nimai.lc.utility;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.nimai.lc.entity.NimaiLCMaster;
 
 
public class PDFHelper {
    private List<NimaiLCMaster> listOfLC;
     
    public PDFHelper(List<NimaiLCMaster> listOfLC) {
        this.listOfLC = listOfLC;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Transaction ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Product Type", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Applicant Contact Person", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Applicant Contact Person Email", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Applicant Country", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Beneficiary Country", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Beneficiary Contact Person", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Beneficiary Contact Person Email", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Beneficiary Country", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Beneficiary Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Beneficiary Swift Code", font));
        table.addCell(cell);
           
            
    }
     
    private void writeTableData(PdfPTable table) {
        for (NimaiLCMaster lc : listOfLC) {
            table.addCell(lc.getTransactionId());
            table.addCell(lc.getUserId());
            table.addCell(lc.getRequirementType());
            table.addCell(lc.getApplicantContactPerson());
            table.addCell(lc.getApplicantContactPersonEmail());
            table.addCell(lc.getApplicantCountry());
            table.addCell(lc.getBeneBankCountry());
            table.addCell(lc.getBeneContactPerson());
            table.addCell(lc.getBeneContactPersonEmail());
            table.addCell(lc.getBeneCountry());
            table.addCell(lc.getBeneName());
            table.addCell(lc.getBeneSwiftCode());


            //table.addCell(user.getRoles().toString());
            //table.addCell(String.valueOf(user.isEnabled()));
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
         
        Paragraph p = new Paragraph("Transaction Details", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(12);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {10.5f,4.5f, 8.5f, 7.5f,7.5f, 7.5f,7.5f,7.5f, 8.5f, 7.5f,7.5f, 7.5f});
        table.setSpacingBefore(10);
//        table.setWidthPercentage(100f);
//        table.setTotalWidth(6000);
       // table.setTotalWidth(500.5f);
       // table.setLockedWidth(true);//
        PageSize pagesize = new PageSize();
        Rectangle rec = new Rectangle(7000,4000);
        writeTableHeader(table);
        writeTableData(table);
         document.setPageSize(rec);
        document.add(table);
         
        document.close();
         
    }
}