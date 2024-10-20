import time
from transformers import pipeline


class SentimentAnalysis:
    def __init__(self):
        preload_time = time.time()
        self.text_classifier = pipeline(task="text-classification", model="./roberta-go_emotions", top_k=None)
        print("Loaded Model Successfully")

    def sentimentAnalze(self,sentence):

        result = self.text_classifier(sentence)
        return result


if __name__ == '__main__':
    classifierTools = SentimentAnalysis();
    print(classifierTools.sentimentAnalze(["The rain tapped gently against the window, a soft reminder of the storm outside, yet within the quiet room, there was a stillness, a fragile peace. Her heart ached with the weight of loss, the memories of what once was, but somewhere beneath the sorrow, a flicker of warmth remained. It wasn’t much—just the faintest ember—but it was enough to keep her going. The pain of today would not last forever, she told herself, and though the road ahead seemed long and uncertain, she could sense that, one day, the clouds would part. There would be light again, perhaps softer, but no less beautiful."]))
