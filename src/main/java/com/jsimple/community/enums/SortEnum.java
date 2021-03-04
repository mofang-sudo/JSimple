package com.jsimple.community.enums;

import com.sun.org.apache.bcel.internal.generic.NEW;

public enum SortEnum {
    HOT,    //当天最多查阅
    HOT30,  //当月最多查阅
    HOT7,   //当周最多查阅
    NO,     //没有回复
    NEW,    //新发布
    GOOD;   //精帖
}
