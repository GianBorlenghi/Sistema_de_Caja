package Impresion;

import Modelos.Producto;
import Modelos.PromocionDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Imprimir {

    public ByteArrayOutputStream crearTicketVenta(ArrayList<Producto> listaCompra, float subTotal, int ticket, String descuento, String pago, ArrayList<PromocionDTO> listaPromos, float total) throws IOException {
    ByteArrayOutputStream docBytes = new ByteArrayOutputStream();
    DecimalFormat formatoDecimal = new DecimalFormat("#.00");

    try {
        // Inicializar la altura base del documento
        float alturaBase = 200f; // Altura mínima para el encabezado
        float alturaPorLinea = 10f; // Estimación de altura por línea de texto

        // Calcular altura dinámica en base al contenido
        int totalLineas = 15; // Líneas base del encabezado y separadores

        // Añadir líneas de los productos en la lista de compra
        totalLineas += listaCompra.size(); // Cada producto agrega una línea

        // Si hay descuentos o promociones, sumar líneas
        if (!descuento.isEmpty()) {
            totalLineas += 1; // Línea del descuento
        }
        totalLineas += listaPromos.size() * 2; // Cada promoción agrega 2 líneas (nombre del producto + promoción)

        // Añadir líneas de subtotal, total y forma de pago
        totalLineas += 3; // Subtotal, total y forma de pago

        // Calcular la altura total
        float alturaTotal = alturaBase + (totalLineas * alturaPorLinea);

        // Definir tamaño dinámico del documento
        Rectangle pageSize = new Rectangle(230f, alturaTotal); // Mantiene el ancho, ajusta la altura
        Document documento = new Document(pageSize);

        Font fuente = new Font(Font.HELVETICA, 7); // Cambiamos la fuente a HELVETICA
        PdfWriter pdfWriter = PdfWriter.getInstance(documento, docBytes);
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.addWriter(pdfWriter);

        documento.open();

        // Cabecera del ticket
        Paragraph titulo = new Paragraph("*GOOD MARKET*", new Font(Font.HELVETICA, 10, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);

        documento.add(new Paragraph("GOOD MARKET S.R.L", new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("CUIT: 23-95438568-1", new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("IVA Responsable Inscripto", new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("Av. Trabajadores 1875", new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("Tel: 2477-368798", new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("Punto de Venta: 001", new Font(Font.HELVETICA, 7)));

        Paragraph sep = new Paragraph("================================", new Font(Font.HELVETICA, 7,Font.BOLD));
        sep.setAlignment(Element.ALIGN_CENTER);
        documento.add(sep);

        // Fecha y hora
        String fechaHora = "Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        documento.add(new Paragraph(fechaHora, new Font(Font.HELVETICA, 7)));
        documento.add(new Paragraph("Ticket N°: " + ticket, new Font(Font.HELVETICA, 7)));

        documento.add(sep);

        // Crear encabezado de tabla
        PdfPTable encabezadoTabla = new PdfPTable(3);
        encabezadoTabla.setWidthPercentage(100); // Ocupa el ancho total del documento
        encabezadoTabla.setWidths(new float[]{1f, 3f, 1.5f}); // Proporción del ancho de las columnas

        PdfPCell headerCell1 = new PdfPCell(new Paragraph("Cant", new Font(Font.HELVETICA, 7,Font.BOLD)));
        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell headerCell2 = new PdfPCell(new Paragraph("Descripción", new Font(Font.HELVETICA, 7,Font.BOLD)));
        headerCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerCell2.setBorder(Rectangle.NO_BORDER);

        PdfPCell headerCell3 = new PdfPCell(new Paragraph("P.U.", new Font(Font.HELVETICA, 7,Font.BOLD)));
        headerCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerCell3.setBorder(Rectangle.NO_BORDER);

        encabezadoTabla.addCell(headerCell1);
        encabezadoTabla.addCell(headerCell2);
        encabezadoTabla.addCell(headerCell3);

        documento.add(encabezadoTabla);

        // Tabla con productos comprados
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100); // Ocupa el ancho total del documento
        table.setWidths(new float[]{1f, 3f, 1.5f}); // Proporción del ancho de las columnas

        for (Producto p : listaCompra) {
            PdfPCell cell1 = new PdfPCell(new Paragraph("" + String.format("%.3f", p.getCant()), new Font(Font.HELVETICA, 7)));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell2 = new PdfPCell(new Paragraph("" + p.getNombre_producto(), new Font(Font.HELVETICA, 7)));
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cell3 = new PdfPCell(new Paragraph("$" + formatoDecimal.format(p.getPrecio_al_publico()), new Font(Font.HELVETICA, 7)));
            cell3.setBorder(Rectangle.NO_BORDER);
            cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
        }

        if (!descuento.isEmpty()) {
            Paragraph p = new Paragraph(descuento);
            p.getFont().setSize(7);
            PdfPCell celdaDescuento = new PdfPCell(p);
            celdaDescuento.setColspan(3);
            celdaDescuento.setPaddingTop(5);
            celdaDescuento.setPaddingBottom(5);
            table.addCell(celdaDescuento);
        }

        // Descuentos especiales
        if (!listaPromos.isEmpty()) {
            Paragraph p12 = new Paragraph("DESCUENTOS", new Font(Font.HELVETICA, 7, Font.BOLD));
            PdfPCell celdaDescuentos = new PdfPCell(p12);
            celdaDescuentos.setBorder(Rectangle.NO_BORDER);
            celdaDescuentos.setColspan(3);
            celdaDescuentos.setPaddingTop(10);
            celdaDescuentos.setPaddingBottom(5);
            table.addCell(celdaDescuentos);

            // Crear tabla separada para los descuentos
            PdfPTable promoTable = new PdfPTable(3);
            promoTable.setWidthPercentage(100);
            promoTable.setWidths(new float[]{3f, 2f, 1.5f});

            listaPromos.forEach(promo -> {
                PdfPCell promoProdCell = new PdfPCell(new Paragraph(promo.getNombre_producto(), new Font(Font.HELVETICA, 7)));
                promoProdCell.setBorder(Rectangle.NO_BORDER);

                PdfPCell promoDescCell = new PdfPCell(new Paragraph(promo.getNombre_promocion(), new Font(Font.HELVETICA, 7)));
                promoDescCell.setBorder(Rectangle.NO_BORDER);

                PdfPCell promoDescuentoCell = new PdfPCell(new Paragraph("-$" + formatoDecimal.format(promo.getDescuento_producto()), new Font(Font.HELVETICA, 7)));
                promoDescuentoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                promoDescuentoCell.setBorder(Rectangle.NO_BORDER);

                promoTable.addCell(promoProdCell);
                promoTable.addCell(promoDescCell);
                promoTable.addCell(promoDescuentoCell);
            });

            PdfPCell promoTableCell = new PdfPCell();
            promoTableCell.addElement(promoTable);
            promoTableCell.setColspan(3);
            promoTableCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(promoTableCell);
        }

        // Forma de pago
        Paragraph p2 = new Paragraph("Forma de pago: " + pago);
        p2.getFont().setSize(7);
        PdfPCell celdaMetodoPago = new PdfPCell(p2);
        celdaMetodoPago.setBorder(Rectangle.NO_BORDER);
        celdaMetodoPago.setColspan(3);
        celdaMetodoPago.setPaddingTop(10);
        celdaMetodoPago.setPaddingBottom(5);
        table.addCell(celdaMetodoPago);

        documento.add(table);
        //documento.add(sep);

        // Subtotal y Total en una nueva tabla con alineación a la derecha
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(100);
        totalTable.setWidths(new float[]{3f, 3f});

        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell subTotalCell = new PdfPCell(new Paragraph("Subtotal: $" + formatoDecimal.format(subTotal), new Font(Font.HELVETICA, 7)));
        subTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        subTotalCell.setBorder(Rectangle.NO_BORDER);
        subTotalCell.setPaddingTop(5);

        PdfPCell totalCell = new PdfPCell(new Paragraph("Total: $" + formatoDecimal.format(total), new Font(Font.HELVETICA, 7, Font.BOLD)));
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalCell.setBorder(Rectangle.NO_BORDER);
        totalCell.setPaddingTop(5);

        totalTable.addCell(emptyCell);
        totalTable.addCell(subTotalCell);

        totalTable.addCell(emptyCell);
        totalTable.addCell(totalCell);

        documento.add(totalTable);
        documento.add(sep);

        // Leyenda de agradecimiento
        Paragraph leyenda = new Paragraph("¡Gracias por su compra!", new Font(Font.HELVETICA, 7));
        leyenda.setAlignment(Element.ALIGN_CENTER);
        documento.add(leyenda);

        documento.close();
        pdfWriter.close();

    } catch (DocumentException e) {
        e.printStackTrace();
    }

    return docBytes;


        /*ByteArrayOutputStream docBytes = new ByteArrayOutputStream();
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

                Paragraph col1 = new Paragraph("" + p.getNombre_producto() + " " + p.getMarca_nombre());
                col1.getFont().setSize(5);
                Paragraph col2 = new Paragraph("" + p.getCant());
                col2.getFont().setSize(5);
                Paragraph col3 = new Paragraph("$" + formatoDecimal.format(p.getPrecio_al_publico()));
                col3.getFont().setSize(5);
                table.addCell(col1);
                table.addCell(col2);
                table.addCell(col3);
            }

            if (descuento != "") {
                Paragraph p = new Paragraph(descuento);
                p.getFont().setSize(5);
                PdfPCell celdaDescuento = new PdfPCell(p);
                celdaDescuento.setColspan(3);
                celdaDescuento.setPaddingTop(5);
                celdaDescuento.setPaddingBottom(5);
                table.addCell(celdaDescuento);
            }

            Paragraph p2 = new Paragraph("Forma de pago: " + pago);
            p2.getFont().setSize(5);
            PdfPCell celdaMetodoPago = new PdfPCell(p2);
            celdaMetodoPago.setBorder(0);
            celdaMetodoPago.setColspan(3);
            celdaMetodoPago.setPaddingTop(10);
            celdaMetodoPago.setPaddingBottom(5);
            table.addCell(celdaMetodoPago);

            Paragraph p12 = new Paragraph("DESCUENTOS");
            p12.getFont().setSize(5);
            PdfPCell celdaDescuentos = new PdfPCell(p12);
            celdaDescuentos.setBorder(0);
            celdaDescuentos.setColspan(3);
            celdaDescuentos.setPaddingTop(10);
            celdaDescuentos.setPaddingBottom(5);
            table.addCell(celdaDescuentos);
            
            for (PromocionDTO p : listaPromos) {

                Paragraph col1 = new Paragraph("" + p.getNombre_producto());
                col1.getFont().setSize(5);
                Paragraph col2 = new Paragraph("" + p.getNombre_promocion());
                col2.getFont().setSize(5);
                Paragraph col3 = new Paragraph("-" + formatoDecimal.format(p.getDescuento_producto()));
                col3.getFont().setSize(5);
                table.addCell(col1);
                table.addCell(col2);
                table.addCell(col3);
            }

            Paragraph pfinal = new Paragraph("SUBTOTAL                                                $" + formatoDecimal.format(subTotal));
            pfinal.getFont().setSize(6);
            Paragraph pfinal2 = new Paragraph("TOTAL                                                     $" + formatoDecimal.format(total));
            pfinal2.getFont().setSize(6);

            PdfPCell celdaFinal = new PdfPCell(pfinal);
            celdaFinal.setBorderColor(Color.RED);
            celdaFinal.setColspan(3);
            celdaFinal.setPaddingTop(5);
            celdaFinal.setPaddingBottom(5);
            celdaFinal.setBackgroundColor(java.awt.Color.GRAY);
            table.addCell(celdaFinal);

            PdfPCell celdaFinal2 = new PdfPCell(pfinal2);
            celdaFinal2.setBorderColor(Color.RED);
            celdaFinal2.setColspan(3);
            celdaFinal2.setPaddingTop(5);
            celdaFinal2.setPaddingBottom(5);
            celdaFinal2.setBackgroundColor(java.awt.Color.GRAY);
            table.addCell(celdaFinal2);

            documento.add(table);

            documento.close();
            pdfWriter.close();

        } catch (DocumentException e) {
            System.out.println(e.toString());
        }

        return docBytes;*/
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
