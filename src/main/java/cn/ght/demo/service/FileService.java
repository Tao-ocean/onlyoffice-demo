package cn.ght.demo.service;

import cn.ght.demo.entity.File;
import cn.ght.demo.mapper.FileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: ght
 * @Date: 2021/1/20
 */
@Service("fileService")
public class FileService extends ServiceImpl<FileMapper, File> {
}
