package com.ProjectOne.service.impl;

import com.ProjectOne.dao.TypeDao;
import com.ProjectOne.dao.impl.TypeDaoImpl;
import com.ProjectOne.model.TypeModel;
import com.ProjectOne.service.TypeService;
import com.ProjectOne.util.BeanFactory;

import java.util.List;

public class TypeServiceImpl implements TypeService {
//业务层链接实现层
    private final TypeDao dao= BeanFactory.getBean(TypeDaoImpl.class);
    @Override
    public List<TypeModel> findAll() {
        return dao.findAll();
    }

    @Override
    public int deleteBytypes(String[] types) {
        int count = 0;
        for (String type : types) {
            boolean b = dao.deleteBytypes(type);
            if (b) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean save(TypeModel typeModel) {
        return dao.save(typeModel)>0;
    }

    @Override
    public boolean change(String typename) {
        return dao.change(typename);
    }
}
