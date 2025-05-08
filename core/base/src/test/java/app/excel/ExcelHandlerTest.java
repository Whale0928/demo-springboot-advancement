package app.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelHandlerTest {

  @Test
  void createDummyExcelFile() {
    ExcelHandler excelHandler = new ExcelHandler();
    String filePath = excelHandler.createDummyExcelFile();
    assertNotNull(filePath, "파일 경로가 null이 아닙니다.");
    assertTrue(filePath.endsWith(".xlsx"), "파일 확장자가 .xlsx입니다.");
  }
}
