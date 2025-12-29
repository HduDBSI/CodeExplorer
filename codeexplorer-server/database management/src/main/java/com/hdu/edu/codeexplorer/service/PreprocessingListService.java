package com.hdu.edu.codeexplorer.service;

import com.hdu.edu.codeexplorer.entity.PreprocessingList;

import java.util.List;

public interface PreprocessingListService {
    // 1. 查询所有任务信息
    List<PreprocessingList> getAllPreprocessingLists();

    // 2. 增加任务
    Integer addPreprocessingList(PreprocessingList preprocessingList);

    // 3. 根据ID删除任务
    Integer deletePreprocessingListById(Integer id);

    // 4. 修改任务
    Integer updatePreprocessingListById(PreprocessingList preprocessingList);

    // 5. 根据ID查询任务信息
    PreprocessingList getPreprocessingById(Integer id);
}
