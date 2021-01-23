package cn.ght.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Data
public class HistDataDto {
    private Integer version;
    private String key;
    private String url;
    private String changesUrl;
    private HistDataDto previous;
}
