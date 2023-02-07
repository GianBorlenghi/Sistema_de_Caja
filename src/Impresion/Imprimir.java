package Impresion;

import Modelos.Producto;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Imprimir {

    public ByteArrayOutputStream crearTicketVenta(ArrayList<Producto> listaCompra, float total, int ticket, String descuento, String pago) throws IOException {
        ByteArrayOutputStream docBytes = new ByteArrayOutputStream();
        DecimalFormat formatoDecimal = new DecimalFormat("#.00");
     

        try {
            Rectangle pageSize = new Rectangle(360f, 14400f);
            Document documento = new Document(pageSize);

            Font fuente = new Font();
            fuente.setSize(8);
            fuente.setStyle(Font.NORMAL);
            PdfWriter pdfWriter = PdfWriter.getInstance(documento, docBytes);
            PdfDocument pdfDoc = new PdfDocument();
            pdfDoc.addWriter(pdfWriter);

            documento.open();

            Paragraph titulo = new Paragraph();
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(10);
            titulo.add("*GOOD MARKET*");
            titulo.setAlignment(Element.ALIGN_CENTER);

            Paragraph srl = new Paragraph();
            srl.getFont().setStyle(Font.NORMAL);
            srl.getFont().setSize(8);
            srl.add("GOOD MARKET S.R.L");
            srl.setAlignment(Element.ALIGN_CENTER);

            Paragraph cuil = new Paragraph();
            cuil.getFont().setStyle(Font.NORMAL);
            cuil.getFont().setSize(8);
            cuil.add("CUIL: 23-95438568-1");
            cuil.setAlignment(Element.ALIGN_CENTER);

            Paragraph direccion = new Paragraph();
            direccion.getFont().setStyle(Font.NORMAL);
            direccion.getFont().setSize(8);
            direccion.add("DIRECCION: AV. DE LOS TRABAJADORES 1875");
            direccion.setAlignment(Element.ALIGN_CENTER);

            Paragraph telefono = new Paragraph();
            telefono.getFont().setStyle(Font.BOLD);
            telefono.getFont().setSize(10);
            telefono.add("TELEFONO: 2477-368798");
            telefono.setAlignment(Element.ALIGN_CENTER);

            Paragraph sep = new Paragraph();
            sep.getFont().setStyle(Font.NORMAL);
            sep.getFont().setSize(8);
            sep.add("***********************************************************************");
            sep.setAlignment(Element.ALIGN_CENTER);

            Paragraph hora = new Paragraph();
            hora.setFont(fuente);
            int gmin;
            int ghora;
            int gdia;
            int gmes;
            int gaño;

            gdia = LocalDateTime.now().getDayOfMonth();
            gmes = LocalDateTime.now().getMonthValue();
            gaño = LocalDateTime.now().getYear();
            ghora = LocalDateTime.now().getHour();
            gmin = LocalDateTime.now().getMinute();

            hora.getFont().setStyle(Font.NORMAL);
            hora.getFont().setSize(8);
            hora.add("FECHA: " + gdia + "/" + gmes + "/" + gaño + " " + ghora + ":" + gmin);
            hora.setAlignment(Element.ALIGN_CENTER);

            Paragraph nticket = new Paragraph();
            nticket.getFont().setSize(8);
            nticket.add("N° TICKET: " + ticket);
            nticket.setAlignment(Element.ALIGN_CENTER);

            documento.add(titulo);
            documento.add(sep);

            documento.add(srl);

            documento.add(cuil);

            documento.add(direccion);

            documento.add(telefono);

            documento.add(hora);

            documento.add(nticket);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            Paragraph titulos = new Paragraph();
            titulos.getFont().setStyle(Font.NORMAL);
            titulos.getFont().setSize(8);
            titulos.add("ARTICULO                          CANT.                 PRECIO UNITARIO\n");
            titulos.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulos);
            documento.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.setTotalWidth(350f);

            for (Producto p : listaCompra) {

                Paragraph col1 = new Paragraph("" + p.getNombre_producto() + " "+p.getMarca_nombre());
                col1.getFont().setSize(8);
                Paragraph col2 = new Paragraph("" + p.getCant());
                col2.getFont().setSize(8);
                Paragraph col3 = new Paragraph("$" +formatoDecimal.format(p.getPrecio()));
                col3.getFont().setSize(8);
                table.addCell(col1);
                table.addCell(col2);
                table.addCell(col3);
            }

            if (descuento != "") {
                Paragraph p = new Paragraph(descuento);
                p.getFont().setSize(8);
                PdfPCell celdaDescuento = new PdfPCell(p);
                celdaDescuento.setColspan(3);
                celdaDescuento.setPaddingTop(5);
                celdaDescuento.setPaddingBottom(5);
                table.addCell(celdaDescuento);
            }
            Paragraph p2 = new Paragraph("Forma de pago: " + pago);
            p2.getFont().setSize(8);
            PdfPCell celdaMetodoPago = new PdfPCell(p2);
            celdaMetodoPago.setBorder(0);
            celdaMetodoPago.setColspan(3);
            celdaMetodoPago.setPaddingTop(10);
            celdaMetodoPago.setPaddingBottom(5);
            table.addCell(celdaMetodoPago);

            Paragraph pfinal = new Paragraph("TOTAL                                                     $" +    formatoDecimal.format(total));
            pfinal.getFont().setSize(8);
            PdfPCell celdaFinal = new PdfPCell(pfinal);
            celdaFinal.setBorderColor(Color.RED);
            celdaFinal.setColspan(3);
            celdaFinal.setPaddingTop(5);
            celdaFinal.setPaddingBottom(5);
            celdaFinal.setBackgroundColor(java.awt.Color.GRAY);
            table.addCell(celdaFinal);

            documento.add(table);

            documento.close();
            pdfWriter.close();

        } catch (DocumentException e) {
            System.out.println(e.toString());
        }

        return docBytes;
    }

    public void imprimir(ByteArrayOutputStream documentoBytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(documentoBytes.toByteArray());

        PDDocument doc = PDDocument.load(bais);
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job.printDialog() == true) {
            job.setPageable(new PDFPageable(doc));
            try {
                job.print();
            } catch (PrinterException ex) {
                Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ByteArrayOutputStream imprimirCierreCaja(float totalDelDia) throws IOException {
        ByteArrayOutputStream docBytes = new ByteArrayOutputStream();
        try {

            Rectangle pageSize = new Rectangle(360f, 14400f);
            Document documento = new Document(pageSize);
            PdfWriter pdfWriter = PdfWriter.getInstance(documento, docBytes);
            PdfDocument pdfDoc = new PdfDocument();
            pdfDoc.addWriter(pdfWriter);

            documento.open();

           Paragraph titulo = new Paragraph();
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(10);
            titulo.add("*CIERRE DE CAJA*");
            titulo.setAlignment(Element.ALIGN_CENTER);

            Paragraph srl = new Paragraph();
            srl.getFont().setStyle(Font.NORMAL);
            srl.getFont().setSize(8);
            srl.add("GOOD MARKET S.R.L");
            srl.setAlignment(Element.ALIGN_CENTER);

            Paragraph cuil = new Paragraph();
            cuil.getFont().setStyle(Font.NORMAL);
            cuil.getFont().setSize(8);
            cuil.add("CUIL: 23-95438568-1");
            cuil.setAlignment(Element.ALIGN_CENTER);

            Paragraph direccion = new Paragraph();
            direccion.getFont().setStyle(Font.NORMAL);
            direccion.getFont().setSize(8);
            direccion.add("DIRECCION: AV. DE LOS TRABAJADORES 1875");
            direccion.setAlignment(Element.ALIGN_CENTER);

            Paragraph telefono = new Paragraph();
            telefono.getFont().setStyle(Font.BOLD);
            telefono.getFont().setSize(10);
            telefono.add("TELEFONO: 2477-368798");
            telefono.setAlignment(Element.ALIGN_CENTER);

            Paragraph sep = new Paragraph();
            sep.getFont().setStyle(Font.NORMAL);
            sep.getFont().setSize(8);
            sep.add("***********************************************************************");
            sep.setAlignment(Element.ALIGN_CENTER);

            Paragraph hora = new Paragraph();
            int gmin;
            int ghora;
            int gdia;
            int gmes;
            int gaño;

            gdia = LocalDateTime.now().getDayOfMonth();
            gmes = LocalDateTime.now().getMonthValue();
            gaño = LocalDateTime.now().getYear();
            ghora = LocalDateTime.now().getHour();
            gmin = LocalDateTime.now().getMinute();

            hora.getFont().setStyle(Font.NORMAL);
            hora.getFont().setSize(8);
            hora.add("FECHA: " + gdia + "/" + gmes + "/" + gaño + " " + ghora + ":" + gmin);
            hora.setAlignment(Element.ALIGN_CENTER);

            documento.add(titulo);

            documento.add(sep);

            documento.add(srl);
            
            documento.add(cuil);


            documento.add(direccion);

            documento.add(hora);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);

            Paragraph col1 = new Paragraph("TOTAL DEL DIA");
            col1.getFont().setSize(8);
            table.addCell(col1);
            
            Paragraph col2 = new Paragraph("$ " + totalDelDia);
            col2.getFont().setSize(8);
            col2.setAlignment(Element.ALIGN_RIGHT);
            table.addCell(col2);
            
            documento.add(table);

            documento.close();
            pdfWriter.close();

        } catch (DocumentException e) {
            System.out.println(e.toString());
        }

        return docBytes;
    }

}
