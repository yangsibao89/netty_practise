package com.shengsiyuan.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by yangsibao on 2018/10/22.
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("杨嗣葆").setAge(29).setAdress("北京").build();

        byte[] student2ByteArray = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2.toString());

        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAdress());

    }
}
