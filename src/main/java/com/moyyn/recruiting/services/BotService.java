package com.moyyn.recruiting.services;

import com.moyyn.recruiting.chatgpt.beans.ChatGptResponse;

public interface BotService {

    ChatGptResponse askChatGpt(String botRequest);
}