package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {

    @InjectMocks
    private TrelloValidator trelloValidator;

    @Mock
    private Logger logger = LoggerFactory.getLogger(TrelloValidator.class);

    @Test
    public void validateTrelloBoards() {
        //given
        List<TrelloBoard> trelloBoards = new ArrayList<>(Arrays.asList(
                new TrelloBoard("test", "test", new ArrayList<>()),
                new TrelloBoard("test", "test", new ArrayList<>()),
                new TrelloBoard("test", "test", new ArrayList<>()),
                new TrelloBoard("nothing", "nothing", new ArrayList<>())
        ));

        //when
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoards);

        //then
        Assert.assertEquals(3, filteredList.size());
        Assert.assertNotEquals(trelloBoards.size(), filteredList.size());
    }
}