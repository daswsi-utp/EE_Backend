package com.report_service.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.report_service.entities.Sale;
import com.report_service.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReporteServiceRestController {

    private ReportService reportService;
    // Reporte de ventas en PDF
    @GetMapping("/sales/pdf")
    public ResponseEntity<byte[]> getVentasPdf() throws IOException, DocumentException {
        List<Sale> sales = reportService.getSales();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        document.add(new Paragraph("Reporte de Ventas"));
        document.add(new Paragraph("Fecha: " + LocalDate.now()));
        PdfPTable table = new PdfPTable(5);
        table.addCell("Fecha");
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Precio");
        table.addCell("Total");
        for (Sale sale : sales) {
            table.addCell(sale.getDate().toString());
            table.addCell(sale.getProduct().getName());
            table.addCell(String.valueOf(sale.getQuantity()));
            table.addCell(String.valueOf(sale.getProduct().getPrice()));
            table.addCell(String.valueOf(sale.getTotal()));
        }
        document.add(table);
        document.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(byteArrayOutputStream.toByteArray());
    }

    @GetMapping("/sales/excel")
    public ResponseEntity<byte[]> getVentasExcel() throws IOException {
        List<Sale> sales = reportService.getSales();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Ventas");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Fecha");
        row.createCell(1).setCellValue("Producto");
        row.createCell(2).setCellValue("Cantidad");
        row.createCell(3).setCellValue("Precio");
        row.createCell(4).setCellValue("Total");
        int rowIndex = 1;
        for (Sale sale : sales) {
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(sale.getDate().toString());
            row.createCell(1).setCellValue(sale.getProduct().getName());
            row.createCell(2).setCellValue(String.valueOf(sale.getQuantity()));
            row.createCell(3).setCellValue(String.valueOf(sale.getProduct().getPrice()));
            row.createCell(4).setCellValue(String.valueOf(sale.getTotal()));
            rowIndex++;
        }
        workbook.write(byteArrayOutputStream);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=ventas.xlsx").body(byteArrayOutputStream.toByteArray());
    }


}

