package com.zh.module.utils;

import java.util.Random;
import java.util.UUID;


public class UUIDs {
  private UUIDs() {
  }

  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  // 得到16位的UUID-(数字)
  public static String getUUID_16() {
    int machineId = 1;// 最大支持1-9个集群机器部署

    int hashCodeV = UUID.randomUUID().toString().hashCode();
    if (hashCodeV < 0) {// 有可能是负数
      hashCodeV = -hashCodeV;
    }
    String string = machineId + String.format("%015d", hashCodeV);
    return string;
  }

  // 得到32位的UUID-(数字)
  public static String getUUID_32() {
    return UUID.randomUUID().toString().replace("-", "").toLowerCase();
  }

  //得到8位的UUID-(码)
  public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
          "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
          "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
          "U", "V", "W", "X", "Y", "Z"};

  public static String getUUID_8() {
    StringBuffer shortBuffer = new StringBuffer();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    for (int i = 0; i < 6; i++) {
      String str = uuid.substring(i * 4, i * 4 + 4);
      int x = Integer.parseInt(str, 16);
      shortBuffer.append(chars[x % 0x3E]);
    }
    return shortBuffer.toString();

  }

  public static Integer getUUID8() {
    StringBuilder str = new StringBuilder();//定义变长字符串
    Random random = new Random();
//随机生成数字，并添加到字符串
    for (int i = 0; i < 8; i++) {
      str.append(random.nextInt(10));
    }
//将字符串转换为数字并输出
    return Integer.parseInt(str.toString());
  }

}
