package com.spro.service.sys;

import com.spro.dao.SequenceCounterMapper;
import com.spro.entity.sys.SequenceCounter;
import com.spro.service.BaseService;
import com.spro.util.DateUtil;
import com.spro.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class SequenceServiceImpl extends BaseService<SequenceCounter>{

    @Autowired
    private SequenceCounterMapper sequenceCounterMapper;

    /**
     * 重置模式：每天重置计数器
     */
    public static final int RESET_BY_DAY = 1;
    /**
     * 重置模式：以年度周数为单位重置计数器。周的第一天以该国日历为准，中国第一天是周日
     */
    public static final int RESET_BY_WEEK_OF_YEAR = 2;
    /**
     * 重置模式：当月改变，和周改变时都重置计数器。 周的第一天以该国日历为准，中国第一天是周日
     */
    public static final int RESET_BY_WEEK_OF_MONTH = 3;
    /**
     * 重置模式：每月重置计数器
     */
    public static final int RESET_BY_MONTH = 4;
    /**
     * 重置模式：每年重置计数器
     */
    public static final int RESET_BY_YEAR = 5;
    /**
     * 重置模式：不按时间重置计数器
     */
    public static final int RESET_NEVER = 6;

    /**
     * 取得系统生成的编码
     *
     * @param className
     * @return
     */
    public synchronized String generateCode(String className, String prefixCode) {
        paramMap.put("name",className);
        List<SequenceCounter> list = sequenceCounterMapper.querySequenceCounterByName(paramMap);
        if (list.size() != 1) {
            throw new RuntimeException("Sequence with name [" + className + "] config error.");
        }
        SequenceCounter sequence = list.get(0);

        Calendar currentCalendar = Calendar.getInstance();
        //currentCalendar.clear(Calendar.HOUR);
        //currentCalendar.clear(Calendar.MINUTE);
        //currentCalendar.clear(Calendar.SECOND);
        //currentCalendar.clear(Calendar.MILLISECOND);

        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.clear();
        lastCalendar.setTime(sequence.getChangeDate());
        //lastCalendar.clear(Calendar.HOUR);
        //lastCalendar.clear(Calendar.MINUTE);
        //lastCalendar.clear(Calendar.SECOND);
        //lastCalendar.clear(Calendar.MILLISECOND);

        int counter = 0;
        switch (sequence.getResetMode()) {
            case RESET_BY_DAY:
                if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                        && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                        && lastCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                    counter = sequence.getCounter() + sequence.getIncrement();
                } else {
                    counter = sequence.getInitValue();
                }
                break;
            case RESET_BY_WEEK_OF_YEAR:
                if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                        && lastCalendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
                    counter = sequence.getCounter() + sequence.getIncrement();
                } else {
                    counter = sequence.getInitValue();
                }
                break;
            case RESET_BY_WEEK_OF_MONTH:
                if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                        && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                        && lastCalendar.get(Calendar.WEEK_OF_MONTH) == currentCalendar.get(Calendar.WEEK_OF_MONTH)) {
                    counter = sequence.getCounter() + sequence.getIncrement();
                } else {
                    counter = sequence.getInitValue();
                }
                break;
            case RESET_BY_MONTH:
                if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                        && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
                    counter = sequence.getCounter() + sequence.getIncrement();
                } else {
                    counter = sequence.getInitValue();
                }
                break;
            case RESET_BY_YEAR:
                if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
                    counter = sequence.getCounter() + sequence.getIncrement();
                } else {
                    counter = sequence.getInitValue();
                }
                break;
            case RESET_NEVER:
                counter = sequence.getCounter() + sequence.getIncrement();
                break;
            default:
                break;
        }

        sequence.setCounter(counter);
        sequence.setChangeDate(currentCalendar.getTime());
        try {
            update(sequence);
        } catch (Exception e){
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.toString(sequence.getPrefix()));
        if (!StringUtil.isEmpty(prefixCode))
            sb.append(prefixCode);
        sb.append(StringUtil.toString(sequence.getSeperator()));
        if (!StringUtil.isEmpty(sequence.getDateFormat())) {
            sb.append(DateUtil.parseDateToStr(sequence.getChangeDate(), sequence.getDateFormat()));
            sb.append(StringUtil.toString(sequence.getSeperator()));
        }
        sb.append(StringUtil.formatLong(counter, sequence.getSeqLength()));
        sb.append(StringUtil.toString(sequence.getSeperator()));
        sb.append(StringUtil.toString(sequence.getSuffix()));
        return sb.toString();
    }
}
