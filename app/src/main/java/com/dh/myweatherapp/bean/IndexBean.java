package com.dh.myweatherapp.bean;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class IndexBean {

    /*
      "name": "感冒指数",
      "code": "gm",
      "index": "",
      "details": "昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。",
      "otherName": ""
     */

    private String name;
    private String code;
    private String index;
    private String details;
    private String otherName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    @Override
    public String toString() {
        return "IndexBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", index='" + index + '\'' +
                ", details='" + details + '\'' +
                ", otherName='" + otherName + '\'' +
                '}';
    }
}
