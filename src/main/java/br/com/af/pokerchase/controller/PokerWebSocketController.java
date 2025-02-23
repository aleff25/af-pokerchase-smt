package br.com.af.pokerchase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PokerWebSocketController {

  private final SimpMessagingTemplate messagingTemplate;

  public void sendEvent(String eventType, String eventData) {
    messagingTemplate.convertAndSend("/topic/events", new Event(eventType, eventData));
  }

  // Classe interna para representar eventos
  public static class Event {
    private String type;
    private String data;

    public Event(String type, String data) {
      this.type = type;
      this.data = data;
    }

    public String getType() {
      return type;
    }

    public String getData() {
      return data;
    }
  }
}
