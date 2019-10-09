package com.kaola.sleep.utils;

import java.util.List;
import java.util.Set;

/**
 * Description：
 * Time：2019-09-23 22:18
 *
 * @author：ltc
 */
public class ListUtils {
    public  static  boolean isEmpty(List list){
        return  list == null || list.size() == 0;
    }
    public  static  boolean isEmpty(Set list){
        return  list == null || list.size() == 0;
    }
}
