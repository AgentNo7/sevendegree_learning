package com.sevendegree.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Integer id;

    private String fileName;

    private Integer userId;

    private String url;

    private String desc;

    private Date createTime;

    private Date updateTime;

}