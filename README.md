# moyyn-recruiting

limitations: the name is composed only by first and last name



$ curl https://api.openai.com/v1/chat/completions \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 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" \
-d '{
"model": "gpt-3.5-turbo",
"messages": [{"role": "user", "content": "Hello!"}]
}'
