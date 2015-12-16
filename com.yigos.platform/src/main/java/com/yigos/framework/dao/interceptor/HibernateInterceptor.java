package com.yigos.framework.dao.interceptor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
public class HibernateInterceptor extends EmptyInterceptor {

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, 
			Object[] previousStatue,
			String[] propertyNames, Type[] types) {
		return this.setDefaultValues(entity, currentState, propertyNames);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, 
			String[] propertyNames, Type[] types) {
		return this.setDefaultValues(entity, state, propertyNames);
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, 
			String[] propertyNames, Type[] types) {
		@SuppressWarnings("unchecked")
		Class<Table> clz = (Class<Table>) entity.getClass();
		if (this.isDsfTable(clz)) {
			for (int i = 0, length = state.length; i < length; i++) {
				try {
					if (state[i] != null 
						&& clz.getDeclaredField(
								propertyNames[i]).getType().equals(String.class)
						) {
						state[i] = StringUtils.stripEnd(String.valueOf(state[i]), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判斷是否為財局的表
	 * 
	 * @param clz
	 * @return
	 */
	private boolean isDsfTable(Class<Table> clz) {
		Table table = clz.getAnnotation(Table.class);
		Assert.notNull(table);
		return true;
		//
		// return !StringUtils.startsWith(table.name(), Constants.SCHEMA + ".");
	}

	/**
	 * 設置默認值
	 * 
	 * @param entity
	 * @param currentState
	 * @param propertyNames
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean setDefaultValues(Object entity, Object[] currentState, 
			String[] propertyNames) {
		Class<Table> clz = (Class<Table>) entity.getClass();
		if (this.isDsfTable(clz)) {
			for (int i = 0, length = currentState.length; i < length; i++) {
				if (currentState[i] == null) {
					try {
						Class<?> fieldType = clz.getDeclaredField(propertyNames[i]).getType();
						if (fieldType.equals(String.class)) {
							currentState[i] = "";
						} else if (Number.class.isAssignableFrom(fieldType)) {
							if (!clz.getMethod("get" + propertyNames[i].substring(0, 1).toUpperCase()
									+ propertyNames[i].substring(1)).getAnnotation(Column.class).nullable()) {
								currentState[i] = fieldType.getMethod("valueOf", String.class).invoke(null, "0");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return true;
	}
}
