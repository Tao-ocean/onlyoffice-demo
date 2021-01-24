package cn.ght.demo.service;

import cn.ght.demo.entity.File;
import cn.ght.demo.mapper.FileMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Service("officeService")
public class OfficeService {

    @Resource
    private FileMapper fileMapper;

    public void saveFile(String url ) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String type = getFileExtension(fileName);
            String key = generateKey();
            File file = new File();
            String fileId = UUID.randomUUID().toString().replaceAll("-","");
            file.setFileId(fileId);
            file.setFileName(fileName);
            file.setFileType(type);
            file.setFileKey(key);
            file.setCreateId(1L);
            file.setUrl(url);
            file.setHistStatus(1);
            file.setCreated(new Date());
            this.fileMapper.insert(file);
    }

    public List<File> getFileList() {
        return this.fileMapper.selectList(null);
    }

    private String generateKey() {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<20;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    // 获取文件类型
    private String getFileExtension(String fileName) {
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExt.toLowerCase(Locale.ROOT);
    }

}
