import time
from transformers import pipeline

print("initialization")
time1 = time.time()

# 本地加载模型
classifier = pipeline(task="text-classification", model="./roberta-go_emotions", top_k=None)

print(time.time() - time1)

# 输入的句子
sentences = ["The rain tapped gently against the window, a soft reminder of the storm outside, yet within the quiet room, there was a stillness, a fragile peace. Her heart ached with the weight of loss, the memories of what once was, but somewhere beneath the sorrow, a flicker of warmth remained. It wasn’t much—just the faintest ember—but it was enough to keep her going. The pain of today would not last forever, she told herself, and though the road ahead seemed long and uncertain, she could sense that, one day, the clouds would part. There would be light again, perhaps softer, but no less beautiful."]

sentences2 = ["The sun was setting, casting a dim orange glow over the empty streets, as he sat alone on the bench, feeling the familiar sting of loneliness settle in. The echoes of laughter and voices from happier days seemed so far away, almost like a dream he could no longer reach. Yet, amidst the sadness, there was something else—a quiet, fragile hope. It whispered that maybe, just maybe, tomorrow would be different. The darkness wouldn’t last forever. Even in the stillness of his solitude, there was a sense that new beginnings were possible, waiting just beyond the horizon."]

# 模型推理
time2 = time.time()
model_outputs = classifier(sentences)
print(time.time() - time2)

model_outputs2 = classifier(sentences2)

print(model_outputs[0])
print(model_outputs2[0])
