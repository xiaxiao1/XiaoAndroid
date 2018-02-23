package com.xiaxiao.xiaoandroid.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/1/29.
 * 求一个集合的幂集的工具类
 */

public class MiJi {

    private final int INT_LENGTH = 32;
    Object[] set;
    Set parent = new HashSet();

    public MiJi(Object[] set) {
        this.set = set;
    }
    public MiJi() {

    }
    public void setObjArray(Object[] objArray) {
        this.set = objArray;
    }
    public Set getSet() {
        return parent;
    }

    // 打印幂集
    public void print() {

        if (0 == set.length) {
            System.out.println("the set is none.");
            return;
        }
        List<Integer> pos = new ArrayList<>();
        int setCount = 1 << set.length;
        for (int i = 0; i < setCount; i++) {
            pos = getPos(i);
            Set child = new HashSet();
            for (int p : pos) {
                System.out.print(set[p] + ",");
                child.add(set[p]);
            }
            parent.add(child);
            System.out.println("\n---------------------");
        }
        int i=0;
    }

    /*
     *获取一个整数二进制形式中数字为1的位数，并返回位数列表
     */
    public List<Integer> getPos(int n) {
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < INT_LENGTH; i++) {
            if (0 == n || 0 > n) {
                break;
            }
            if ((n & 1) == 1) {
                pos.add(i);
            }
            n = n >> 1;
        }
        return pos;
    }

}
