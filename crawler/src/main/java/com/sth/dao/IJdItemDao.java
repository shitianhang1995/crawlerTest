package com.sth.dao;

import com.sth.domain.JdItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: root
 * @Date: 2019/7/23 19:10
 * @Description:
 */
public interface IJdItemDao extends JpaRepository<JdItem, Long> {
}
