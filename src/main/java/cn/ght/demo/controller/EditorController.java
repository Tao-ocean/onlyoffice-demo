package cn.ght.demo.controller;

import cn.ght.demo.entity.File;
import cn.ght.demo.entity.HistData;
import cn.ght.demo.service.EditorService;
import cn.ght.demo.service.FileService;
import cn.ght.demo.service.HistDataService;
import cn.ght.demo.utils.FileUtils;
import cn.ght.demo.utils.ResultMsg;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @Author: ght
 * @Date: 2021/1/13
 */
@Slf4j
@RestController
@RequestMapping("/test/")
public class EditorController {

    @Value("${fileserver.upload}")
    private String upload;

    @RequestMapping("value")
    public Map<String,Object> test() {
        return Collections.singletonMap("msg",upload);
    }

    @Resource
    private FileService fileService;

    @Resource
    private HistDataService histDataService;
    @Resource
    private EditorService editorService;

    @RequestMapping("editor/{id}")
    public String editorFile(@PathVariable Long id) {
        return ResultMsg.success(this.editorService.getEditMsg(id));
    }
    @RequestMapping("save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        System.out.println("===========保存文件=============");
        String fileId = request.getParameter("fileId");
        System.out.println(fileId);
        try {
            writer = response.getWriter();
            Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\n");
            String body = scanner.hasNext() ? scanner.next() : "";
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(body);
            System.out.println("===saveEditedFile:" + jsonObject.get("status"));

            /*
                0 - no document with the key identifier could be found,
                1 - document is being edited,
                2 - document is ready for saving,
                3 - document saving error has occurred,
                4 - document is closed with no changes,
                6 - document is being edited, but the current document state is saved,
                7 - error has occurred while force saving the document.
             **/
            long status = (long) jsonObject.get("status");
            if (status == 2 || status ==3) {
                String downloadUri = (String) jsonObject.get("url");
                String fileName = downloadUri.substring(downloadUri.lastIndexOf("/") + 1);
                // 更改之后的文件保存
                String saveUrl = FileUtils.downloadToFile(downloadUri,"out.dock");
                // diff文件
                String changesUrl = (String) jsonObject.get("changesurl");
                String diffUrl = FileUtils.downloadToFile(changesUrl, "diff.zip");

                // history
                String changeshistory = (String) jsonObject.get("changeshistory");
                System.out.println("changeshistory========>"+changeshistory);
                JSONObject history = (JSONObject) jsonObject.get("history");
                System.out.println("history=====>"+history);
                // key
                String key = (String) jsonObject.get("key");
                System.out.println(key);
                File preFile = this.fileService.getOne(new QueryWrapper<File>().eq("file_id", fileId));

                UpdateWrapper<File> wrapper = new UpdateWrapper<>();
                wrapper.eq("file_id",fileId)
                        .set("file_key",key)
                        .set("hist_status",0)
                        .set("url",saveUrl);
                this.fileService.update(wrapper);
                List<HistData> histList = this.histDataService.list(new QueryWrapper<HistData>().eq("file_id", fileId));
                if (histList.size()==0) {
                    int version = 0;
                    HistData v0 = new HistData();
                    v0.setFileId(fileId);
                    v0.setVersion(version);
                    v0.setFileKey(preFile.getFileKey());
                    v0.setDelFlag(1);
                    HistData v1 = new HistData();
                    v1.setVersion(version+1);
                    v1.setFileId(fileId);
                    v1.setFileKey(key);
                    v1.setChangesUrl(diffUrl);
                    v1.setPreKey(preFile.getFileKey());
                    v1.setPreUrl(preFile.getUrl());
                    v1.setDelFlag(1);
                    List<HistData> list = Arrays.asList(v0, v1);
                    this.histDataService.saveBatch(list);
                } else {
                    Integer max = histList.stream().map(HistData::getVersion).max(Integer::compareTo).orElse(1);
                    HistData data = new HistData();
                    data.setVersion(max+1);
                    data.setFileId(fileId);
                    data.setFileKey(key);
                    data.setChangesUrl(diffUrl);
                    data.setPreKey(preFile.getFileKey());
                    data.setPreUrl(preFile.getUrl());
                    data.setDelFlag(1);
                    this.histDataService.save(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.write("{\"error\":0}");
    }
}
