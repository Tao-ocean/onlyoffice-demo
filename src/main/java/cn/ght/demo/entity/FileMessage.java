package cn.ght.demo.entity;

import lombok.Data;

/**
 * @Author: ght
 * @Date: 2021/1/15
 */
@Data
public class FileMessage {
    private String fileName;
    private String fileExt;
    private String mode;
}
