package com.moyyn.recruiting.chatgpt.beans;


import lombok.Data;

import java.io.Serializable;

@Data
public class BotRequest implements Serializable {
    private String message;
}