package com.hdu.edu.codeexplorer.mapper;

import com.hdu.edu.codeexplorer.entity.TrainList;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainListMapper {

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
