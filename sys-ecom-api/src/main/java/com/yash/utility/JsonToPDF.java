package com.yash.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
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
import com.itextpdf.text.pdf.draw.LineSeparator;

public class JsonToPDF {

	public byte[] jsontopdf(String filename, String content) throws IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Font f1 = new Font(FontFamily.TIMES_ROMAN, 10.0f, Font.UNDEFINED, BaseColor.BLACK);
		Font f3 = new Font(FontFamily.TIMES_ROMAN, 14.0f, Font.UNDEFINED, BaseColor.BLACK);
		try {
			JSONObject jsonObject = new JSONObject(content);
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			table1.setSpacingBefore(10f);
			table1.setSpacingAfter(10f);
			float[] columnWidths1 = { 2f, 2f, 7f };
			table1.setWidths(columnWidths1);
			Font f2 = new Font(FontFamily.TIMES_ROMAN, 15.0f, Font.UNDEFINED, BaseColor.BLACK);
			table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(new Paragraph("Quantity", f2));
			table1.addCell(new Paragraph("Unit Cost", f2));
			table1.addCell(new Paragraph("Description", f2));
			table1.setHeaderRows(1);
			PdfPCell[] cells1 = table1.getRow(0).getCells();
			for (int j = 0; j < cells1.length; j++) {
				cells1[j].setBackgroundColor(BaseColor.GRAY);
			}
			PdfWriter.getInstance(document, out);
			Font f = new Font(FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.DARK_GRAY);
			Paragraph p = new Paragraph("Sales Invoice", f);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			document.open();
			document.add(p);
			document.add(new Paragraph("\n\n\n"));
			document.add(new Paragraph("Customer Name: " + (String) jsonObject.get("customer_name"), f3));
			document.add(new Paragraph("Customer Email: " + (String) jsonObject.get("customer_email"), f3));
			document.add(new Paragraph("Customer Phone: 7338318134", f3));
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph("Invoice Id: " + (String) jsonObject.get("id"), f3));
			document.add(new Paragraph("Account Name: " + (String) jsonObject.get("account_name"), f3));
			document.add(new Paragraph("Account Country: " + (String) jsonObject.get("account_country"), f3));
			document.add(new Paragraph("\n\n"));

			JSONObject jsonobj = jsonObject.getJSONObject("lines");
			JSONArray jsonarr = jsonobj.getJSONArray("data");

			for (int i = 0; i < jsonarr.length(); i++) {
				JSONObject json = jsonarr.getJSONObject(i);
				Iterator<String> keys = json.keys();

				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equals("description")) {
						table1.addCell(new Paragraph((String) json.get("description"), f1));
					}
					if (key.equals("amount")) {
						table1.addCell(new Paragraph(String.valueOf(json.get("amount")), f1));
					}
					if (key.equals("quantity")) {
						table1.addCell(new Paragraph(String.valueOf(json.get("quantity")), f1));
					}
				}
			}

			document.add(table1);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("\n"));
			Paragraph p1 = new Paragraph("Total: " + String.valueOf(jsonObject.get("total")), f3);
			p1.setAlignment(Paragraph.ALIGN_RIGHT);
			document.add(p1);
			Paragraph p2 = new Paragraph("Amount Paid: " + String.valueOf(jsonObject.get("amount_paid")), f3);
			p2.setAlignment(Paragraph.ALIGN_RIGHT);
			document.add(p2);
			Paragraph p3 = new Paragraph("Amount Due: " + String.valueOf(jsonObject.get("amount_due")), f3);
			p3.setAlignment(Paragraph.ALIGN_RIGHT);
			document.add(p3);
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"Thanks for your purchase! \nFor any futher questions do not hesitate to contact us!", f3));
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.print("DONEEEEEEE");
		return out.toByteArray();
	}

}