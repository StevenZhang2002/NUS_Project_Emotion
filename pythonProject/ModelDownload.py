from transformers import pipeline

# 下载模型并保存到本地
classifier = pipeline(task="text-classification", model="SamLowe/roberta-base-go_emotions")
classifier.save_pretrained('./roberta-go_emotions')
