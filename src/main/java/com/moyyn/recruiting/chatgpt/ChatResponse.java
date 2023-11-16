package com.moyyn.recruiting.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
@Data
@Slf4j
public class ChatResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
    }
}