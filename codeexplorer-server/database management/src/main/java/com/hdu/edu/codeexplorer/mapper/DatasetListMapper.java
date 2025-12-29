package com.hdu.edu.codeexplorer.mapper;

import com.hdu.edu.codeexplorer.entity.DatasetList;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DatasetListMapper {

    // 1. 查询所有数据集信息
    List<DatasetList> getAllDatasets();

    // 2. 增加数据集
    Integer addDataset(DatasetList dataset);

    // 3. 根据ID删除数据集信息
    Integer deleteDatasetById(Integer id);

    // 4. 根据ID查询数据集信息
    DatasetList getDatasetById(Integer id);
}