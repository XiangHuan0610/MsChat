package com.chx.chat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public static R isPay() {
		R r = new R();
		r.put("code",200);
		r.put("status",1);
		r.put("msg","已支付");
		return r;
	}

	public static R notPay(String msg){
		R r = new R();
		r.put("code",200);
		r.put("status",0);
		r.put("msg",msg);
		return r;
	}

	public static R notLogin() {
		R r = new R();
		r.put("code",500);
		r.put("msg","请先登录");
		return r;
	}



    public R setData(Object data) {
		put("data",data);
		return this;
	}

	//利用fastjson进行反序列化
	public <T> T getData(TypeReference<T> typeReference) {
		Object data = get("data");	//默认是map
		String jsonString = JSON.toJSONString(data);
		T t = JSON.parseObject(jsonString, typeReference);
		return t;
	}


	//利用fastjson进行反序列化
	public <T> T getData(String key,TypeReference<T> typeReference) {
		Object data = get(key);	//默认是map
		String jsonString = JSON.toJSONString(data);
		T t = JSON.parseObject(jsonString, typeReference);
		return t;
	}



	public R() {
		put("code", 200);
		put("msg", "success");
	}
	
	public static R error() {
		return error(500, "ERROR");
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("code",200);
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		R r = new R();
		r.put("code",200);
		r.put("msg","SUCCESS");
		return r;
	}

	public static R record() {
		R r = new R();
		r.put("code",1);
		r.put("msg","SUCCESS");
		return r;
	}

	public static R collected(){
		R r = new R();
		r.put("code",0);
		r.put("msg","ERROR");
		return r;
	}


	public static R notBuy() {
		R r = new R();
		r.put("code",200);
		r.put("status",0);
		r.put("msg","未购买");
		return r;
	}

	public static R isBuy() {
		R r = new R();
		r.put("code",200);
		r.put("status",1);
		r.put("msg","已购买");
		return r;
	}

	public R put(Object value) {
		super.put("msg", value);
		return this;
	}

	public Integer getCode() {

		return (Integer) this.get("code");
	}


	public R login(String offlineMsg,String token) {
		R r = new R();
		r.put("offlineMsg",offlineMsg);
		r.put("token",token);
		return r;
	}

	public R login(UmsMemberInfoEntity umsMemberInfoEntity,String offlineMsg, String token) {
		R r = new R();
		r.put("data",umsMemberInfoEntity);
		r.put("offlineMsg",offlineMsg);
		r.put("token",token);
		return r;
	}
}