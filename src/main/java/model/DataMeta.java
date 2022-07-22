package model;

/**
 * @author Iceberry
 * @date 2022/7/19
 * @desc 配置文件映射实体类
 * @since 1.0
 */
public class DataMeta {
    /**
     * 待批处理的工作簿根文件夹
     */
    String workbookBatchRootPath;
    /**
     * 待批处理的工作簿正则匹配规则，这里匹配到的是要丢弃的文件
     */
    String workbookBatchPathRegex;
    /**
     * 手动指定待处理的工作簿路径
     */
    String[] workbookPaths;
    /**
     * 输出结果文件路径
     */
    String outputPath;
    /**
     * 要处理的sheet名称（若有自定义）
     */
    String sheetName;
    /**
     * 要处理的sheet编号
     */
    String sheetNo;
    /**
     * 手动指定的标题所在单元格，按字符串数组提供
     */
    String[] headLocs;
    /**
     * 手动指定的内容所在单元格，按字符串数组提供，需与位置对应
     */
    String[] bodyLocs;
    /**
     * 手动指定的内容所在单元格备用，按字符串数组提供，需与位置对应，若查询时bodyLocs给出的单元格内容为空，则尝试在本数组中查询
     */
    String[] bodyLocs2;
    /**
     * 标题需要分割的分隔符，取分隔后第一个分隔符前内容作为标题，若不指定或找不到该分隔符，则不进行分割
     */
    String headSeparator;
    /**
     * 内容需要分割的分隔符，取分隔后第一个分隔符后内容作为标题，若不指定或找不到该分隔符，则不进行分割
     */
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
