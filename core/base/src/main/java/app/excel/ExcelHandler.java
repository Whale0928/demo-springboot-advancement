package app.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
@Service
public class ExcelHandler {
  private static final String EXCEL_DIR = "../../data/excel";
  private static final int MIN_ROWS = 150;
  private static final int MAX_ROWS = 250;
  private static final int COLUMNS = 30;
  private static final int CELL_DATA_SIZE = 2000;

  public String createDummyExcelFile() {
    log.info("더미 엑셀 파일 생성 시작");

    Path dirPath = Paths.get(EXCEL_DIR).toAbsolutePath().normalize();
    try {
      if (!Files.exists(dirPath)) {
        Files.createDirectories(dirPath);
        log.info("디렉토리 생성: {}", dirPath);
      }

      String fileName =
          "dummy_excel_"
              + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
              + ".xlsx";
      Path filePath = dirPath.resolve(fileName);

      try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("DummyData");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < COLUMNS; i++) {
          Cell cell = headerRow.createCell(i);
          cell.setCellValue("Column_" + (i + 1));
        }

        Random random = new Random();
        int rowCount = random.nextInt(MAX_ROWS - MIN_ROWS) + MIN_ROWS; // 최소 150행, 최대 250행

        for (int i = 1; i <= rowCount; i++) {
          Row row = sheet.createRow(i);
          for (int j = 0; j < COLUMNS; j++) {
            Cell cell = row.createCell(j);

            StringBuilder sb = new StringBuilder();
            int dataSize = random.nextInt(CELL_DATA_SIZE) + 50;
            for (int k = 0; k < dataSize; k++) {
              char c = (char) (random.nextInt(26) + 'a');
              sb.append(c);
            }
            cell.setCellValue(sb.toString());
          }

          if (i % (rowCount / 5) == 0) {
            log.info("엑셀 파일 생성 진행 중: {}%", (i * 100 / rowCount));
          }
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath.toFile())) {
          workbook.write(fileOut);
        }

        log.info("더미 엑셀 파일 생성 완료: {}", filePath);

        // 파일 크기 확인
        long fileSize = Files.size(filePath);
        log.info("생성된 파일 크기: {} MB", fileSize / (1024 * 1024));

        return filePath.toString();
      }
    } catch (IOException e) {
      log.error("엑셀 파일 생성 중 오류 발생", e);
      throw new RuntimeException("엑셀 파일 생성 실패", e);
    }
  }
}
