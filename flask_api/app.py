from flask import Flask, jsonify
from kafka import KafkaConsumer, KafkaProducer
import json

app = Flask(__name__)

# 配置Kafka消费者和生产者
consumer = KafkaConsumer(
    'input_topic',  # Kafka 主题
    bootstrap_servers=['localhost:9092'],  # Kafka服务器地址
    auto_offset_reset='earliest',
    enable_auto_commit=True,
    group_id='flask-group',  # group_id
    value_deserializer=lambda x: json.loads(x.decode('utf-8'))
)

producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],  # Kafka服务器地址
    value_serializer=lambda x: json.dumps(x).encode('utf-8')
)

# 消费Kafka消息并返回响应
@app.route('/consume', methods=['GET'])
def consume_and_process():
    for message in consumer:
        # 处理消息
        print(f"Received message: {message.value}")
        processed_data = process_data(message.value)
        # 发送处理结果回Kafka
        produce_message(processed_data)
        return jsonify(processed_data)

# 数据处理逻辑
def process_data(data):
    return {'processed': data['value'] * 2}

# 向Kafka发送消息
def produce_message(data):
    producer.send('output_topic', value=data)  # 返回的topic
    producer.flush()

if __name__ == '__main__':
    app.run(debug=True)
