FROM python:3.6
MAINTAINER message-thrift-service w@wktadmin.com
RUN pip install thrift
COPY message/ /message
WORKDIR /message
ENTRYPOINT ["python", "message_service.py"]