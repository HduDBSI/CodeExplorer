package com.hdu.edu.codeexplorer.service.impl;

import com.hdu.edu.codeexplorer.entity.PreprocessingList;
import com.hdu.edu.codeexplorer.mapper.PreprocessingListMapper;
import com.hdu.edu.codeexplorer.service.PreprocessingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PreprocessingListServiceImpl implements PreprocessingListService {

    @Autowired
    private PreprocessingListMapper preprocessingListMapper;

    @Override
    public List<PreprocessingList> getAllPreprocessingLists() {
        return preprocessingListMapper.getAllPreprocessingLists();
    }

    @Override
    public Integer addPreprocessingList(PreprocessingList preprocessingList) {
        return preprocessingListMapper.addPreprocessingList(preprocessingList);
    }

    @Override
    public Integer deletePreprocessingListById(Integer id) {
        return preprocessingListMapper.deletePreprocessingListById(id);
    }

    @Override
    public Integer updatePreprocessingListById(PreprocessingList preprocessingList) {
        return preprocessingListMapper.updatePreprocessingListById(preprocessingList);
    }

    // 5. 根据ID查询任务信息
    @Override
    public PreprocessingList getPreprocessingById(Integer id) {
        return preprocessingListMapper.getPreprocessingById(id);
    }
}
