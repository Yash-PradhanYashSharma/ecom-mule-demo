package com.yash.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class JsonToPDF {

	public byte[] jsontopdf(String filename, String content) throws IOException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try

		{
			JSONObject jsonObject = new JSONObject(content);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			float[] columnWidths = { 4f, 4f, 4f };
			table.setWidths(columnWidths);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell("Total");
			table.addCell("AmountPaid");
			table.addCell("AmountDue");

			table.setHeaderRows(1);
			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j = 0; j < cells.length; j++) {
				cells[j].setBackgroundColor(BaseColor.GRAY);
			}
			table.addCell(String.valueOf(jsonObject.get("total")));
			table.addCell(String.valueOf(jsonObject.get("amount_paid")));
			table.addCell(String.valueOf(jsonObject.get("amount_due")));

			PdfWriter.getInstance(document, out);

			Font f = new Font(FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.DARK_GRAY);
			Paragraph p = new Paragraph("Sales Invoice", f);

			p.setAlignment(Paragraph.ALIGN_CENTER);

			document.open();
			document.add(p);
			document.add(new Paragraph("\n\n\n"));
			document.add(new Paragraph("Customer Name: " + (String) jsonObject.get("customer_name")));
			document.add(new Paragraph("Customer Email: " + (String) jsonObject.get("customer_email")));
			document.add(new Paragraph("Customer Phone: 7338318134"));

			document.add(new Paragraph("\n\n"));

			document.add(new Paragraph("Invoice Id: " + (String) jsonObject.get("id")));
			document.add(new Paragraph("Account Name: " + (String) jsonObject.get("account_name")));
			document.add(new Paragraph("Account Country: " + (String) jsonObject.get("account_country")));
			document.add(new Paragraph("\n\n"));

			document.add(table);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"Thanks for your purchase! \nFor any futher questions do not hesitate to contact us!"));

			document.close();

		} catch (DocumentException e)

		{

			e.printStackTrace();

		}
		System.out.print("DONEEEEEEE");
		return out.toByteArray();

	}

}