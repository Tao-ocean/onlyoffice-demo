package cn.ght.demo.dto;

import cn.ght.demo.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Data
public class VersionDto {
    private String serverVersion;
    private String created;
    private Integer version;
    private List<VersionDto> changes;
    private User user;
    private String key;
}
