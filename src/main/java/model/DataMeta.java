package model;

public class DataMeta {
    String workbookBatchRootPath;
    String workbookBatchPathRegex;
    String[] workbookPaths;
    String outputPath;
    String sheetName;
    String sheetNo;
    String[] headLocs;
    String[] bodyLocs;
    String[] bodyLocs2;
    String headSeparator;
    String bodySeparator;

    public DataMeta() {
    }

    public String[] getBodyLocs2() {
        return bodyLocs2;
    }

    public void setBodyLocs2(String[] bodyLocs2) {
        this.bodyLocs2 = bodyLocs2;
    }

    public String getHeadSeparator() {
        return headSeparator;
    }

    public void setHeadSeparator(String headSeparator) {
        this.headSeparator = headSeparator;
    }

    public String getBodySeparator() {
        return bodySeparator;
    }

    public void setBodySeparator(String bodySeparator) {
        this.bodySeparator = bodySeparator;
    }

    public String getWorkbookBatchPathRegex() {
        return workbookBatchPathRegex;
    }

    public String getWorkbookBatchRootPath() {
        return workbookBatchRootPath;
    }

    public void setWorkbookBatchRootPath(String workbookBatchRootPath) {
        this.workbookBatchRootPath = workbookBatchRootPath;
    }

    public void setWorkbookBatchPathRegex(String workbookBatchPathRegex) {
        this.workbookBatchPathRegex = workbookBatchPathRegex;
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

    public String[] getHeadLocs() {
        return headLocs;
    }

    public void setHeadLocs(String[] headLocs) {
        this.headLocs = headLocs;
    }

    public String[] getBodyLocs() {
        return bodyLocs;
    }

    public void setBodyLocs(String[] bodyLocs) {
        this.bodyLocs = bodyLocs;
    }
}
