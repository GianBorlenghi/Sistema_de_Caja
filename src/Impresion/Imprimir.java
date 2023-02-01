package Impresion;

import Modelos.Producto;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.print.Pageable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Imprimir {

    private String ruta = "Users\\giaan\\OneDrive\\Documentos\\Escritorio\\pepe\\";
    
    public void imprimimos(ArrayList<Producto> listaCompra, float total, int ticket,String descuento) throws IOException {
        Document documento = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("C:\\"+ruta+"Ticket.pdf"));
            
            documento.open();
            PdfContentByte cb = writer.getDirectContent();
            Graphics g = cb.createGraphics(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());

            Paragraph titulo = new Paragraph();
            titulo.add("TICKET GOOD MARKET");
            titulo.setAlignment(Element.ALIGN_CENTER);

            Paragraph nticket = new Paragraph();
            nticket.add("N° TICKET: " + ticket);
            nticket.setAlignment(Element.ALIGN_CENTER);

            Paragraph direccion = new Paragraph();
            direccion.add("AV. DE LOS TRABAJADORES 1875");
            direccion.setAlignment(Element.ALIGN_CENTER);

            Paragraph sep = new Paragraph();
            direccion.add("***********************************************************************");
            direccion.setAlignment(Element.ALIGN_CENTER);

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

            hora.add("Fecha: " + gdia + "/" + gmes + "/" + gaño + " " + ghora + ":" + gmin);
            hora.setAlignment(Element.ALIGN_CENTER);

            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            documento.add(nticket);
            documento.add(Chunk.NEWLINE);

            documento.add(direccion);
            documento.add(Chunk.NEWLINE);

            documento.add(hora);
            documento.add(Chunk.NEWLINE);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            Paragraph titulos = new Paragraph();
            titulos.add("ARTICULO                                       CANT.                         PRECIO UNITARIO\n");
            titulos.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulos);
            documento.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);

            for (Producto p : listaCompra) {

                table.addCell("" + p.getNombre_producto());
                table.addCell("" + p.getCant());
                table.addCell("$" + p.getPrecio());

            }

            PdfPCell celdaDescuento = new PdfPCell(new Paragraph(descuento));
            celdaDescuento.setColspan(3);
            table.addCell(celdaDescuento);
            
            
            PdfPCell celdaFinal = new PdfPCell(new Paragraph("TOTAL                                                                                  $" + total));
            celdaFinal.setBorderColor(Color.RED);
            celdaFinal.setColspan(3);
            celdaFinal.setPaddingTop(5);
            celdaFinal.setPaddingBottom(5);
            celdaFinal.setBackgroundColor(java.awt.Color.GRAY);
            table.addCell(celdaFinal);

            documento.add(table);

            documento.close();
            writer.close();
            
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (DocumentException e) {
            System.out.println(e.toString());
        }
        
        File file = new File("C:\\"+ruta+"Ticket.pdf");
        
        Desktop.getDesktop().open(file);
        //Desktop.getDesktop().print(file);
        // file.deleteOnExit();*/

    }

    public void imprimirCierreCaja(float totalDelDia) throws IOException {
        Document documento = new Document();
        try {

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("C:\\"+ruta+"Cierre.pdf"));
            documento.open();
            PdfContentByte cb = writer.getDirectContent();
            Graphics g = cb.createGraphics(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());

            Paragraph titulo = new Paragraph();
            titulo.add("TICKET GOOD MARKET");
            titulo.setAlignment(Element.ALIGN_CENTER);

            Paragraph cierre = new Paragraph();
            cierre.add("CIERRE DE CAJA");
            cierre.setAlignment(Element.ALIGN_CENTER);

            Paragraph direccion = new Paragraph();
            direccion.add("AV. DE LOS TRABAJADORES 1875");
            direccion.setAlignment(Element.ALIGN_CENTER);

            Paragraph sep = new Paragraph();
            direccion.add("***********************************************************************");
            direccion.setAlignment(Element.ALIGN_CENTER);

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

            hora.add("Fecha: " + gdia + "/" + gmes + "/" + gaño + " " + ghora + ":" + gmin);
            hora.setAlignment(Element.ALIGN_CENTER);

            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            documento.add(cierre);
            documento.add(Chunk.NEWLINE);

            documento.add(direccion);
            documento.add(Chunk.NEWLINE);

            documento.add(hora);
            documento.add(Chunk.NEWLINE);

            documento.add(sep);
            documento.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);

            table.addCell("TOTAL DEL DIA");
            table.addCell("" + totalDelDia);

            documento.add(table);
            
        documento.close();   
        
        
        File file = new File("C:\\"+ruta+"Cierre.pdf");

        Desktop.getDesktop().open(file);

        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (DocumentException e) {
            System.out.println(e.toString());
        }
        documento.close();

    }

}/*

//Creamos un flujo de entrada al cual asignamos el valor del PDF que esta dentro del JAR
            InputStream flujoEntrada = this.getClass().getResourceAsStream("/docs/AGBase.pdf");
            //Creamos un flujo de salida para poder escribir sobre el archivo temporal
            FileOutputStream flujoSalida = new FileOutputStream(temp);
            //Preparamos el temp para que se llene de la informacion de PDF dentro del jar
            FileWriter fw = new FileWriter(temp);
            //Creamos un arreglo de bytes generico que soporte un gran tamano 1KB * 512 B --> se usa para todo tipo de archivo  
            byte[] buffer = new byte[1024*512];
 
            int control; //para contar posiciones de byte
 
            //Mientras haya bytes por leer se ejecuta este bucle
            while ((control = flujoEntrada.read(buffer)) != -1){
                flujoSalida.write(buffer, 0, control);
            }
 
            //Cerramos y guardamos el archivo creado
            fw.close();
            flujoSalida.close();
            flujoEntrada.close();
 */
