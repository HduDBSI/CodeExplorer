package com.hdu.edu.codeexplorer.mapper;

import com.hdu.edu.codeexplorer.entity.TestList;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TestListMapper {
    // 1. 查询所有任务信息
    List<TestList> getAllTestLists();

    // 2. 增加任务
    Integer addTestList(TestList testList);

    // 3. 根据ID删除任务
    Integer deleteTestListById(Integer id);

    // 4. 修改任务
    Integer updateTestListById(TestList testList);

    // 5. 根据ID查询任务信息
    TestList getTestById(Integer id);
}
