package com.sth.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: root
 * @Date: 2019/7/23 18:58
 * @Description:
 */
@Data
@Entity
@Table(name = "jd_item")
public class JdItem implements Serializable {
    private static final long serialVersionUID = -781808053904013981L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spu;
    private Long sku;
    private String title;
    private Long price;
    private String pic;
    private Date createtime;
    private Date updatetime;
    private String url;
}
