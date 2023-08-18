package com.hitachi.coe.fullstack.exceptions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BaseResponse implements Serializable{

	private static final long serialVersionUID = -7646980077956757929L;
	
	private boolean success;
	private Set<Error> errors;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Set<Error> getErrors() {
		return errors;
	}
	
	public void setError(Set<Error> errorSet) {
		if(this.errors == null){
			this.errors = errorSet;
		}
		else{
			this.errors.addAll(errorSet);
			this.success = false;
		}
	}
	
	public void addError(Error error) {
		if(this.errors == null){
			this.errors = new HashSet<>();
		}
		
		this.errors.add(error);
		this.success = false;
	}
}
