package com.fast.shadow.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.utils
 * @Version:1.0
 **/


public class BatchListUtil {

    public static <T> List<List<T>> groupList(List<T> list, int toIndex) {
        try {
            List<List<T>> listGroup = new ArrayList<>();
            int listSize = list.size();

            for (int i = 0; i < list.size(); i += toIndex) {
                if (i + toIndex > listSize) {
                    toIndex = listSize - i;
                }
                List<T> newList = list.subList(i, i + toIndex);
                listGroup.add(newList);
            }
            return listGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
