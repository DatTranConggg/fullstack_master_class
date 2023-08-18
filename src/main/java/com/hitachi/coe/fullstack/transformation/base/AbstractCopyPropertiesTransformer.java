package com.hitachi.coe.fullstack.transformation.base;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.BeanUtils;

import lombok.SneakyThrows;

/**
 * This abstract class is to copy properties from one to other.
 * @author thovo
 *
 * @param <Origin>
 * @param <Destination>
 */
public abstract class AbstractCopyPropertiesTransformer<Origin, Destination> { //NOSONAR

	protected final Class<Destination> destinationClass;

	@SuppressWarnings("unchecked")
	protected AbstractCopyPropertiesTransformer() {
		super();
		this.destinationClass = (Class<Destination>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@SneakyThrows
	public Destination apply(final Origin orig) {
		try {
			final Destination dest = destinationClass.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(orig, dest);
			return dest;	
		} catch (Exception e) {
			return null;
		}
		
	}
}
