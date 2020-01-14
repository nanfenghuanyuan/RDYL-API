package com.zh.module.constants;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public class TeamType {
    //新手 精灵 使者 元老
    public static  final int ONE = 0; //无具体指向币种
    public static  final int TWO = 1;
    public static  final int THREE = 2;
    public static  final int FOUR = 3;

    public static String getCoinName(Integer coinType){
        switch (coinType){
            case 0 : return "新手";
            case 1 : return "精灵";
            case 2 : return "使者";
            case 3 : return "元老";
            default: return null;
        }
    }
}
