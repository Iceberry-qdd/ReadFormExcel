import com.alibaba.excel.EasyExcel;
import model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Iceberry
 * @date 2022/7/22
 * @desc 读写表单形式Excel文件主类
 * @since 2.2
 */
public class ExcelDealer {
    private static final Logger logger = LoggerFactory.getLogger(ExcelDealer.class);
    private static final long DEFAULT_BATCH_SIZE = 1;

    /**
     * 入口函数，根据传入的配置文件路径configurationPath处理Excel表格，遇到错误则立即停止作业
     * 默认处理的文件数量为1
     *
     * @param configurationPath 配置文件路径
     */
    public static void deal(String configurationPath) {
        deal(configurationPath, DEFAULT_BATCH_SIZE);
    }

    /**
     * 入口函数，根据传入的配置文件路径configurationPath和处理数量BATCH_SIZE处理Excel表格，遇到错误则立即停止作业
     *
     * @param configurationPath 配置文件路径
     * @param BATCH_SIZE        要处理的文件数量
     */
    public static void deal(String configurationPath, long BATCH_SIZE) {
        deal(configurationPath, BATCH_SIZE, 0);
    }

    /**
     * 入口函数，根据传入的配置文件路径configurationPath和处理数量BATCH_SIZE处理Excel表格，遇到错误则立即停止作业
     *
     * @param configurationPath 配置文件路径
     * @param BATCH_SIZE        要处理的文件数量
     * @param offset            起始处理文件的偏移量
     */
    public static void deal(String configurationPath, long BATCH_SIZE, long offset) {
        Configuration config = ConfigurationParser.parse(configurationPath, StandardCharsets.UTF_8);

        String[] workbookPaths = getWorkbookPaths(config);
        config.setWorkbookPaths(workbookPaths);

        deleteOutputFileIfExisted(config.getOutputPath());

        for (long i = offset, jobCount = 0L; i < workbookPaths.length && jobCount < BATCH_SIZE; i++, jobCount++) {
            for (String workbookPath : workbookPaths) {
                logger.info("[job{}]正在处理工作表：{}", jobCount + 1, workbookPath);

                EasyExcel.read(workbookPath, new ExcelListener(config))
                        .sheet(config.getSheetNo())
                        .doRead();
            }
        }

        logger.info("所有表均处理完毕");
    }

    /**
     * 从dataMeta文件中获取要处理的workbook的路径。
     * 若规定了Excel工作表路径的batchRootPath属性，则按照正则表达式内容获取Excel文件列表，并赋给workbookPaths对象；
     * 若没有规定，则从dataMeta对象的getWorkbookPaths()方法获取。
     *
     * @param config dataMeta文件
     * @return workbook路径数组
     */
    private static String[] getWorkbookPaths(Configuration config) {
        String[] workbookPaths;
        if (config.getWorkbookPaths() == null || config.getWorkbookPaths().length == 0) {
            logger.info("开始批量读取workbook路径...");
            String batchRootPath = config.getWorkbookBatchRootPath();
            String batchPathRegex = config.getWorkbookBatchPathRegex();
            workbookPaths = getWorkbookPaths(batchRootPath, batchPathRegex);
        } else {
            workbookPaths = config.getWorkbookPaths();
        }
        return workbookPaths;
    }

    /**
     * 从给的<code>batchRootPath</code>参数和<code>batchPathRegex</code>参数批量提取workbook路径
     *
     * @param batchRootPath  要批量提取的根路径（文件夹）
     * @param batchPathRegex 要批量提取的文件名匹配规则
     * @return 提取出的workbook路径
     */
    private static String[] getWorkbookPaths(String batchRootPath, String batchPathRegex) {
        String[] workbookPaths;
        try (Stream<Path> paths = Files.walk(Paths.get(batchRootPath))) {
            List<String> pathList = new ArrayList<>();
            paths.filter(p -> !Files.isDirectory(p))
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".xls") || p.endsWith(".xlsx"))
                    .filter(p -> !p.matches(batchPathRegex))
                    .forEach(pathList::add);
            workbookPaths = new String[pathList.size()];
            for (int i = 0; i < pathList.size(); i++) {
                workbookPaths[i] = pathList.get(i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workbookPaths;
    }

    /**
     * 若输出文件已存在，则删除输出文件再进行处理；
     * 若删除失败，则直接退出程序；
     * 若输出文件不存在，则创建。
     *
     * @param outputFilePath 输出文件的路径
     */
    private static void deleteOutputFileIfExisted(String outputFilePath) {
        File file = new File(outputFilePath);
        if (file.exists()) {
            logger.warn("输出文件{}已存在，在工作前需要删除", file.getAbsolutePath());
            boolean isDelete = file.delete();
            if (isDelete) {
                logger.info("输出文件已删除，即将开始工作......");
            } else {
                logger.error("无法删除输出文件，可能是文件处于打开状态，请尝试关闭文件再次尝试！系统即将退出......");
                System.exit(0);
            }
        }
    }
}
