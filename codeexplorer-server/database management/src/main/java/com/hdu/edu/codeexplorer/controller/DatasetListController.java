package com.hdu.edu.codeexplorer.controller;

import com.hdu.edu.codeexplorer.entity.DatasetList;
import com.hdu.edu.codeexplorer.service.DatasetListService;
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
@RequestMapping("/dataset")
public class DatasetListController {

    @Autowired
    DatasetListService datasetListService;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    // 1. 查询所有数据集信息
    @GetMapping("/getAllDatasets")
    public Object getAllDatasets() {
        try {
            List<DatasetList> datasets = datasetListService.getAllDatasets();
            return ResponseUtil.okList(datasets);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，获取所有数据集信息失败：" + e);
            return ResponseUtil.fail(-1, "获取所有数据集信息失败：" + e.getMessage());
        }
    }

    // 2. 增加数据集
    @PostMapping("/addDataset")
    public Object addDataset(@RequestBody DatasetList dataset) {
        try {
            int result = datasetListService.addDataset(dataset);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "新增数据集失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，新增数据集失败：" + e);
            return ResponseUtil.fail(-1, "新增数据集失败：" + e.getMessage());
        }
    }

    // 3. 根据ID删除数据集
    @DeleteMapping("/deleteDatasetById/{id}")
    public Object deleteDatasetById(@PathVariable("id") Integer id) {
        try {
            int result = datasetListService.deleteDatasetById(id);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "数据集ID不存在，删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID删除数据集失败：" + e);
            return ResponseUtil.fail(-1, "删除数据集失败：" + e.getMessage());
        }
    }

    // 4. 根据ID查询数据集信息
    @GetMapping("/getDatasetById/{id}")
    public Object getDatasetById(@PathVariable("id") Integer id) {
        try {
            DatasetList dataset = datasetListService.getDatasetById(id);
            if (dataset != null) {
                return ResponseUtil.ok(dataset);
            } else {
                return ResponseUtil.fail(-1, "数据集ID不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID获取数据集信息失败：" + e);
            return ResponseUtil.fail(-1, "获取数据集信息失败：" + e.getMessage());
        }
    }
}
