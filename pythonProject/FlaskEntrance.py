
from flask import Flask,request
from flask_restful import Resource, Api
from SentimentAnalysis import SentimentAnalysis

app = Flask(__name__)
api = Api(app)
sentimentClassifier = SentimentAnalysis()

@app.route("/")
def index():
    return "Hello World!"

class TextSentiment(Resource):
    def post(self):
        data = request.get_json()
        text = data['text']
        text_list = list()
        text_list.append(text)
        res = sentimentClassifier.sentimentAnalze(text_list)
        return res

api.add_resource(TextSentiment, '/sentiment')
app.run()
