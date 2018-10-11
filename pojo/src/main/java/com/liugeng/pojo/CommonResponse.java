package com.liugeng.pojo;

import lombok.Data;

@Data
public class CommonResponse<T> {
	String msg;
	String code;
	T body;
}
