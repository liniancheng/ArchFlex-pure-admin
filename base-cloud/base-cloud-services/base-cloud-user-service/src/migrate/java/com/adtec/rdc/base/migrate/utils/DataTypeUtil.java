package com.adtec.rdc.base.migrate.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 数据类型工具类
 * 
 * @author dengchf
 * @description 1.字段类型判断，目前整理一部分数据类型，后续可能会继续拓展;</br>
 *              2.暂不考虑二进制;</br> 
 *              3.当前仅测试了mysql
 */
public class DataTypeUtil {

	private static final String TYPE_BIT = "BIT";
	private static final String TYPE_TINYINT = "TINYINT";
	private static final String TYPE_TINYINT_UNSIGNED = "TINYINT UNSIGNED";
	private static final String TYPE_BOOL = "BOOL";
	private static final String TYPE_BOOLEAN = "BOOLEAN";
	private static final String TYPE_SMALLINT = "SMALLINT";
	private static final String TYPE_SMALLINT_UNSIGNED = "SMALLINT UNSIGNED";
	private static final String TYPE_MEDIUMINT = "MEDIUMINT";
	private static final String TYPE_MEDIUMINT_UNSIGNED = "MEDIUMINT UNSIGNED";
	private static final String TYPE_INT = "INT";
	private static final String TYPE_INT_UNSIGNED = "INT UNSIGNED";
	private static final String TYPE_INTEGER = "INTEGER";
	private static final String TYPE_INTEGER_UNSIGNED = "INTEGER UNSIGNED";
	private static final String TYPE_BIGINT = "BIGINT";
	private static final String TYPE_BIGINT_UNSIGNED = "BIGINT UNSIGNED";
	private static final String TYPE_FLOAT = "FLOAT";
	private static final String TYPE_DOUBLE = "DOUBLE";
	private static final String TYPE_DECIMAL = "DECIMAL";
	private static final String TYPE_DATE = "DATE";
	private static final String TYPE_DATETIME = "DATETIME";
	private static final String TYPE_TIMESTAMP = "TIMESTAMP";
	private static final String TYPE_TIME = "TIME";
	private static final String TYPE_YEAR = "YEAR";
	private static final String TYPE_CHAR = "CHAR";
	private static final String TYPE_VARCHAR = "VARCHAR";
	private static final String TYPE_VARCHAR2 = "VARCHAR2";
	private static final String TYPE_BINARY = "BINARY";
	private static final String TYPE_VARBINARY = "VARBINARY";
	private static final String TYPE_TINYBLOB = "TINYBLOB";
	private static final String TYPE_TINYTEXT = "TINYTEXT";
	private static final String TYPE_BLOB = "BLOB";
	private static final String TYPE_TEXT = "TEXT";
	private static final String TYPE_MEDIUMBLOB = "MEDIUMBLOB";

	private static final String TYPE_MEDIUMTEXT = "MEDIUMTEXT";
	private static final String TYPE_LONGBLOB = "LONGBLOB";
	private static final String TYPE_LONGTEXT = "LONGTEXT";
	private static final String TYPE_ENUM = "ENUM";
	private static final String TYPE_SET = "SET";

	/**
	 * 根据字段类型，入参
	 */
	public static void setColValue(int index, Object obj, String typeName, int dataType, PreparedStatement ps)
			throws SQLException {
		if (obj == null) {
			ps.setNull(index, dataType);
		} else {
			if (isString(typeName)) {
				ps.setString(index, String.valueOf(obj));
			} else if (isInteger(typeName)) {
				ps.setInt(index, (int) obj);
			} else if (isFloat(typeName)) {
				ps.setFloat(index, (float) obj);
			} else if (isDouble(typeName)) {
				ps.setDouble(index, (double) obj);
			} else if (isLong(typeName)) {
				ps.setLong(index, (Long) obj);
			} else if (isDate(typeName)) {
				ps.setDate(index, (Date) obj);
			} else if (isTime(typeName)) {
				ps.setTime(index, (Time) obj);
			} else if (isTimestamp(typeName)) {
				ps.setTimestamp(index, (Timestamp) obj);
			} else if (isBytes(typeName)) {
				ps.setBytes(index, (byte[]) obj);
			} else if (isBigDecimal(typeName)) {
				ps.setBigDecimal(index, (BigDecimal) obj);
			} else if (isBigInteger(typeName)) {
				ps.setLong(index, ((BigInteger) obj).longValue());
			} else if (isBlob(typeName)) {
				ps.setBytes(index, (byte[]) obj);
			} else if (isTintInt(typeName)) {// 需要二次判断
				if (obj instanceof Integer) {
					ps.setInt(index, (int) obj);
				} else {// boolean
					ps.setBoolean(index, (boolean) obj);
				}
			} else if (isYear(typeName)) {// 需要二次判断
				if (obj instanceof Date) {
					Calendar c = Calendar.getInstance();
					c.setTime((Date) obj);
					ps.setInt(index, c.get(Calendar.YEAR));
				} else {// short
					ps.setShort(index, (short) obj);
				}
			} else if (isBit(typeName)) {// 需要二次判断
				if (obj instanceof byte[]) {
					ps.setBytes(index, (byte[]) obj);
				} else {// boolean
					ps.setBoolean(index, (boolean) obj);
				}
			}

		}
	}

