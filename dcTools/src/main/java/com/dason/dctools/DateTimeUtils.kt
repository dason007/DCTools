package com.dason.dctools

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object DateTimeUtils {
    private const val DEFAULT_DATE_PATTERN: String = "yyyy-MM-dd HH:mm:ss"

    private val zodiacArr: Array<String> =
        arrayOf("猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊")

    private val constellationArr: Array<String> = arrayOf(
        "水瓶座",
        "双鱼座",
        "白羊座",
        "金牛座",
        "双子座",
        "巨蟹座",
        "狮子座",
        "处女座",
        "天秤座",
        "天蝎座",
        "射手座",
        "魔羯座"
    )
    //星座对应的日期
    private val constellationEdgeDay: IntArray = intArrayOf(20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22)

    @JvmStatic
    fun yearByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.YEAR]
    }

    @JvmStatic
    fun monthByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.MONTH] + 1
    }

    @JvmStatic
    fun weekByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.DAY_OF_WEEK]
    }

    @JvmStatic
    fun dayByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.DATE]
    }

    @JvmStatic
    fun hourByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.HOUR_OF_DAY]
    }

    @JvmStatic
    fun minuteByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.MINUTE]
    }

    @JvmStatic
    fun secondByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.SECOND]
    }

    @JvmStatic
    fun millisecondByDate(date: Date?): Int {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return cal[Calendar.MILLISECOND]
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    @JvmStatic
    fun zodicaByDate(date: Date?): String {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        return zodiacArr[cal[Calendar.YEAR] % 12]
    }

    /**
     * 根据日期获取星座
     *
     * @return
     */
    @JvmStatic
    fun constellationByDate(date: Date?): String {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        var month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        if (day < constellationEdgeDay[month]) {
            month -= 1
        }
        if (month >= 0) {
            return constellationArr[month]
        }
        return constellationArr[11]
    }


    /***
     * 获取与一个日期相差几个小时的时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的开始日期
     */
    @JvmStatic
    fun datetimeOfHourByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.HOUR_OF_DAY, offset)
        return cal.time
    }

    /***
     * 获取于一个日期相差几天的时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的开始日期
     */
    @JvmStatic
    fun datetimeOfDayByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.DATE, offset)
        return cal.time
    }

    /***
     * 获取于一个日期相差几天的某天的开始时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的开始日期
     */
    @JvmStatic
    fun beginTimeOfDayByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.DATE, offset)
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    /***
     * 获取于一个日期相差几天的某天的结束时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的结束日期
     */
    @JvmStatic
    fun endTimeOfDayByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.DATE, offset)
        cal[Calendar.HOUR_OF_DAY] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 59
        cal[Calendar.MILLISECOND] = 999
        return cal.time
    }


    /***
     * 获取于一个日期对应的周的开始时间
     *
     * @param date      参照日期
     * @return 对应周的开始日期，周日为第一天
     */
    @JvmStatic
    fun beginTimeOfWeekByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val weekday = weekByDate(date)
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.DATE, 7 * offset - weekday + 1)
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    /***
     * 获取于一个日期对应的周的结束时间
     *
     * @param date      参照日期
     * @return 对应周的结束日期
     */
    @JvmStatic
    fun endTimeOfWeekByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val weekday = weekByDate(date)
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.DATE, 7 * offset + 7 - weekday)
        cal[Calendar.HOUR_OF_DAY] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 59
        cal[Calendar.MILLISECOND] = 999
        return cal.time
    }

    /***
     * 获取于一个日期对应的月的开始时间
     *
     * @param date      参照日期
     * @return 对应月的开始日期
     */
    @JvmStatic
    fun beginTimeOfMonthByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.MONTH, offset)
        cal[Calendar.DATE] = 1
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    /***
     * 获取于一个日期对应的月的结束时间
     *
     * @param date      参照日期
     * @return 对应月的结束日期
     */
    @JvmStatic
    fun endTimeOfMonthByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.MONTH, offset)
        val day = cal.getActualMaximum(Calendar.DATE)
        cal[Calendar.DATE] = day
        cal[Calendar.HOUR_OF_DAY] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 59
        cal[Calendar.MILLISECOND] = 999
        return cal.time
    }

    /***
     * 获取于一个日期对应的年的开始时间
     *
     * @param date      参照日期
     * @return 对应年的开始日期
     */
    @JvmStatic
    fun beginTimeOfYearByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.YEAR, offset)
        cal[Calendar.MONTH] = Calendar.JANUARY
        cal[Calendar.DATE] = 1
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    /***
     * 获取于一个日期对应的年的结束时间
     *
     * @param date      参照日期
     * @return 对应年的结束日期
     */
    @JvmStatic
    fun endTimeOfYearByDate(date: Date?, offset: Int): Date {
        require(!EmptyUtils.isEmpty(date)) { "The date can not be empty" }
        val cal = Calendar.getInstance()
        cal.time = date!!
        cal.add(Calendar.YEAR, offset)
        cal[Calendar.MONTH] = Calendar.DECEMBER
        cal[Calendar.DATE] = 31
        cal[Calendar.HOUR_OF_DAY] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 59
        cal[Calendar.MILLISECOND] = 999
        return cal.time
    }

    /***
     * 两个时间间的间隔
     * @param date1  第一个时间
     * @param date2  第二个时间
     * @return 两个时间间的间隔(毫秒)
     */
    @JvmStatic
    fun intervalBetweenDates(date1: Date?, date2: Date?): Long {
        require(!EmptyUtils.isEmpty(date1)) { "The date1 can not be empty" }
        require(!EmptyUtils.isEmpty(date2)) { "The date2 can not be empty" }
        return date1!!.time - date2!!.time
    }

    /**
     * 获取两个日期中的最大日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    @JvmStatic
    fun maximumBetweenDates(beginDate: Date?, endDate: Date?): Date? {
        if (beginDate == null) {
            return endDate
        }
        if (endDate == null) {
            return beginDate
        }
        if (beginDate.after(endDate)) {
            return beginDate
        }
        return endDate
    }

    /**
     * 获取两个日期中的最小日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    @JvmStatic
    fun minimumBetweenDates(beginDate: Date?, endDate: Date?): Date? {
        if (beginDate == null) {
            return endDate
        }
        if (endDate == null) {
            return beginDate
        }
        if (beginDate.after(endDate)) {
            return endDate
        }
        return beginDate
    }

    /**
     * 两个日期相差的天数(隔着一个24点就算是一天)
     *
     * @param date1
     * @param date2
     * @return
     */
    @JvmStatic
    fun differenceBetweenDates(date1: Date?, date2: Date?): Long {
        val max = maximumBetweenDates(date1, date2)
        val min = minimumBetweenDates(date1, date2)
        val end = endTimeOfDayByDate(max, 0)
        val start = beginTimeOfDayByDate(min, 0)
        val milliseconds = intervalBetweenDates(end, start)
        val millisecondsDay = (24 * 60 * 60 * 1000).toLong()
        val days = milliseconds / millisecondsDay
        return days
    }

    /**
     * 格式化日期
     * yyyy-MM-dd HH:mm:ss
     *
     * @param @param  date
     * @param @return
     * @Description:
     */
    @JvmStatic
    fun format(sDate: String): Date? {
        return format(sDate, null)
    }

    @JvmStatic
    fun format(sDate: String, timeZone: TimeZone?): Date? {
        return format(sDate, timeZone, DEFAULT_DATE_PATTERN)
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun format(
        sDate: String,
        timeZone: TimeZone? = TimeZone.getTimeZone("GMT+8"),
        pattern: String?
    ): Date? {
        try {
            val sd = SimpleDateFormat(pattern)
            if (EmptyUtils.isNotEmpty(timeZone)) {
                sd.timeZone = timeZone!!
            }
            return sd.parse(sDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 格式化日期
     * yyyy-MM-dd HH:mm:ss
     *
     * @param @param  date
     * @param @return
     * @Description:
     */
    @JvmStatic
    fun format(date: Date): String? {
        return format(date, null)
    }

    @JvmStatic
    fun format(date: Date, timeZone: TimeZone?): String? {
        return format(date, timeZone, DEFAULT_DATE_PATTERN)
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun format(
        date: Date,
        timeZone: TimeZone? = TimeZone.getTimeZone("GMT+8"),
        pattern: String?
    ): String? {
        try {
            val sd = SimpleDateFormat(pattern)
            if (EmptyUtils.isNotEmpty(timeZone)) {
                sd.timeZone = timeZone!!
            }
            return sd.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    fun isAvailablePattern(pattern: String?): Boolean {
        if (EmptyUtils.isEmpty(pattern)) {
            return false
        }
        val dateTime = format(Date(), null, pattern)
        return EmptyUtils.isNotEmpty(dateTime)
    }


}