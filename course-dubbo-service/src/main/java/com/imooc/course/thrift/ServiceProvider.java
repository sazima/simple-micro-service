package com.imooc.course.thrift;

import com.imooc.thrift.user.UserService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 调用thrift服务(初始化)
 */
@Component
public class ServiceProvider {
    @Value("${thrift.user.ip}")
    private String serverIp;
    @Value("${thrift.user.port}")
    private int serverPort;

    private enum ServiceType {
        USER
    }

    public UserService.Client getUserService() {
        return getService(serverIp, serverPort, ServiceType.USER);
    }


    private  <T> T getService(String ip, int port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip, port, 3000);
        TFramedTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient result = null;
        switch (serviceType) {
            case USER:
                result = new UserService.Client(protocol);
                break;
        }
        return (T)result;
    }
}
