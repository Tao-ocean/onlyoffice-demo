package cn.ght.demo.utils;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ght
 * @Date: 2021/1/24
 */
@Component
public class FileUtils {

    @Value("${fileserver.upload}")
    private String UPLOAD_PATH;

    private static String path;

    @PostConstruct
    public void init() {
        path = UPLOAD_PATH;
    }

    public static String upload(MultipartFile file) {
        String result = "";
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(), file.getOriginalFilename());
            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
            params.put("output", "json");
            String resp = HttpUtil.post(path, params);
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resp);
            result = (String) jsonObject.get("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String downloadToFile(String downloadUrl,String fileName) throws IOException {
        URL url = new URL(downloadUrl);
        java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream is = connection.getInputStream();
        MockMultipartFile multipartFile = new MockMultipartFile(fileName,fileName,"",is);
        return upload(multipartFile);
    }

}
