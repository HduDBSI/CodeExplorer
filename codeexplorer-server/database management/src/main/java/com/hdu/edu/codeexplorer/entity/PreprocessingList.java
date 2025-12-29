package com.hdu.edu.codeexplorer.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PreprocessingList {
    //序号
    private Integer id;

    //名称
    private String name;

    //数据集
    private String dataset;

    //状态
    private Integer state;

    //GPU卡
    private String gpu;

    //时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    // 运行结果
    private String result;

    // 备注
    private String remark;
}
