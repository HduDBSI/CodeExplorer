package com.hdu.edu.codeexplorer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TrainList {
    //序号
    private Integer id;

    //名称
    private String name;

    //模型
    private String model;

    //数据集id
    private Integer datasetId;

    //数据集
    private String dataset;

    //状态
    private Integer state;

    //GPU卡
    private String gpu;

    //类型
    private String type;

    //参数
    private String param;

    //时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    // 运行结果
    private String result;

    // 备注
    private String remark;

    private String summary;

    private Integer epochs;

    private Integer batch_size;

    private float lr;

    private float lr_decay_factor;

    private float weight_decay;

    private Integer lr_decay_step_size;

    private Integer hidden;

    private Integer num_classes;

    private Integer nheads;

    private float dropout;

    private float alpha;

}
