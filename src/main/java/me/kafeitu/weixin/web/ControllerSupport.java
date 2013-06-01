package me.kafeitu.weixin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Spring MVC的控制器支持
 * @author HenryYan
 */
public class ControllerSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
    public void binder(WebDataBinder binder) {
		// java.util.Date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));

		// java.sql.Date
		SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sqlDateFormat.setLenient(false);
		binder.registerCustomEditor(java.sql.Date.class, new CustomDateEditor(new MyDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * 获取Request对象
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ThreadContextHolder.getHttpRequest();
	}
}

class MyDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 1L;

	public MyDateFormat(String s) {
		super(s);
	}

	public java.sql.Date parse(String text) throws ParseException {
		return getSqlDate(text, "yyyy-MM-dd");
	}

    /**
     * 转换字符串为日期
     * @throws ParseException
     * @dateValue 日期字符串
     * @dateType 日期字符串格式
     */
    public static Date getDate(String dateValue, String dateType) throws ParseException {
        Assert.notNull(dateValue, "parameter dataValue can not be null.");
        Assert.notNull(dateType, "parameter dateType can not be null.");

        SimpleDateFormat sfdate = new SimpleDateFormat(dateType);
        return sfdate.parse(dateValue);
    }

    /**
     * 用字符串获得java.sql.Date日期
     * @throws ParseException
     * @dateValue 日期字符串
     * @dateType 日期字符串格式
     */
    public static java.sql.Date getSqlDate(String dateValue, String dateType) throws ParseException {
        Date date = getDate(dateValue, dateType);
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
}