package org.example.port_manage_system.service;

import org.example.port_manage_system.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public Integer getCategoryIdByName(String categoryName){
        return categoryMapper.getCategoryIdByName(categoryName);
    };

    public String getCategoryByCategoryId(Integer id){
        return categoryMapper.getCategoryByCategoryId(id);
    };

}
