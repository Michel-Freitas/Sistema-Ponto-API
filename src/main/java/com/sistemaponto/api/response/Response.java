package com.sistemaponto.api.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Response<T> {
	
	private T data;
	private List<String> erros;
	
	
	public List<String> getErros(){
		if(this.erros == null) {
			this.erros = new ArrayList<String>();
		}
		
		return erros;
	}
	
}
