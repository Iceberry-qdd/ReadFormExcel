package model;

/**
 * @author Iceberry
 * @date 2022/7/19
 * @desc 配置文件映射实体类
 * @since 1.0
 */
public class Configuration {
    /**
     * 待批处理的工作簿根文件夹
     */
    private String workbookBatchRootPath;
    /**
     * 待批处理的工作簿正则匹配规则，这里匹配到的是要丢弃的文件
     */
    private String workbookBatchPathRegex;
    /**
     * 手动指定待处理的工作簿路径
     */
    private String[] workbookPaths;
    /**
     * 输出结果文件路径
     */
    private String outputPath;
    /**
     * 要处理的sheet编号
     */
    private String sheetNo;
    /**
     * 手动指定的标题所在单元格，按字符串数组提供
     */
    private String[] headLocs;
    /**
     * 手动指定的内容所在单元格，按字符串数组提供，需与位置对应
     */
    private String[] bodyLocs;

    public Configuration() {
    }

    public String getWorkbookBatchRootPath() {
        return workbookBatchRootPath;
    }

    public void setWorkbookBatchRootPath(String workbookBatchRootPath) {
        this.workbookBatchRootPath = workbookBatchRootPath;
    }

    public String getWorkbookBatchPathRegex() {
        return workbookBatchPathRegex;
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
