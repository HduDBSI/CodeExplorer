package com.hdu.edu.codeexplorer.controller;

import com.hdu.edu.codeexplorer.entity.TrainList;
import com.hdu.edu.codeexplorer.service.TrainListService;
import com.hdu.edu.codeexplorer.util.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping("/train")
public class TrainListController {

    @Autowired
    TrainListService trainListService;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    // 1. 查询所有任务信息
    @GetMapping("/getAllTrain")
    public Object getAllTrainLists() {
        try {
            List<TrainList> trainList = trainListService.getAllTrainLists();
            return ResponseUtil.okList(trainList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，获取所有任务信息失败：" + e);
            return ResponseUtil.fail(-1, "获取所有任务信息失败：" + e.getMessage());
        }
    }

    // 2. 增加任务信息
    @PostMapping("/addTrain")
    public Object addTrainList(@RequestBody TrainList trainList) {
        try {
            int result = trainListService.addTrainList(trainList);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "增加任务失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，增加任务失败：" + e);
            return ResponseUtil.fail(-1, "增加任务失败：" + e.getMessage());
        }
    }

    // 3. 根据ID删除任务信息
    @DeleteMapping("/deleteTrain/{id}")
    public Object deleteTrainListById(@PathVariable("id") Integer id) {
        try {
            int result = trainListService.deleteTrainListById(id);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "任务ID不存在，删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID删除任务失败：" + e);
            return ResponseUtil.fail(-1, "删除任务失败：" + e.getMessage());
        }
    }

    // 4. 修改任务信息
    @PutMapping("/updateTrain")
    public Object updateTrainListById(@RequestBody TrainList trainList) {
        try {
            int result = trainListService.updateTrainListById(trainList);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "更新任务失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，更新任务失败：" + e);
            return ResponseUtil.fail(-1, "更新任务失败：" + e.getMessage());
        }
    }

    // 5. 根据ID查询任务信息
    @GetMapping("/getTrainById/{id}")
    public Object getTrainById(@PathVariable("id") Integer id) {
        try {
            TrainList train = trainListService.getTrainById(id);
            if (train != null) {
                return ResponseUtil.ok(train);
            } else {
                return ResponseUtil.fail(-1, "任务ID不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID获取任务信息失败：" + e);
            return ResponseUtil.fail(-1, "获取任务信息失败：" + e.getMessage());
        }
    }

}
