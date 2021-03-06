package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.mail.Mail;
import com.crud.tasks.trello.CreatedTrelloCardDto;
import com.crud.tasks.trello.TrelloBoardDto;
import com.crud.tasks.trello.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: new Trello card";
    private final SimpleEmailService simpleEmailService;
    private final TrelloClient trelloClient;
    private final AdminConfig adminConfig;


    public TrelloService(SimpleEmailService simpleEmailService, TrelloClient trelloClient, AdminConfig adminConfig) {
        this.simpleEmailService = simpleEmailService;
        this.trelloClient = trelloClient;
        this.adminConfig = adminConfig;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createdTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        Optional.ofNullable(newCard).ifPresent(card -> simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "New card " + card.getName() + " has been created on your Trello account"
        ), false));
        return newCard;
    }


}
