package cn.ght.demo.entity;

import cn.ght.demo.dto.HistDataDto;
import cn.ght.demo.dto.HistoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: ght
 * @Date: 2021/1/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandleEditModel {
    private String callbackUrl;
    private Map<String,HistDataDto> histData;
    private HistoryDto history;
}
