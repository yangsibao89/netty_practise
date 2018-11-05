package com.shengsiyuan.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonServices;

/**
 * Created by yangsibao on 2018/11/5.
 */
public class ThriftClient {

    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);//transport与server对应
        TProtocol protocol = new TCompactProtocol(transport);
        PersonServices.Client client = new PersonServices.Client(protocol);//来自于生成代码的客户端实现

        try {
            transport.open();

            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("-----------");

            Person person1 = new Person();
            person1.setName("李四");
            person1.setAge(30);
            person1.setMarried(true);

            client.savePerson(person1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            transport.close();
        }
    }
}
