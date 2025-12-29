package com.hdu.edu.codeexplorer.service;

import com.hdu.edu.codeexplorer.entity.TrainList;
import java.util.List;

public interface TrainListService {

    // 1. 查询所有任务信息
    List<TrainList> getAllTrainLists();

    // 2. 增加任务
    Integer addTrainList(TrainList trainList);

    // 3. 根据ID删除任务
    Integer deleteTrainListById(Integer id);

    // 4. 修改任务
    Integer updateTrainListById(TrainList trainList);

    // 5. 根据ID查询任务信息
    TrainList getTrainById(Integer id);
}
