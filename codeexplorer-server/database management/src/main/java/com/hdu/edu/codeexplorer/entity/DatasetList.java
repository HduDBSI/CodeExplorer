package com.hdu.edu.codeexplorer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class DatasetList {
    // 序号
    private Integer id;

    // 数据集名称
    private String dataset;

    // 状态：0未处理，1已处理
    private Integer state;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    // 运行结果
    private String result;

    // 备注
    private String remark;
}
