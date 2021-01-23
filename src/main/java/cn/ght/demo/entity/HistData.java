package cn.ght.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: ght
 * @Date: 2021/1/18
 */
@Data
@TableName("tb_hist_data")
public class HistData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileId;
    private String fileKey;
    private String preKey;
    private Integer version;
    private String changesUrl;
    private String preUrl;
    private Date created;
    private Integer updateId;
    @TableLogic
    private Integer delFlag;
}
