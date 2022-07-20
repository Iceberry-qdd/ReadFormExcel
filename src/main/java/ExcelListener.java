import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import model.DataMeta;

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
public class ExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    private final List<Map<Integer, String>> dataList;
    private DataMeta dataMeta;


    public ExcelListener() {
        this.dataList = new ArrayList<>();
    }

    public ExcelListener(DataMeta dataMeta) {
        this.dataList = new ArrayList<>();
        this.dataMeta = dataMeta;
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
        System.out.println("该Excel表处理完成！");
    }

    /**
     * 将数据写入Excel中，若文件存在，则追加；否则新建文件
     */
    public void saveAsExcel() {
        List<List<String>> head = getTableHead(dataMeta);
        List<List<String>> body = getTableBody(dataMeta);

        File file=new File(dataMeta.getOutputPath());
        File tempFile=new File("temp.xlsx");

        if (file.exists()){
            System.out.println("文件已存在");
            EasyExcel.write(file)
                    .needHead(false)
                    .withTemplate(file)
                    .file(tempFile)
                    .sheet(dataMeta.getSheetName())
                    .doWrite(body);
        }else {
            EasyExcel.write(dataMeta.getOutputPath())
                    .excelType(ExcelTypeEnum.XLSX)
                    .head(head)
                    .sheet(dataMeta.getSheetName())
                    .doWrite(body);
        }

        if (tempFile.exists()){
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
    public String getCellContent(int colIndex, int rowIndex) {
        return dataList.get(rowIndex).get(colIndex);
    }

    /**
     * 根据Excel中单元格的坐标获取该单元格的内容
     *
     * @param coordinate 单元格坐标，列需使用大写字母表示
     * @return 该单元格的内容
     */
    public String getCellContent(String coordinate) {
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
     * @param dataMeta 元数据
     * @return 表头列表
     */
    private List<List<String>> getTableHead(DataMeta dataMeta) {
        List<List<String>> list = ListUtils.newArrayList();

        for (String headLoc : dataMeta.getHeadLocs()) {
            String headString = getCellContent(headLoc);
            List<String> head = new ArrayList<>(Collections.singleton(headString));
            list.add(head);
        }
        return list;
    }

    /**
     * 从dataMeta中获取表内容
     *
     * @param dataMeta 元数据
     * @return 表内容列表
     */
    private List<List<String>> getTableBody(DataMeta dataMeta) {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> body = new ArrayList<>();

        for (String bodyLoc : dataMeta.getBodyLocs()) {
            String content = getCellContent(bodyLoc);
            body.add(content);
        }
        list.add(body);
        return list;
    }
}
