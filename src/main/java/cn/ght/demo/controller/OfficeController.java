package cn.ght.demo.controller;

import cn.ght.demo.service.OfficeService;
import cn.ght.demo.utils.ResultMsg;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ght
 * @Date: 2021/1/12
 */
@Slf4j
@RestController
@RequestMapping("/office/")
public class OfficeController {

    public static final String UPLOAD_PATH = "http://192.168.0.120:9570/group1/upload";

    final private OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @RequestMapping("save")
    public String saveFile(MultipartFile file) {

        String result = "";
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(), file.getOriginalFilename());
            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
            params.put("output", "json");
            String resp = HttpUtil.post(UPLOAD_PATH, params);
            log.info("resp==>{}", resp);
            result = resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.officeService.saveFile(result);
        return ResultMsg.success();
    }

    @RequestMapping("list")
    public String getFileList() {
        return ResultMsg.success(this.officeService.getFileList());
    }
}
