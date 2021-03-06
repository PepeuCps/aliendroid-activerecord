/*
 * AlienDroid Framework
 * Copyright (C) 2012 Marlon Silva Carvalho
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.alienlabs.aliendroid.activerecord;

import java.lang.reflect.Field;
import java.util.Date;

import org.alienlabs.aliendroid.util.DateUtils;
import org.alienlabs.aliendroid.util.Reflection;

import android.database.Cursor;

public class ColumnMapper {

	// http://www.sqlite.org/datatype3.html#datetime
	// http://en.wikipedia.org/wiki/ISO_8601
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @param cursor
	 * @param field
	 * @param object
	 */
	public void setValueToObject(final Cursor cursor, final Field field, final Object object) {
		final int index = cursor.getColumnIndex(field.getName());
		final Class<?> type = field.getType();
		if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			setBooleanValue(cursor, index, field, object);
		} else if (type.equals(Date.class)) {
			setDateValue(cursor, index, field, object);
		} else if (type.equals(Integer.class) || type.equals(int.class)) {
			setIntegerValue(cursor, index, field, object);
		} else if (type.equals(Long.class) || type.equals(long.class)) {
			setLongValue(cursor, index, field, object);
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			setFloatValue(cursor, index, field, object);
		} else if (type.equals(Short.class) || type.equals(short.class)) {
			setShortValue(cursor, index, field, object);
		} else if (type.equals(Double.class) || type.equals(double.class)) {
			setDoubleValue(cursor, index, field, object);
		} else if (type.equals(String.class)) {
			setStringValue(cursor, index, field, object);
		} else if (type.isEnum()) {
			setEnumValue(cursor, index, field, object);
		}
	}

	/**
	 * @param field
	 * @param object
	 * @return
	 */
	public String getValueFromObject(final Field field, final Object object) {
		String resValue = null;
		final Class<?> type = field.getType();
		if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			resValue = getBooleanValue(field, object);
		} else if (type.equals(Date.class)) {
			resValue = getDateValue(field, object);
		} else if (type.equals(Integer.class) || type.equals(int.class)) {
			resValue = getIntegerValue(field, object);
		} else if (type.equals(Long.class) || type.equals(long.class)) {
			resValue = getLongValue(field, object);
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			resValue = getFloatValue(field, object);
		} else if (type.equals(Short.class) || type.equals(short.class)) {
			resValue = getShortValue(field, object);
		} else if (type.equals(Double.class) || type.equals(double.class)) {
			resValue = getDoubleValue(field, object);
		} else if (type.equals(String.class)) {
			resValue = getStringValue(field, object);
		} else if (type.isEnum()) {
			resValue = getEnumValue(field, object);
		}
		return resValue;
	}

	// getters
	
	private String getStringValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getDoubleValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getFloatValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getShortValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getLongValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getIntegerValue(final Field field, final Object object) {
		Object result = getRawValue(field, object);
		if (result == null) {
			result = "";
		}
		return result.toString();
	}

	private String getDateValue(final Field field, final Object object) {
		String resValue = null;
		Object value = getRawValue(field, object);
		if (value != null && value instanceof Date) {
			resValue = DateUtils.format((Date) value, DATE_FORMAT);
		}
		return resValue;
	}

	private String getBooleanValue(final Field field, final Object object) {
		String resValue = null;
		Object value = getRawValue(field, object);
		if (value != null && value instanceof Boolean) {
			resValue = ((Boolean) value).booleanValue() ? "1" : "0";
		}
		return resValue;
	}

	@SuppressWarnings("rawtypes")
	private String getEnumValue(final Field field, final Object object) {
		String resValue = null;
		Object value = getRawValue(field, object);
		if (value != null) {
			int valEnum = ((Enum) value).ordinal();
			resValue = String.valueOf(valEnum);
		}
		return resValue;
	}

	// setters
	
	private void setStringValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final String value = cursor.getString(index);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setDoubleValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final Double value = (!cursor.isNull(index) ? cursor.getDouble(index) : null);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setShortValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final Short value = (!cursor.isNull(index) ? cursor.getShort(index) : null);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setFloatValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final Float value = (!cursor.isNull(index) ? cursor.getFloat(index) : null);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setLongValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final Long value = (!cursor.isNull(index) ? cursor.getLong(index) : null);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setIntegerValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final Integer value = (!cursor.isNull(index) ? cursor.getInt(index) : null);
		Reflection.setFieldValue(field.getName(), object, value);
	}

	private void setDateValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final String value = cursor.getString(index);
		final Date result = (!cursor.isNull(index) && value != null ? DateUtils.format(value, DATE_FORMAT) : null);
		Reflection.setFieldValue(field.getName(), object, result);
	}

	private void setBooleanValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final int value = (!cursor.isNull(index) ? cursor.getInt(index) : 0);
		final boolean result = (value == 1);
		Reflection.setFieldValue(field.getName(), object, result);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setEnumValue(final Cursor cursor, final int index, final Field field, final Object object) {
		final int value = cursor.getInt(index);
		final Object result = (!cursor.isNull(index) ? valueOf((Class<Enum>) field.getType(), value) : null);
		Reflection.setFieldValue(field.getName(), object, result);
	}

	private static <E extends Enum<E>> E valueOf(final Class<E> type, final int ordinal) {
		final E[] enums = type.getEnumConstants();
		return (ordinal >= 0 && ordinal < enums.length ? enums[ordinal] : null);
	}

	/**
	 * @param field
	 * @param object
	 * @return
	 */
	public Object getRawValue(final Field field, final Object object) {
		final Object result;
		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			result = field.get(object);
			field.setAccessible(accessible);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
