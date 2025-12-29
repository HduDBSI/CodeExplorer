package com.hdu.edu.codeexplorer.controller;

import com.hdu.edu.codeexplorer.entity.ModelList;
import com.hdu.edu.codeexplorer.service.ModelListService;
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
@RequestMapping("/modelList")
public class ModelListController {

    @Autowired
    ModelListService modelListService;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    // 1. 查询所有模型信息
    @GetMapping("/getAllModels")
    public Object getAllModels() {
        try {
            List<ModelList> modelList = modelListService.getAllModels();
            return ResponseUtil.okList(modelList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，获取所有模型信息失败：" + e);
            return ResponseUtil.fail(-1, "获取所有模型信息失败：" + e.getMessage());
        }
    }

    // 2. 根据ID查询模型信息
    @GetMapping("/getModelById/{id}")
    public Object getModelById(@PathVariable("id") Integer id) {
        try {
            ModelList model = modelListService.getModelById(id);
            if (model != null) {
                return ResponseUtil.ok(model);
            } else {
                return ResponseUtil.fail(-1, "模型ID不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID获取模型信息失败：" + e);
            return ResponseUtil.fail(-1, "获取模型信息失败：" + e.getMessage());
        }
    }

    // 3. 根据ID删除模型信息
    @DeleteMapping("/deleteModelById/{id}")
    public Object deleteModelById(@PathVariable("id") Integer id) {
        try {
            int result = modelListService.deleteModelById(id);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "模型ID不存在，删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，根据ID删除模型信息失败：" + e);
            return ResponseUtil.fail(-1, "删除模型失败：" + e.getMessage());
        }
    }

    // 4. 增加模型
    @PostMapping("/addModel")
    public Object addModel(@RequestBody ModelList modellist) {
        try {
            int result = modelListService.addModel(modellist);
            if (result > 0) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail(-1, "新增模型失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作失败，新增模型失败：" + e);
            return ResponseUtil.fail(-1, "新增模型失败：" + e.getMessage());
        }
    }
}
