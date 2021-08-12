package io.github.xpeteliu.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
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

}
