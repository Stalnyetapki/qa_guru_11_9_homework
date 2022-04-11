package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class UnpackingAndParsingZipFileTest {

    String XLSX_FILE_NAME = "Interkassa min&max (1).xlsx";
    String CSV_FILE_NAME = "left_balances_eb23ea1d-af1a-47d5-9429-9484d8056092_2022_03_28_11_16_45.csv";
    String PDF_FILE_NAME = "SQL_M_Gruber.pdf";
    String ZIP_FILE_PATH = "src\\test\\resources\\files\\test.zip";


    @Test
    void parseAndCheckZipFilesTest() throws IOException, CsvException {
        ZipFile zipFile = new ZipFile(ZIP_FILE_PATH);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            if (entry.getName().endsWith("csv")) {
                assertThat(entry.getName()).isEqualTo(CSV_FILE_NAME);
                InputStream inputStream = zipFile.getInputStream(entry);
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                List<String[]> content = reader.readAll();
                assertThat(content.get(0)).contains(
                        "RealMoney",
                        "BonusMoney",
                        "CombinedMoney");
                inputStream.close();

            } else if (entry.getName().endsWith("xlsx")) {
                assertThat(entry.getName()).isEqualTo(XLSX_FILE_NAME);
                InputStream inputStream = zipFile.getInputStream(entry);
                XLS xlsFile = new XLS(inputStream);
                assertThat(xlsFile.excel
                        .getSheetAt(0)
                        .getRow(3)
                        .getCell(0)
                        .getStringCellValue()).contains("advcash");
                inputStream.close();

            } else if (entry.getName().endsWith("pdf")) {
                assertThat(entry.getName()).isEqualTo(PDF_FILE_NAME);
                InputStream inputStream = zipFile.getInputStream(entry);
                PDF pdf = new PDF(inputStream);
                assertThat(pdf.numberOfPages).isEqualTo(291);
                inputStream.close();
            }
        }
    }
}

