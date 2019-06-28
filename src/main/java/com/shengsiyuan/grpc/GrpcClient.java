package com.shengsiyuan.grpc;

import com.shengsiyuan.proto.StreamRequest;
import com.shengsiyuan.proto.StreamResponse;
import com.shengsiyuan.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

/**
 * @author yangsibao
 * @date 2019/6/20-17:38
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                .usePlaintext(true).build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

//        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
//
//        System.out.println(myResponse.getRealname());
//
//        managedChannel.shutdown();
//
//        System.out.println("--------------------");
//
//        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
//
//        while (iterator.hasNext()) {
//            StudentResponse studentResponse = iterator.next();
//
//            System.out.println(studentResponse.getName() + " , " + studentResponse.getAge() + " , " + studentResponse.getCity());
//
//        }
//
//        System.out.println("--------------------");

//        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                value.getStudentResponseList().forEach(studentResponse -> {
//                    System.out.println(studentResponse.getName());
//                    System.out.println(studentResponse.getAge());
//                    System.out.println(studentResponse.getCity());
//                    System.out.println("******");
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted!");
//            }
//        };
//
//        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
//
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
//
//        studentRequestStreamObserver.onCompleted();

        StreamObserver<StreamRequest> requestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                t.getMessage();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }
        });

        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        managedChannel.shutdown();

    }
}
