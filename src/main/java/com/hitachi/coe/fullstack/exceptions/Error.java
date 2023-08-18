package com.hitachi.coe.fullstack.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error implements Serializable{

	private static final long serialVersionUID = -3795831259310063579L;
	
	private String errorCode;
	private String errorMessage;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Error other = (Error) obj;
		
		if (errorCode == null) {
			if (other.errorCode != null) {
				return false;
			}
		} else if (!errorCode.equals(other.errorCode)) {
			return false;
		}
		
		if (errorMessage == null) {
			if (other.errorMessage != null) {
				return false;
			}
		} else if (!errorMessage.equals(other.errorMessage)) {
			return false;
		}
		return true;
	}

}
