import com.google.gson.Gson;
import model.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Iceberry
 * @date 2022/7/20
 * @desc 配置文件编码器，仅支持Json格式
 * @since 1.0
 */
class ConfigurationParser {
    private final Gson gson;

    protected ConfigurationParser() {
        this.gson = new Gson();
    }

    protected Configuration parse(String jsonStr) {
        return gson.fromJson(jsonStr, Configuration.class);
    }

    protected Configuration parse(String filePath, Charset charset) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath), charset)) {
            String lineStr;
            StringBuilder sb = new StringBuilder();
            while ((lineStr = bufferedReader.readLine()) != null) {
                sb.append(lineStr);
            }
            return parse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Configuration();
    }
}
