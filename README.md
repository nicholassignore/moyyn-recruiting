# moyyn-recruiting

limitations: the name is composed only by first and last name



$ curl https://api.openai.com/v1/chat/completions \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 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" \
-d '{
"model": "gpt-3.5-turbo",
"messages": [{"role": "user", "content": "can you translate the string \"Candidate(fileName=file, fileType=application/pdf, firstName=mickey, lastName=mouse, age=25, married=true, skills=[java, spring, react])\" to a json string ?"}]
}'
