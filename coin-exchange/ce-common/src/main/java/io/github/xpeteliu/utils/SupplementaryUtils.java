package io.github.xpeteliu.utils;

import io.github.xpeteliu.dto.KLineType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupplementaryUtils {

    public static void copyBeanIgnoringNullProps(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Set<String> sourceFieldNames = retrieveAllFields(sourceClass)
                .map(Field::getName).collect(Collectors.toSet());
        Arrays.stream(sourceClass.getMethods()).forEach(sourceMethod -> {
            String sourceMethodName = sourceMethod.getName();
            if (sourceMethodName.startsWith("get")) {
                String InitCapsFieldName = sourceMethodName.substring(3);
                String fieldName = InitCapsFieldName.substring(0, 1).toLowerCase() + InitCapsFieldName.substring(1);
                if (!sourceFieldNames.contains(fieldName)) {
                    return;
                }
                Object sourceProp;
                try {
                    sourceProp = sourceMethod.invoke(source);
                    if (sourceProp != null) {
                        targetClass
                                .getMethod("set" + InitCapsFieldName, sourceProp.getClass())
                                .invoke(target, sourceProp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Skipped field " +
                            sourceClass.getName() + "." + fieldName +
                            " while copying to a bean of type " +
                            targetClass.getName());
                }
            }
        });
    }

    public static Stream<Field> retrieveAllFields(Class<?> clazz) {
        Class<?> aClass = clazz;
        Stream.Builder<Field[]> streamBuilder = Stream.builder();
        do {
            streamBuilder.accept(aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != Object.class);
        return streamBuilder.build().flatMap(Arrays::stream);
    }

    public static List<BigDecimal> kLineString2OutputList(String cs) {
        String[] split = cs.split(",");
        if (split.length != 6) {
            throw new IllegalArgumentException("Invalid compact string");
        }
        return List.of(
                new BigDecimal(split[0]),
                new BigDecimal(split[1]),
                new BigDecimal(split[2]),
                new BigDecimal(split[3]),
                new BigDecimal(split[4]),
                new BigDecimal(split[5])
        );
    }

    public static LocalDateTime getKLineTime(LocalDateTime localDateTime, KLineType lineType) {
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hours = localDateTime.getHour();
        int minutes = localDateTime.getMinute();
        int seconds = localDateTime.getSecond();
        int week = localDateTime.getDayOfWeek().getValue();
        switch (lineType) {
            case ONE_MINUTE: // 1分钟
                return LocalDateTime.of(year, month, day, hours, minutes, 0, 000);
            case FIVE_MINUTES: // 5分钟
                return LocalDateTime.of(year, month, day, hours, minutes / 5 * 5, 0, 000);
            case FIFTEEN_MINUTES: // 15分钟
                return LocalDateTime.of(year, month, day, hours, minutes / 15 * 15, 0, 000);
            case THIRTY_MINUTES: // 30分钟
                return LocalDateTime.of(year, month, day, hours, minutes / 30 * 30, 0, 000);
            case ONE_HOUR: // 1小时
                return LocalDateTime.of(year, month, day, hours, 0, 0, 000);
            case TWO_HOURS: // 2小时
                return LocalDateTime.of(year, month, day, hours / 2 * 2, 0, 0, 000);
            case FOUR_HOURS: // 4小时
                return LocalDateTime.of(year, month, day, hours / 4 * 4, 0, 0, 000);
            case SIX_HOURS: // 6小时
                return LocalDateTime.of(year, month, day, hours / 6 * 6, 0, 0, 000);
            case TWELVE_HOURS: // 12小时
                return LocalDateTime.of(year, month, day, hours / 12 * 12, 0, 0, 000);
            case ONE_DAY: // 1天
                return LocalDateTime.of(year, month, day, 0, 0, 0, 000);
            case ONE_WEEK: // 1周
                if (week != 7) {
                    return LocalDateTime.of(year, month, day, 0, 0, 0, 000).plusDays(-week);
                }
                return LocalDateTime.of(year, month, day, 0, 0, 0, 000);
            case ONE_MONTH: // 1月
                return LocalDateTime.of(year, month, 1, 0, 0, 0, 000);
            case ONE_YEAR: // 1年
                return LocalDateTime.of(year, 1, 1, 0, 0, 0, 000);
        }
        return null;
    }

}
