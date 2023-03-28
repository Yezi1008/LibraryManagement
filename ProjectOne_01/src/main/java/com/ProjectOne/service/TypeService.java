package com.ProjectOne.service;

import com.ProjectOne.model.TypeModel;

import java.util.List;

public interface TypeService {
    List<TypeModel> findAll();
    int deleteBytypes(String[] types);

    boolean save(TypeModel typeModel);

    boolean change(String typename);

}
