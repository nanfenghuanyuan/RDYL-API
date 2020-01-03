package com.zh.module.enums;


public enum RewardType {

    PERSON_AWARD_ONE(1, "PERSON_AWARD_ONE"),
    PERSON_AWARD_TWO(2, "PERSON_AWARD_TWO"),
    TEAM_AWARD_ONE(3, "TEAM_AWARD_ONE"),
    TEAM_AWARD_TWO(4, "TEAM_AWARD_TWO"),
    TEAM_AWARD_THREE(5, "TEAM_AWARD_THREE");

    private Integer code;

    private String msg;

    RewardType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.msg;
    }

    public void setMessage(String msg){
    	this.msg = msg;
    }

    public static String getMessage(Integer code) {
        for (RewardType item : RewardType.values()) {
            if (item.code().equals(code)) {
                return item.msg;
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (RewardType item : RewardType.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

}
