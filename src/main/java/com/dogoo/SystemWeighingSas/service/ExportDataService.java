package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportDataService {

    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<WeightSlip> listAccounts;

    public ExportDataService(List<WeightSlip> listAccounts) {
        this.listAccounts = listAccounts;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Phiếu");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Mã Phiếu ", style);
        createCell(row, 1, "Khách hàng", style);
        createCell(row, 2, "Biển số xe", style);
        createCell(row, 3, "Tên hàng", style);
        createCell(row, 4, "Khối lượng", style);
        createCell(row, 5, "Giờ có tải", style);
        createCell(row, 6, "Giờ không tải", style);
        createCell(row, 7, "Ngày cân", style);
        createCell(row, 8, "Thành tiền", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        AtomicInteger rowCount = new AtomicInteger(1);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        listAccounts.forEach(user -> {
            Row row = sheet.createRow(rowCount.getAndIncrement());
            int columnCount = 0;

            createCell(row, columnCount++, user.getMaPhieu(), style);
            createCell(row, columnCount++, user.getKhachHang(), style);
            createCell(row, columnCount++, user.getSoXe(), style);
            createCell(row, columnCount++, user.getTenHang(), style);
            createCell(row, columnCount++, user.getHang(), style);
            createCell(row, columnCount++,
                    user.getGioCoTai().toLocalDateTime().format(formatterTime), style);
            createCell(row, columnCount++, user.getGioKTai().toLocalDateTime().format(formatterTime), style);
            createCell(row, columnCount++,
                    user.getNgayCan().toLocalDateTime().format(formatterDate), style);
            createCell(row, columnCount++, user.getThanhTien(), style);
        });
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
