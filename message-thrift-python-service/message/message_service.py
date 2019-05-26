# -*- coding:utf-8 -*-.
import os

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
import smtplib
from email.mime.text import MIMEText
from email.header import Header

from api import MessageService

sender = "w@wktadmin.com"
authCode = os.environ.get("MAIL_PASS", "secret")


class MessageServiceHandler:
    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage, mobile: {}, message: {}".format(mobile, message))
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage, email: {}, message: {}".format(email, message))
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj["From"] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header("慕课网邮件", "utf-8")
        try:
            smtpObj = smtplib.SMTP("smtp.qq.com")
            smtpObj.login(sender, authCode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
        except smtplib.SMTPException:
            print("send mail fail")
            return False
        print("send mail success")
        return True


if __name__ == '__main__':
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)  # 处理
    transport = TSocket.TServerSocket("0.0.0.0", "19090")  # 谁负责监听
    tfactory = TTransport.TFramedTransportFactory()  # 传输方式
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()  # 协议

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)  # 服务
    print("python thrift server start")
    server.serve()
    print("python thrift server exit")
    # handler = MessageServiceHandler()
    # handler.sendEmailMessage("245584916@qq.com", "helloworld")