	// -----------------------------------------------单类型判断--------------------------------------------------------//
	/**
	 * @description CHAR, VARCHAR(二进制时为byte[]), VARCHAR2, ENUM, SET
	 */
	private static boolean isString(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_CHAR.equals(typeName) || TYPE_VARCHAR.equals(typeName) || TYPE_VARCHAR2.equals(typeName)
				|| TYPE_TINYTEXT.equals(typeName) || TYPE_VARCHAR.equals(typeName) || TYPE_TEXT.equals(typeName)
				|| TYPE_MEDIUMTEXT.equals(typeName) || TYPE_LONGTEXT.equals(typeName) || TYPE_ENUM.equals(typeName)
				|| TYPE_SET.equals(typeName);
	}

	/**
	 * @description DOUBLE
	 */
	private static boolean isDouble(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_DOUBLE.equals(typeName);
	}

	/**
	 * @description FLOAT
	 */
	private static boolean isFloat(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_FLOAT.equals(typeName);
	}

	/**
	 * @description SMALLINT [UNSIGNED];MEDIUMINT [UNSIGNED];INT;INTEGER;TINYINT
	 *              UNSIGNED
	 */
	private static boolean isInteger(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_SMALLINT.equals(typeName) || TYPE_INT.equals(typeName) || TYPE_INTEGER.equals(typeName)
				|| TYPE_SMALLINT_UNSIGNED.equals(typeName) || TYPE_MEDIUMINT.equals(typeName)
				|| TYPE_MEDIUMINT_UNSIGNED.equals(typeName) || TYPE_TINYINT_UNSIGNED.equals(typeName);

	}

	/**
	 * @description INTEGER UNSIGNED;BIGINT
	 */
	private static boolean isLong(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_INTEGER_UNSIGNED.equals(typeName) || TYPE_INT_UNSIGNED.equals(typeName)
				|| TYPE_BIGINT.equals(typeName);
	}

	/**
	 * @description TIME
	 */
	private static boolean isTime(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_TIME.equals(typeName);
	}

	/**
	 * @description DATETIME;TIMESTAMP
	 */
	private static boolean isTimestamp(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_DATETIME.equals(typeName) || TYPE_TIMESTAMP.equals(typeName);
	}

	/**
	 * @description Date
	 */
	private static boolean isDate(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_DATE.equals(typeName);
	}

	/**
	 * @description BigDecimal
	 */
	private static boolean isBigDecimal(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_DECIMAL.equals(typeName);
	}

	/**
	 * @description BIGINT UNSIGNED
	 */
	private static boolean isBigInteger(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_BIGINT_UNSIGNED.equals(typeName);
	}

	/**
	 * @description BINARY;VARBINARY;TINYBLOB;BLOB;MEDIUMBLOB;LONGBLOB
	 */
	public static boolean isBytes(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_BINARY.equals(typeName) || TYPE_VARBINARY.equals(typeName);
	}

	/**
	 * @description blob系列:TINYBLOB;BLOB;MEDIUMBLOB;LONGBLOB
	 */
	public static boolean isBlob(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_TINYBLOB.equals(typeName) || TYPE_MEDIUMBLOB.equals(typeName) || TYPE_LONGBLOB.equals(typeName)
				|| TYPE_BLOB.equals(typeName);
	}

	// -----------------------------------------------需二次判断--------------------------------------------------------//
	/**
	 * @description If yearIsDateType configuration property is set to false, then
	 *              the returned object type is java.sql.Short. If set to true (the
	 *              default), then the returned object is of type java.sql.Date with
	 *              the date set to January 1st, at midnight.
	 */
	private static boolean isYear(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_YEAR.equals(typeName);
	}

	/**
	 * @description java.lang.Boolean if the configuration property tinyInt1isBit is
	 *              set to true (the default) and the storage size is 1, or
	 *              java.lang.Integer if not.
	 */
	private static boolean isTintInt(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_TINYINT.equals(typeName) || TYPE_BOOL.equals(typeName) || TYPE_BOOLEAN.equals(typeName);
	}

	/**
	 * @description BIT(1):Boolean;BIT(>1):byte[]
	 */
	private static boolean isBit(String typeName) {
		typeName = typeName.toUpperCase();
		return TYPE_BIT.equals(typeName);
	}
}
