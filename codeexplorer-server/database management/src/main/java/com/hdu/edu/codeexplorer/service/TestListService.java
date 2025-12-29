package com.hdu.edu.codeexplorer.service;

import com.hdu.edu.codeexplorer.entity.TestList;

import java.util.List;

public interface TestListService {

    // 1. 查询所有任务信息
    List<TestList> getAllTestLists();

    // 2. 增加任务
    Integer addTestList(TestList trainList);

    // 3. 根据ID删除任务
    Integer deleteTestListById(Integer id);

    // 4. 修改任务
    Integer updateTestListById(TestList testList);

    // 5. 根据ID查询任务信息
    TestList getTestById(Integer id);
}
