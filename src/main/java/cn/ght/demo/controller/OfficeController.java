package cn.ght.demo.controller;

import cn.ght.demo.service.OfficeService;
import cn.ght.demo.utils.FileUtils;
import cn.ght.demo.utils.ResultMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: ght
 * @Date: 2021/1/12
 */
@Slf4j
@RestController
@RequestMapping("/office/")
public class OfficeController {

    final private OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @RequestMapping("save")
    public String saveFile(MultipartFile file) {
        String url = FileUtils.upload(file);
        this.officeService.saveFile(url);
        return ResultMsg.success();
    }

    @RequestMapping("list")
    public String getFileList() {
        return ResultMsg.success(this.officeService.getFileList());
    }
}
