package com.ProjectOne.dao;

import com.ProjectOne.model.TypeModel;

import java.util.List;

public interface TypeDao {
    List<TypeModel> findAll();
    boolean deleteBytypes(String type);

    int save(TypeModel typeModel);

    boolean change(String typename);
}
