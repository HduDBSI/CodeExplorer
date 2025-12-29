package com.hdu.edu.codeexplorer.service.impl;

import com.hdu.edu.codeexplorer.entity.ModelList;
import com.hdu.edu.codeexplorer.mapper.ModelListMapper;
import com.hdu.edu.codeexplorer.service.ModelListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModelListServiceImpl implements ModelListService {

    @Autowired
    ModelListMapper modelListMapper;

    // 1. 查询所有模型信息
    @Override
    public List<ModelList> getAllModels() {
        return modelListMapper.getAllModels();
    }

    // 2. 根据ID查询模型信息
    @Override
    public ModelList getModelById(Integer id) {
        return modelListMapper.getModelById(id);
    }

    // 3. 根据ID删除模型信息
    @Override
    public Integer deleteModelById(Integer id) {
        return modelListMapper.deleteModelById(id);
    }

    // 4. 增加模型
    @Override
    public Integer addModel(ModelList modellist) {
        return modelListMapper.addModel(modellist);
    }
}
