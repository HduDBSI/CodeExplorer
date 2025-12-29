package com.hdu.edu.codeexplorer.service.impl;

import com.hdu.edu.codeexplorer.entity.TestList;
import com.hdu.edu.codeexplorer.mapper.TestListMapper;
import com.hdu.edu.codeexplorer.service.TestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TestListServiceImpl implements TestListService {

    @Autowired
    private TestListMapper testListMapper;

    @Override
    public List<TestList> getAllTestLists() {
        return testListMapper.getAllTestLists();
    }

    @Override
    public Integer addTestList(TestList testList) {
        return testListMapper.addTestList(testList);
    }

    @Override
    public Integer deleteTestListById(Integer id) {
        return testListMapper.deleteTestListById(id);
    }

    @Override
    public Integer updateTestListById(TestList testList) {
        return testListMapper.updateTestListById(testList);
    }

    @Override
    public TestList getTestById(Integer id) {
        return testListMapper.getTestById(id);
    }
}
