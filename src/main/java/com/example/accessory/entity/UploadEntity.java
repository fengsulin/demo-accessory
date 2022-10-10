package com.example.accessory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@TableName("db_upload")
@Data
public class UploadEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    //存在本地的地址
    private String location;

    //名称，业务中用到的名称，比如 ”档案模板“、”用户信息“、”登录记录“等等
    private String name;

    //保留文件原来的名字
    private String oldName;

    //描述（可以为空）
    private String description;

    private Date createTime;

    private Date updateTime;
}
