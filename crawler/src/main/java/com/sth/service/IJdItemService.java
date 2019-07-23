package com.sth.service;

import com.sth.domain.JdItem;

import java.util.List;

/**
 * @Auther: root
 * @Date: 2019/7/23 19:18
 * @Description:
 */
public interface IJdItemService {
    public void save(JdItem jdItem);

    public List<JdItem> findAll(JdItem jdItem);
}
