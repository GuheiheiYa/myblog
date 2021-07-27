package com.yjk.service;

import com.yjk.dao.TypeRepository;
import com.yjk.handler.NotFoundException;
import com.yjk.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    TypeRepository typeRepository;

    @Transactional  //添加事务
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional  //添加事务
    @Override
    public Type getType(Long id) {
        return typeRepository.getById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional  //添加事务
    @Override  //分页查询
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);

    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional  //添加事务
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getById(id);
        if (t == null){
            throw new NotFoundException("不存在该类型!");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional  //添加事务
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);
    }



}
