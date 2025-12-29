package com.hdu.edu.codeexplorer.service.impl;

import com.hdu.edu.codeexplorer.entity.DatasetList;
import com.hdu.edu.codeexplorer.mapper.DatasetListMapper;
import com.hdu.edu.codeexplorer.service.DatasetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DatasetListServiceImpl implements DatasetListService {

    @Autowired
    private DatasetListMapper datasetListMapper;

    // 1. 查询所有数据集信息
    @Override
    public List<DatasetList> getAllDatasets() {
        return datasetListMapper.getAllDatasets();
    }

    // 2. 增加数据集
    @Override
    public Integer addDataset(DatasetList dataset) {
        return datasetListMapper.addDataset(dataset);
    }

    // 3. 根据ID删除数据集信息
    @Override
    public Integer deleteDatasetById(Integer id) {
        return datasetListMapper.deleteDatasetById(id);
    }

    // 4. 根据ID查询模型信息
    @Override
    public DatasetList getDatasetById(Integer id) {
        return datasetListMapper.getDatasetById(id);
    }
}
