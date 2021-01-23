package cn.ght.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Data
@TableName("tb_file")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class File {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileId;
    private String fileName;
    private String fileKey;
    private String fileType;
    private String url;
    private Integer histStatus;
    private Long createId;
    private Date created;
    @TableLogic
    private Integer delFlag;
}
