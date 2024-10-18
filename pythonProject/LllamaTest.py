import json
from llamaapi import LlamaAPI

# Initialize the SDK
llama = LlamaAPI("LA-0cce0081ac7a40f09c37468a06893c212274f31f97d242c4a4e65091815a585d")

# Build the API request
api_request_json = {
    "model": "llama3-8b",
    "messages": [
        {"role": "user", "content": "What is the weather like in Boston?"},
    ],
    "functions": [
        {
            "name": "get_current_weather",
            "description": "Get the current weather in a given location",
            "parameters": {
                "type": "object",
                "properties": {
                    "location": {
                        "type": "string",
                        "description": "The city and state, e.g. San Francisco, CA",
                    },
                    "days": {
                        "type": "number",
                        "description": "for how many days ahead you wants the forecast",
                    },
                    "unit": {"type": "string", "enum": ["celsius", "fahrenheit"]},
                },
            },
            "required": ["location", "days"],
        }
    ],
    "stream": False,
    "function_call": "get_current_weather",
}

# Execute the Request
response = llama.run(api_request_json)
print(json.dumps(response.json(), indent=2))
