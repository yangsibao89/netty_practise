package com.shengsiyuan.grpc;

import com.shengsiyuan.proto.MyRequest;
import com.shengsiyuan.proto.MyResponse;
import com.shengsiyuan.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author yangsibao
 * @date 2019/6/20-17:27
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接受客户端消息: " + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }
}
