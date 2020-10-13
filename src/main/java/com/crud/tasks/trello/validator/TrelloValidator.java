package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TrelloValidator {
    public void validateCard(final TrelloCard trelloCard) {
        if (trelloCard.getName().contains("test")) {
            log.info("Someone is testing my aplication");
        } else {
            log.info("Seems that my application is used in proper way");
        }
    }

    public List<TrelloBoard> validateTrelloBoards(List<TrelloBoard> trelloBoards) {
        log.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        log.info("Boards have been filtered. Current list size = " + filteredBoards.size());
        return filteredBoards;
    }
}
