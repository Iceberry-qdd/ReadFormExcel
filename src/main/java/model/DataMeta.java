package model;

import java.util.List;

public class DataMeta {
    String[] workbookPaths;
    String outputPath;
    String sheetName;
    String sheetNo;
    List<String> headLocs;
    List<String> bodyLocs;

    public DataMeta() {
    }

    public DataMeta(String[] workbookPaths, String outputPath, String sheetName, String sheetNo, List<String> headLocs, List<String> bodyLocs) {
        this.workbookPaths = workbookPaths;
        this.outputPath = outputPath;
        this.sheetName = sheetName;
        this.sheetNo = sheetNo;
        this.headLocs = headLocs;
        this.bodyLocs = bodyLocs;
    }

    public String[] getWorkbookPaths() {
        return workbookPaths;
    }

    public void setWorkbookPaths(String[] workbookPaths) {
        this.workbookPaths = workbookPaths;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
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

    public List<String> getHeadLocs() {
        return headLocs;
    }

    public void setHeadLocs(List<String> headLocs) {
        this.headLocs = headLocs;
    }

    public List<String> getBodyLocs() {
        return bodyLocs;
    }

    public void setBodyLocs(List<String> bodyLocs) {
        this.bodyLocs = bodyLocs;
    }


}
