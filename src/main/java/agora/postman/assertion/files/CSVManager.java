package agora.postman.assertion.files;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class CSVManager {

    public static List<List<String>> readCSV(String path, Boolean includeFirstRow, char delimiter) {
        List<List<String>> rows = readCSV(path, delimiter);
        if (!includeFirstRow)
            rows.remove(0);
        return rows;
    }

    public static List<List<String>> readCSV(String path, char delimiter) {
        List<List<String>> rows = new ArrayList<>();

        Reader in;
        try {
            in = new FileReader(path);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(delimiter).parse(in);
            for (CSVRecord record : records) {
                List<String> currentRow = new ArrayList<>();
                for (String field: record)
                    currentRow.add(field);
                rows.add(currentRow);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file");
        }

        return rows;
    }

    public static CSVRecord getCSVRecord(String line) {
        CSVRecord res;
        try {
            List<CSVRecord> csvRecords =  CSVParser.parse(line, CSVFormat.EXCEL).getRecords();
            if(csvRecords.size() != 1) {
                throw new IndexOutOfBoundsException("Each line should contain only one record");
            }
            res = csvRecords.get(0);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file");
        }
        return res;
    }

}
