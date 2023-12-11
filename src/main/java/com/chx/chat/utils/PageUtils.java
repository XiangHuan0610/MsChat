package com.chx.chat.utils;


import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class PageUtils {
    // 当前页
    private Integer currPage;

    // 总页数
    private Integer currSize;

    // 当前页记录数
    private Integer limit;

    // 总记录数
    private Integer totalCount;


    private List<?> data;

    public PageUtils() {
        this.currPage = 0;
        this.currSize = 0;
        this.limit = 0;
        this.totalCount = 0;
        this.data = null;
    }

    /**
     *
     * @param list 数据集合
     * @param param map参数
     * @param totalCount  数据总长度
     * @param currPage 当前页
     * @param limit 分页个数
     */
    public PageUtils(List<?> list, Map<String,Object> param, Integer totalCount){
        this.currPage = Integer.parseInt(param.get("page").toString()); // 拿到当前页
        this.limit = Integer.parseInt(param.get("limit").toString()); // 当前页展示limit个数据
        this.currSize = currSize(totalCount,this.limit); // 总页数
        try{
            this.data = list.subList(currPage*this.limit - this.limit,currPage * this.limit > totalCount ? currPage * this.limit - ((currPage * limit) - totalCount) : currPage * this.limit);
        }catch (Exception e){
            this.data = null;
        }
        this.totalCount = totalCount;
    }


    private Integer currSize(Integer totalCount, Integer limit) {
        if (totalCount % limit == 0){
            return totalCount / limit;
        }else{
            return (totalCount / limit) + 1;
        }
    }

    @Override
    public String toString() {
        return "currPageModel{" +
                "currPage=" + currPage +
                ", currSize=" + currSize +
                ", limit=" + limit +
                ", totalCount=" + totalCount +
                ", data=" + data +
                '}';
    }

    public Integer getcurrPage() {
        return currPage;
    }

    public void setcurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getcurrSize() {
        return currSize;
    }

    public void setcurrSize(Integer currSize) {
        this.currSize = currSize;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer gettotalCount() {
        return totalCount;
    }

    public void settotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getData() {
        return data;
    }

    public void setList(List<?> data) {
        this.data = data;
    }
}
