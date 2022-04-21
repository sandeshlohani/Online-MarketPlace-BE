package edu.miu.cs545.waa.util;

import edu.miu.cs545.waa.dto.OrderDto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * to create downloadable excel sheets with order details
 */
public class ExcelGenerator {

  public static ByteArrayInputStream createExcelReport(OrderDto orderDTO) throws IOException {
    try (
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
    ) {
      CreationHelper createHelper = workbook.getCreationHelper();
      //for whole sheet
      Sheet sheet = workbook.createSheet("Order Details");
      sheet.setColumnWidth(0, 8000);
      sheet.setColumnWidth(1, 8000);
      sheet.setColumnWidth(2, 2700);
      //for header font
      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLACK.getIndex());
      //for header styling
      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);
      //for title styling
      CellStyle titleCellStyle = workbook.createCellStyle();
      titleCellStyle.setFont(headerFont);
      Row titleRow = sheet.createRow(0);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue("Order Date: " + orderDTO.getOrderDate());
      titleCell.setCellStyle(headerCellStyle);
      //for event Id row
      Row userIdRow = sheet.createRow(1);
      Cell userIdCell = userIdRow.createCell(0);
      userIdCell.setCellValue("User Id: " + orderDTO.getUserId());
      userIdCell.setCellStyle(headerCellStyle);
      //for event Name row
      Row shippingAddressRow = sheet.createRow(2);
      Cell shippingAddressCell = shippingAddressRow.createCell(0);
      shippingAddressCell.setCellValue("Shipping Address: " + orderDTO.getShippingAddress().toString());
      shippingAddressCell.setCellStyle(headerCellStyle);

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }
}
