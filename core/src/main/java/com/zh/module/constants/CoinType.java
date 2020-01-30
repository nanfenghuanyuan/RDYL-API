package com.zh.module.constants;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public class CoinType {

    public static  final int NONE = -1; //无具体指向币种
    public static  final int CNY = 0;
    public static  final int OS = 1;

    public static String getCoinName(Integer coinType){
        switch (coinType){
            case 0 : return "CNY";
            case 1 : return "MEPC";
            default: return null;
        }
    }
}
