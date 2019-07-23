package com.sth.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sth.domain.JdItem;
import com.sth.service.IJdItemService;
import com.sth.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Auther: root
 * @Date: 2019/7/24 02:57
 * @Description:
 */
@Component
public class JdItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private IJdItemService jdItemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @throws Exception
     */
    //当任务完成后，间隔多长时间进行下一次任务
    @Scheduled(fixedDelay = 100 * 1000)
    public void phoneItemTask() throws Exception {
        String url = "https://search.jd.com/Search?keyword=手机&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=shou%27ji&cid2=653&cid3=655&s=5801&click=0&page=";

        for (int i = 1; i < 10; i = i + 2) {
            String html = httpUtils.doGetHtml(url + i);
            //解析页面，获取商品并存储
            this.parse(html);
        }

        System.out.println("手机数据抓取完成");

    }

    /**
     * 解析页面，获取商品并存储
     *
     * @param html
     */
    private void parse(String html) throws Exception {
        Document doc = Jsoup.parse(html);
        //获取spu
        Elements spuEles = doc.select("div#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            //获取spu
            Long spu = Long.parseLong(spuEle.attr("data-spu"));
            //获取sku信息
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle : skuEles) {
                //获取sku
                Long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));

                //根据sku查询商品
                JdItem jdItem = new JdItem();
                jdItem.setSku(sku);
                List<JdItem> jdItems = jdItemService.findAll(jdItem);

                if (jdItems.size() > 0) {
                    //商品存在就不保存跳过
                    continue;
                }
                //设置商品spu
                jdItem.setSpu(spu);
                //获取商品详情
                String jdItemUrl = "https://item.jd.com/" + sku + ".html";
                jdItem.setUrl(jdItemUrl);
                //获取商品图片
                String picUrl = "https:" + skuEle.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl.replace("/n9/", "/n1/");
                String picName = this.httpUtils.doGetImage(picUrl);
                jdItem.setPic(picName);

                //获取商品的价格
                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                jdItem.setPrice(price);

                //获取商品标题
                String jdItemInfo = this.httpUtils.doGetHtml(jdItem.getUrl());
                String title = Jsoup.parse(jdItemInfo).select("div.sku-name").text();
                jdItem.setTitle(title);

                //创建时间更新时间
                jdItem.setCreatetime(new Date());
                jdItem.setUpdatetime(jdItem.getCreatetime());

                //保存数据
                jdItemService.save(jdItem);
            }
        }

    }
}
