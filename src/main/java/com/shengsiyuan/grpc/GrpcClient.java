package com.shengsiyuan.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * @author yangsibao
 * @date 2019/6/20-17:38
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                .usePlaintext(true).build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());

//        managedChannel.shutdown();

        System.out.println("--------------------");

        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());

        while (iterator.hasNext()) {
            StudentResponse studentResponse = iterator.next();

            System.out.println(studentResponse.getName() + " , " + studentResponse.getAge() + " , " + studentResponse.getCity());

        }

        System.out.println("--------------------");

        managedChannel.shutdown();

    }
}
