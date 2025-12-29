package com.hdu.edu.codeexplorer.controller;
import com.hdu.edu.codeexplorer.entity.PreprocessingList;
import com.hdu.edu.codeexplorer.service.PreprocessingListService;
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
@RequestMapping("/preprocessing")
public class PreprocessingController {

    @Autowired
    PreprocessingListService preprocessingListService;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    // 1. 查询所有任务信息
    @GetMapping("/getAllPreprocessing")
    public Object getAllPreprocessingLists() {
        try {
            List<PreprocessingList> preprocessingList = preprocessingListService.getAllPreprocessingLists();
            return ResponseUtil.okList(preprocessingList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，获取所有任务信息失败：" + e);
            return ResponseUtil.fail(-1, "获取所有任务信息失败：" + e.getMessage());
        }
    }

    // 2. 增加任务信息
    @PostMapping("/addPreprocessing")
    public Object addPreprocessingList(@RequestBody PreprocessingList preprocessingList) {
        try {
            int result = preprocessingListService.addPreprocessingList(preprocessingList);
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
    @DeleteMapping("/deletePreprocessing/{id}")
    public Object deletePreprocessingListById(@PathVariable("id") Integer id) {
        try {
            int result = preprocessingListService.deletePreprocessingListById(id);
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
    @PutMapping("/updatePreprocessing")
    public Object updatePreprocessingListById(@RequestBody PreprocessingList preprocessingList) {
        try {
            int result = preprocessingListService.updatePreprocessingListById(preprocessingList);
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

    // 5. 根据ID查询热媒信息
    @GetMapping("/getPreprocessingById/{id}")
    public Object getPreprocessingById(@PathVariable("id") Integer id) {
        try {
            PreprocessingList preprocessing = preprocessingListService.getPreprocessingById(id);
            if (preprocessing != null) {
                return ResponseUtil.ok(preprocessing);
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
