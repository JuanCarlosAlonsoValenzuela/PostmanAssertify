package agora.postman.assertion.mutation;

import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static agora.postman.assertion.files.CSVManager.getCSVRecord;


public class MutatedTestCaseFileManager {

    private final Integer testCaseIdIndex;
    private final Integer operationIdIndex;
    private final Integer pathIndex;
    private final Integer httpMethodIndex;
    private final Integer headerParametersIndex;
    private final Integer pathParametersIndex;
    private final Integer queryParametersIndex;
    private final Integer formParametersIndex;
    private final Integer bodyParameterIndex;
    private final Integer statusCodeIndex;
    private final Integer responseBodyIndex;

    public MutatedTestCaseFileManager(String header) throws IOException {

        CSVRecord headers = getCSVRecord(header);

        this.testCaseIdIndex = getIndexOfElementInHeaders(headers, "testCaseId");
        this.operationIdIndex = getIndexOfElementInHeaders(headers,"operationId");
        this.pathIndex = getIndexOfElementInHeaders(headers,"path");
        this.httpMethodIndex = getIndexOfElementInHeaders(headers,"httpMethod");
        this.headerParametersIndex = getIndexOfElementInHeaders(headers,"headerParameters");
        this.pathParametersIndex = getIndexOfElementInHeaders(headers, "pathParameters");
        this.queryParametersIndex = getIndexOfElementInHeaders(headers, "queryParameters");
        this.formParametersIndex = getIndexOfElementInHeaders(headers, "formParameters");
        this.bodyParameterIndex = getIndexOfElementInHeaders(headers, "bodyParameter");
        this.statusCodeIndex = getIndexOfElementInHeaders(headers, "statusCode");
        this.responseBodyIndex = getIndexOfElementInHeaders(headers,"responseBody");

    }

    public static List<MutatedTestCase> readMutatedTestCasesFromPath(String testCasesFilePath) {

        List<MutatedTestCase> res = new ArrayList<>();

        try {
            // Read test cases
            File testCasesFile = new File(testCasesFilePath);
            FileReader testCasesFileReader = new FileReader(testCasesFile);
            BufferedReader testCasesBR = new BufferedReader(testCasesFileReader);
            String testCasesLine = "";

            // The first line must be the header
            String header = testCasesBR.readLine();
            if (header == null) {
                throw new NullPointerException("The csv file containing the mutated test cases is empty");
            }

            MutatedTestCaseFileManager testCaseFileManager = new MutatedTestCaseFileManager(header);

            while((testCasesLine = testCasesBR.readLine()) != null) {
                MutatedTestCase testCase = testCaseFileManager.getMutatedTestCase(getCSVRecord(testCasesLine));
                res.add(testCase);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return res;
    }

    public MutatedTestCase getMutatedTestCase(CSVRecord row) {

        Map<String, String> headerParameters = stringToMap(row.get(this.headerParametersIndex));
        Map<String, String> pathParameters = stringToMap(row.get(this.pathParametersIndex));
        Map<String, String> queryParameters = stringToMap(row.get(this.queryParametersIndex));
        Map<String, String> formParameters = stringToMap(row.get(this.formParametersIndex));

        return new MutatedTestCase(row.get(this.testCaseIdIndex), row.get(this.operationIdIndex), row.get(this.pathIndex),
                row.get(this.httpMethodIndex), headerParameters, pathParameters, queryParameters,
                formParameters, row.get(this.bodyParameterIndex),
                row.get(this.statusCodeIndex), row.get(this.responseBodyIndex));

    }

    private static int getIndexOfElementInHeaders(CSVRecord headers, String header){
        for(int i = 0; i < headers.size(); i++) {
            if(headers.get(i).equalsIgnoreCase(header)) {
                return i;
            }
        }
        throw new NullPointerException("Element " + header + " not found in the csv headers");
    }

    private static Map<String, String> stringToMap(String str) {
        if(str.trim().isEmpty()){
            return new HashMap<>();
        }else {
            Map<String, String> res = Arrays.asList(str.split("\\s*;\\s*")).stream().map(s -> s.split("="))
                    .collect(Collectors.toMap(a -> a[0], a -> a[1]));
            // Remove all new line chars (\n and \r). The parameter value line of the dTrace must be specified in a single line
            res.replaceAll((k,v) -> removeNewLineChars(v));
            return res;
        }
    }

    public static String removeNewLineChars(String str) {
        String res = str.replace("\n","\\n");
        res = res.replace("\r","\\r");
        return res;
    }

}
