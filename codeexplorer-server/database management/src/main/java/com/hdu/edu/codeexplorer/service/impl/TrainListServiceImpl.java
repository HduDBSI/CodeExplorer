package com.hdu.edu.codeexplorer.service.impl;

import com.hdu.edu.codeexplorer.entity.TrainList;
import com.hdu.edu.codeexplorer.mapper.TrainListMapper;
import com.hdu.edu.codeexplorer.service.TrainListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainListServiceImpl implements TrainListService {

    @Autowired
    private TrainListMapper trainListMapper;

    @Override
    public List<TrainList> getAllTrainLists() {
        return trainListMapper.getAllTrainLists();
    }

    @Override
    public Integer addTrainList(TrainList trainList) {
        return trainListMapper.addTrainList(trainList);
    }

    @Override
    public Integer deleteTrainListById(Integer id) {
        return trainListMapper.deleteTrainListById(id);
    }

    @Override
    public Integer updateTrainListById(TrainList trainList) {
        return trainListMapper.updateTrainListById(trainList);
    }

    @Override
    public TrainList getTrainById(Integer id) {
        return trainListMapper.getTrainById(id);
    }
}
