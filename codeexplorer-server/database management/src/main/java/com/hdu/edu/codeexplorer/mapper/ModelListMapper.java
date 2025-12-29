package com.hdu.edu.codeexplorer.mapper;

import com.hdu.edu.codeexplorer.entity.ModelList;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModelListMapper {

	// 1. 查询所有模型信息
	List<ModelList> getAllModels();

	// 2. 根据ID查询模型信息
	ModelList getModelById(Integer id);

	// 3. 根据ID删除模型信息
	Integer deleteModelById(Integer id);

	// 4. 增加模型
	Integer addModel(ModelList modellist);
}
