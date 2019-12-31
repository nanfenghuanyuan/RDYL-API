package com.zh.module.model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by
 * 配置文件
 */
@Data
public class Configuration implements Serializable{
	
	private static final long serialVersionUID = -4328261889873040163L;
    /**各种动态页面url*/
    /*注册协议*/
    private  String agreenmentUrl;
    /*帮助文档*/
    private  String helpDocUrl;
    //弹窗公告
    private Object notice;

    private List<CoinModule> coinList;

}
