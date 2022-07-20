import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import model.DataMeta;

import java.nio.charset.StandardCharsets;

/**
 * @author Iceberry
 * @date 2022/7/19
 * @desc 测试用easyExcel读取数据
 * @since 1.0
 */
public class Main {
    public static final String INPUT_DATA_META_PATH = "C:\\Users\\DELL\\Documents\\Idea projects\\DataScript\\src\\main\\resources\\dataMeta.json";

    public static void main(String[] args) {
        DataMetaParser parser = new DataMetaParser();
        DataMeta dataMeta = parser.parse(INPUT_DATA_META_PATH, StandardCharsets.UTF_8);



        for (String workbookPath : dataMeta.getWorkbookPaths()) {
            EasyExcel.read(workbookPath, new ExcelListener(dataMeta))
                    .excelType(ExcelTypeEnum.XLS)
                    .sheet(2)
                    .doRead();
        }


    }
}
