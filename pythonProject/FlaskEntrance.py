import json

import pika
from flask import Flask, request
from flask_restful import Resource, Api
from SentimentAnalysis import SentimentAnalysis
import threading

app = Flask(__name__)
api = Api(app)
sentimentClassifier = SentimentAnalysis()

RABBITMQ_HOST = '13.251.129.185'
QUEUE_NAME = 'record.queue'
EXCHANGE_NAME = "record.exchange"
ROUTING_KEY = "record.routingkey"


def callback(ch, method, properties, body):
    # 处理接收到的消息
    message = json.loads(body)
    print(message)
    text = message.get('content')  # 假设消息中有 'text' 字段
    text_list = list()
    text_list.append(text)
    response = sentimentClassifier.sentimentAnalze(text_list)  # 调用 POST 方法
    print(f"Sentiment analysis result: {response}")


#
def start_consuming():
    try:
        credentials = pika.PlainCredentials("md","123")
        connection = pika.BlockingConnection(pika.ConnectionParameters(host=RABBITMQ_HOST, port=31681,credentials=credentials,virtual_host="md"))
        print("Successfully connected to RabbitMQ")
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME)
        channel.queue_declare(queue=QUEUE_NAME, durable=True)
        channel.queue_bind(exchange=EXCHANGE_NAME, queue=QUEUE_NAME, routing_key=ROUTING_KEY)
        channel.basic_consume(queue=QUEUE_NAME, on_message_callback=callback, auto_ack=True)
        print('Waiting for messages. To exit press CTRL+C')
        channel.start_consuming()
    except pika.exceptions.AMQPConnectionError as e:
        print(f"Failed to connect to RabbitMQ: {e}")





@app.route("/")
def index():
    return "Hello World!"


#
# class TextSentiment(Resource):
#     def post(self):
#         data = request.get_json()
#         text = data['text']
#         text_list = list()
#         text_list.append(text)
#         res = sentimentClassifier.sentimentAnalze(text_list)
#         return res
# api.add_resource(TextSentiment, '/sentiment')
#

app.run()
start_consuming()
