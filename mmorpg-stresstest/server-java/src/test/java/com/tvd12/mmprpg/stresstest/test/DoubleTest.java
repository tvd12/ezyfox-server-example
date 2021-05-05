package com.tvd12.mmprpg.stresstest.test;

import com.tvd12.ezyfox.codec.MsgPackIntSerializer;
import com.tvd12.ezyfox.io.EzyBytes;

import java.util.Arrays;

public class DoubleTest {

    public static void main(String[] args) {
        double value = 160;
        byte[] bytes = EzyBytes.getBytes(value);
        System.out.println(Arrays.toString(bytes));
        System.out.println((byte)((0xa0 | 8) & 0XFF));
        System.out.println(Arrays.toString(MsgPackIntSerializer.getInstance().serialize(1)));
    }

}
