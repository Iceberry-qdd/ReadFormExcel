import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import model.DataMeta;
import model.DataScheme;

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
        /*
        Integer rowIndex = context.readRowHolder().getRowIndex();
        //context.readRowHolder().setCellMap();

        System.out.println(data);
        switch (rowIndex) {
            case 1:
                model.setAddress(data.get(1));
                model.setNo(data.get(5));
                model.setSchedule(data.get(7).split("：")[1]);// XXX 注意范围！

                break;
            case 2:
                model.setLandLocated(data.get(1));
                model.setLandUser(data.get(6));
                break;
            case 3:
                model.setLandTenureSource(data.get(1));
                model.setLandCertificateUse(data.get(6));
                break;
            case 4:
                model.setLandUseCertificateNo(data.get(1));
                model.setLandActualUse(data.get(6));
                break;
            case 5:
                model.setLandUserRightType(data.get(1));
                model.setLotNo(data.get(6));
                break;
            case 6:
                model.setLandArea(data.get(2));
                model.setApportionedArea(data.get(4));
                model.setExclusiveUseArea(data.get(7));
                break;
            case 7:
                model.setBuildingCount(data.get(2));
                model.setTotalBuildingCount(data.get(4));
                model.setTotalCottageCount(data.get(7));
                break;
            case 8:
                model.setRemark(data.get(2));
                break;
            default:
        }

         */

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        dataList.add(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveAsExcel();
    }

    /**
     * 将数据写入Excel中
     */
    public void saveAsExcel() {
        List<List<String>> head = getTableHead(dataMeta);
        List<List<String>> body = getTableBody(dataMeta);

        EasyExcel.write(dataMeta.getOutputPath())
                .excelType(ExcelTypeEnum.XLSX)
                .head(head)
                .sheet(dataMeta.getSheetName())
                .doWrite(body);
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

        for (int i = colStr.length() - 1; i >= 0; i--) {
            colIndex += 26 * i + colStr.charAt(i) - 'A';
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

        for (DataScheme dataScheme : dataMeta.getDataSchemes()) {
            String headString = getCellContent(dataScheme.getCoordinate().getTitle());
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

        for (DataScheme scheme : dataMeta.getDataSchemes()) {
            String content = getCellContent(scheme.getCoordinate().getContent());
            body.add(content);
        }
        list.add(body);
        return list;
    }
}
