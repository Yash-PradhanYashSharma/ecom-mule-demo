package com.pdf;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;


public class JsonToPDF  {

public  void jsontopdf(String filename,String content) {

Document document = new Document();

try

{

File file = new File(filename);

PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

document.open();

document.add(new Paragraph(content));

document.close();

writer.close();

} catch (DocumentException e)

{

e.printStackTrace();

} catch (FileNotFoundException e)

{

e.printStackTrace();

}


}


}