import com.google.gson.Gson;
import exception.NotDirectoryException;
import exception.NullOutputPathException;
import exception.NullSheetException;
import exception.NullWorkbookException;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import model.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/**
 * @author Iceberry
 * @date 2022/7/20
 * @desc 配置文件编码器，仅支持Json格式。
 * @since 1.0
 */
class ConfigurationParser {
    private static final Gson gson = new Gson();

    /**
     * 默认的编码格式为UTF-8
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 根据给定的Json字符串解析成为配置类，默认使用UFT-8编码格式。
     *
     * @param jsonStr 待解析的Json字符串
     * @return 解析完成的配置类
     */
    protected static Configuration parse(String jsonStr) {
        return parse(jsonStr, DEFAULT_CHARSET);
    }

    /**
     * 根据给定的Json字符串和编码格式解析成为配置类。
     *
     * @param filePath 待解析的Json字符串
     * @param charset  使用的编码格式
     * @return 解析完成的配置类
     */
    protected static Configuration parse(String filePath, Charset charset) {
        Configuration config = new Configuration();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath), charset)) {
            String lineStr;
            StringBuilder sb = new StringBuilder();
            while ((lineStr = bufferedReader.readLine()) != null) {
                sb.append(lineStr);
            }
            config = gson.fromJson(sb.toString(), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkConfig(config);
        return config;
    }

    /**
     * 检查配置类的合规性，如不合规，则抛出对应的异常。
     *
     * @param config 待检查的配置类
     */
    private static void checkConfig(Configuration config) {
        checkSheetConfig(config.getSheetName(), config.getSheetNo());
        checkWorkbookConfig(config.getWorkbookPaths(), config.getWorkbookBatchRootPath(), config.getWorkbookBatchPathRegex());
        checkOutputPath(config.getOutputPath());
    }

    /**
     * 检查配置类中有关sheet项的合规性，若不合规，则抛出对应异常，检查标准如下：
     * 1. sheetName和sheetNo两者不能同时为null；
     * 2. sheetNo字符串中应只包含数字，且可被转化为整数。
     *
     * @param sheetName 表名称
     * @param sheetNo   表编号
     */
    private static void checkSheetConfig(String sheetName, String sheetNo) {
        if (sheetName == null && sheetNo == null) {
            throw new NullSheetException("配置文件中的sheetName属性和sheetNo属性应至少指定一个！");
        }

        if (sheetName == null) {
            Integer.parseInt(sheetNo);
            return;
        }

        if (sheetName.equals("") && sheetNo.equals("")) {
            throw new NullSheetException("配置文件中的sheetName属性和sheetNo属性应至少指定一个！");
        }
    }

    /**
     * 检查配置类中有关workbook项的合规性，若不合规，则抛出对应异常，检查标准如下：
     * 1. workbookPaths为null或其length为0时，workbookBatchRootPath不能为null;
     * 2. workbookBatchRootPath为null时，workbookPaths不能为null或length为0；
     * 3. 当workbookBatchRootPath不为null时，workbookBatchPathRegex应是合法的正则表达式。
     *
     * @param workbookPaths          要手动指定处理的工作簿路径数组
     * @param workbookBatchRootPath  要批量获得工作簿文件路径的根目录
     * @param workbookBatchPathRegex 要批量获得工作簿文件路径的排除规则，正则表达式
     */
    private static void checkWorkbookConfig(String[] workbookPaths, String workbookBatchRootPath, String workbookBatchPathRegex) {
        if ((workbookPaths == null || workbookPaths.length == 0) && (workbookBatchRootPath == null|| workbookBatchRootPath.equals(""))) {
            throw new NullWorkbookException("配置文件中的workbookPaths属性和workbookBatchRootPath属性应至少指定一个！");
        }

        if (workbookBatchRootPath != null) {
            RegExpFactory.validate(workbookBatchPathRegex, "m"); //XXX flag不确定是否正确
        }
    }

    /**
     * 检查配置类中outputPath项的合规性，若不合规，则抛出对应异常，检查标准如下：
     * 1. outputPath不应为null
     * 2. outputPath应为文件
     *
     * @param outputPath 待检查的输出文件路径
     */
    private static void checkOutputPath(String outputPath) {
        if (outputPath == null || outputPath.equals("")) {
            throw new NullOutputPathException("输出文件路径outputPath属性为空！");
        }

        if (isDirectory(outputPath)){
            throw new NotDirectoryException("outputPath指定的字段不是一个文件！");
        }
    }

    /**
     * 检查给定的路径是否是文件夹
     * @param path 待校验的路径
     * @return 校验结果，若path是文件夹，返回true
     */
    private static boolean isDirectory(String path){
        File file=new File(path);
        return file.isDirectory();
    }
}
