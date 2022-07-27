import com.google.gson.Gson;
import model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Iceberry
 * @date 2022/7/20
 * @desc 配置文件编码器，仅支持Json格式。
 * @since 1.0
 */
class ConfigurationParser {
    private static final Gson gson = new Gson();
    private static final Logger logger= LoggerFactory.getLogger(ConfigurationParser.class);

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
            logger.error("在指定路径没有发现配置文件：{}",filePath);
            e.printStackTrace();
        }
        return config;
    }
}
