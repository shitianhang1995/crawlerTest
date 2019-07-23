package com.sth.service.impl;

import com.sth.dao.IJdItemDao;
import com.sth.domain.JdItem;
import com.sth.service.IJdItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: root
 * @Date: 2019/7/23 19:20
 * @Description:
 */
@Service
public class JdItemServiceImpl implements IJdItemService {

    @Autowired
    private IJdItemDao jdItemDao;

    @Override
    public void save(JdItem jdItem) {
        jdItemDao.save(jdItem);
    }

    @Override
    public List<JdItem> findAll(JdItem jdItem) {
        Example<JdItem> example = Example.of(jdItem);
        List<JdItem> jdItems = jdItemDao.findAll(example);
        return jdItems;
    }
}
