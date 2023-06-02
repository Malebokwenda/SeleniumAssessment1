package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;

public class Utilities {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public static void setupExcel(){

        try {
            workbook = new XSSFWorkbook("src/test/java/Data/ShopDemoTools_selenium1.xlsx");
            sheet = workbook.getSheet("Sheet1_TestData");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCellData(int rowNum, int cellNum){
        setupExcel();
        String cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
        return cellData;
    }

    public static int getCellInt(int rowNum, int cellNum){
        setupExcel();
        int cellData = (int) sheet.getRow(rowNum).getCell(cellNum).getNumericCellValue();
        return cellData;
    }
}
//
//    @DataProvider(name = "excelData")
//    public Object[][] readExcelData() throws IOException {
//        String filePath = "src/test/java/Data/ShopDemoTools_selenium1.xlsx";
//        String sheetName = "Sheet1_TestData";
//
//        FileInputStream fis = new FileInputStream(filePath);
//        Workbook workbook = new XSSFWorkbook(fis);
//        Sheet sheet = workbook.getSheet(sheetName);
//
//        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
//        int colCount = sheet.getRow(0).getLastCellNum();
//        Object[][] data = new Object[rowCount][colCount];
//
//        for (int i = 1; i <= rowCount; i++) {
//            Row row = sheet.getRow(i);
//            for (int j = 0; j < colCount; j++) {
//                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                CellType cellType = cell.getCellType();
//                if (cellType == CellType.STRING) {
//                    data[i - 1][j] = cell.getStringCellValue();
//                } else if (cellType == CellType.NUMERIC) {
//                    data[i - 1][j] = cell.getNumericCellValue();
//                } else if (cellType == CellType.BOOLEAN) {
//                    data[i - 1][j] = cell.getBooleanCellValue();
//                }
//            }
//        }
//
//        workbook.close();
//        fis.close();
//
//        return data;
//    }

//    @Test(dataProvider = "excelData")
//    public void testMethod(String username, String password, int email) {
//        // Use the data from the Excel sheet in your test method
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//        System.out.println("Email: " + email);
//    }
//
//






