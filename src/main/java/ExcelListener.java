import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Iceberry
 * @date 2022/7/19
 * @desc 测试用easyExcel读取数据的监听器
 * @since 1.0
 */
class ExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger logger = LoggerFactory.getLogger(ExcelListener.class);
    private final List<Map<Integer, String>> dataList;
    private Configuration config;


    protected ExcelListener() {
        this.dataList = new ArrayList<>();
    }

    protected ExcelListener(Configuration config) {
        this.dataList = new ArrayList<>();
        this.config = config;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        dataList.add(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveAsExcel();
        //logger.info("该Excel表处理完成！");
    }

    /**
     * 将数据写入Excel中，若文件存在，则追加；否则新建文件
     */
    protected void saveAsExcel() {
        List<List<String>> head = getTableHead(config);
        List<List<String>> body = getTableBody(config);

        File file = new File(config.getOutputPath());
        File tempFile = new File("temp.xlsx");

        if (file.exists()) {
            //logger.warn("输出文件已存在，将追加写");
            EasyExcel.write(file)
                    .needHead(false)
                    .withTemplate(file)
                    .file(tempFile)
                    .sheet(config.getSheetNo())
                    .doWrite(body);
        } else {
            EasyExcel.write(config.getOutputPath())
                    .excelType(ExcelTypeEnum.XLSX)
                    .head(head)
                    .sheet(config.getSheetNo())
                    .doWrite(body);
        }

        if (tempFile.exists()) {
            file.delete();
            tempFile.renameTo(file);
        }
    }

    /**
     * 根据Excel中单元格的行号和列号获取该单元格的内容
     *
     * @param colIndex 列号
     * @param rowIndex 行号
     * @return 该单元格的内容
     */
    protected String getCellContent(int colIndex, int rowIndex) {
        return dataList.get(rowIndex).get(colIndex);
    }

    /**
     * 根据Excel中单元格的坐标获取该单元格的内容
     *
     * @param coordinate 单元格坐标，列需使用大写字母表示
     * @return 该单元格的内容
     */
    protected String getCellContent(String coordinate) {
        int colIndex = 0, rowIndex = 0;
        rowIndex = Integer.parseInt(coordinate.replaceAll("[A-Z]+", "")) - 1;
        String colStr = coordinate.replaceAll("\\d", "");

        for (int i = 0; i < colStr.length(); i++) {
            if (i == colStr.length() - 1) {
                colIndex += colStr.charAt(i) - 'A';
            } else {
                colIndex += 26 * (colStr.length() - i);
            }
        }

        return getCellContent(colIndex, rowIndex);
    }

    /**
     * 从dataMeta中获取表头
     *
     * @param config 元数据
     * @return 表头列表
     */
    protected List<List<String>> getTableHead(Configuration config) {
        List<List<String>> list = ListUtils.newArrayList();

        for (String headLoc : config.getHeadLocs()) {
            String headString = getCellContent(headLoc);
            List<String> head = new ArrayList<>(Collections.singleton(headString));
            list.add(head);
        }
        return list;
    }

    /**
     * 从dataMeta中获取表内容
     *
     * @param config 元数据
     * @return 表内容列表
     */
    protected List<List<String>> getTableBody(Configuration config) {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> body = new ArrayList<>();
        String[] bodyLocs = config.getBodyLocs();

        for (int i = 0; i < bodyLocs.length; i++) {
            String content = getCellContent(bodyLocs[i]);
            body.add(content);
        }
        list.add(body);
        return list;
    }
}
