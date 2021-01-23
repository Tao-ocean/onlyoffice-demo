package cn.ght.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: ght
 * @Date: 2021/1/20
 */
@Data
public class HistoryDto {
    private List<VersionDto> history;
    private Integer currentVersion;
}
