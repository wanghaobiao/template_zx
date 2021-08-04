package com.acrabsoft.web.util;


import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: 对应数据字典的枚举类
 * @author wanghb
 * @date 2019-07-18
 */
public interface ParamEnum {
    /**
     * 字典项type
     * @author wanghb
     */
    enum dicType {
        simLimit( "",""),
        ;
        private String code;
        private String name;
        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        dicType(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public static String getNameByCode(String code) {
            for (dicType item : dicType.values()) {
                if (item.getCode().equals(code)) {
                    return item.getName();
                }
            }
            return "";
        }
        public static List<Map<String, Object>> getList() {
            List<Map<String, Object>> list = new ArrayList<>();
            for (dicType item : dicType.values()) {
                list.add( ImmutableMap.of("value",item.getCode(),"label",item.getName() ));
            }
            return list;
        }
    }


    /**
     * 字典项type
     * @author wanghb
     */
    enum active {
        dev( "dev","本地环境"),
        prd( "prd","生产环境"),
        test( "prd","测试环境"),
        ;
        private String code;
        private String name;
        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        active(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public static String getNameByCode(String code) {
            for (active item : active.values()) {
                if (item.getCode().equals(code)) {
                    return item.getName();
                }
            }
            return "";
        }
        public static List<Map<String, Object>> getList() {
            List<Map<String, Object>> list = new ArrayList<>();
            for (active item : active.values()) {
                list.add( ImmutableMap.of("value",item.getCode(),"label",item.getName() ));
            }
            return list;
        }
    }


    /**
     * 逻辑删除字段
     * @author wanghb
     */
    enum deleted {
        noDel( "0","未删除"),
        yesDel( "1","已删除"),
        ;
        private String code;
        private String name;
        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        deleted(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public static String getNameByCode(String code) {
            for (deleted item : deleted.values()) {
                if (item.getCode().equals(code)) {
                    return item.getName();
                }
            }
            return "";
        }
        public static List<Map<String, Object>> getList() {
            List<Map<String, Object>> list = new ArrayList<>();
            for (deleted item : deleted.values()) {
                list.add( ImmutableMap.of("value",item.getCode(),"label",item.getName() ));
            }
            return list;
        }
    }



}

