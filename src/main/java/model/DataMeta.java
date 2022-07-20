package model;

import java.util.List;

public class DataMeta {
    String workbookPath;

    String outputPath;
    String sheetName;
    String sheetNo;
    List<DataScheme> dataSchemes;

    public DataMeta() {
    }

    public DataMeta(String workbookPath, String outputPath, String sheetName, String sheetNo, List<DataScheme> dataSchemes) {
        this.workbookPath = workbookPath;
        this.outputPath = outputPath;
        this.sheetName = sheetName;
        this.sheetNo = sheetNo;
        this.dataSchemes = dataSchemes;
    }

    public String getWorkbookPath() {
        return workbookPath;
    }

    public void setWorkbookPath(String workbookPath) {
        this.workbookPath = workbookPath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public List<DataScheme> getDataSchemes() {
        return dataSchemes;
    }

    public void setDataSchemes(List<DataScheme> dataSchemes) {
        this.dataSchemes = dataSchemes;
    }
}
