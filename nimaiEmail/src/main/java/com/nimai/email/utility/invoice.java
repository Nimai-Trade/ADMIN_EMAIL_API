package com.nimai.email.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class invoice {
	public static void main(String[] args) throws Exception {

		Document document = new Document();

		  PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
 
		document.open();
		  Font bold = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
	        Font normal = new Font(Font.FontFamily.HELVETICA, 8f, Font.NORMAL);
	        PdfPTable tabletmp = new PdfPTable(1);
	        tabletmp.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        tabletmp.setWidthPercentage(100);
	        PdfPTable table = new PdfPTable(2);
	        float[] colWidths = { 45, 55 };
	        table.setWidths(colWidths);
	        String imageUrl = "NIMAI";
	        //Image image2 = Image.getInstance(new URL(imageUrl));
	       // image2.setWidthPercentage(60);
	        
	        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
	        
	        PdfPCell cell = new PdfPCell();
	        cell.setBorder(Rectangle.NO_BORDER);
	        Chunk chunk = new Chunk("NIMAI", normal);
	        Phrase p= new Phrase(chunk);
	        table.addCell(p);
	        
	        
	        
	        
	        String INVOICE = "3223424";
	        String INVOICEDate = "09/09/09";
	        Chunk chunk1 = new Chunk("Date: ", normal);
	        Phrase ph1 = new Phrase(chunk1);

	        Chunk chunk2 = new Chunk(INVOICEDate, bold);
	        Phrase ph2 = new Phrase(chunk2);

	        Chunk chunk3 = new Chunk("Invoice Number: ", normal);
	        Phrase ph3 = new Phrase(chunk3);

	        Chunk chunk4 = new Chunk(INVOICE, bold);
	        Phrase ph4 = new Phrase(chunk4);

	        Paragraph ph = new Paragraph();
	        ph.add(ph1);
	        ph.add(ph2);
	        ph.add(ph3);
	        ph.add(ph4);

	        table.addCell(ph);
	        tabletmp.addCell(table);
	        PdfContentByte canvas = writer.getDirectContent();
	        canvas.saveState();
	        canvas.setLineWidth((float) 10 / 10);
	        canvas.moveTo(40, 806 - (5 * 10));
	        canvas.lineTo(555, 806 - (5 * 10));
	        canvas.stroke();
	        document.add(tabletmp);
	        canvas.restoreState();
	        PdfPTable tabletmp1 = new PdfPTable(1);
	        tabletmp1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        tabletmp1.setWidthPercentage(100);

	        document.add(tabletmp1);


		document.close();
	}

	private static PdfPCell getCell(String text, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(text));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

}
