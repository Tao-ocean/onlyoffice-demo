package cn.ght.demo.service;

import cn.ght.demo.entity.File;
import cn.ght.demo.mapper.FileMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Service("officeService")
public class OfficeService {

    @Resource
    private FileMapper fileMapper;

    public void saveFile(String fileMsg ) {
        System.out.println(fileMsg);
        try {
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(fileMsg);
            String url = (String) jsonObj.get("url");
            System.out.println("=======>"+url);

            String fileName = url.substring(url.lastIndexOf("/") + 1);
            System.out.println(fileName);
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            System.out.println("=======>"+type);
            String key = generateKey();
            System.out.println("========>"+key);
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

        } catch (ParseException e) {
            e.printStackTrace();
        }
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

}
