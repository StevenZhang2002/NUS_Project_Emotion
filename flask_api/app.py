from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/')
def home():
    return jsonify(message="Hello, Flask from NUS_Project_Emotion!")

if __name__ == '__main__':
    app.run(debug=True)
