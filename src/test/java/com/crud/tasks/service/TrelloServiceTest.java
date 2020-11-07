package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.trello.CreatedTrelloCardDto;
import com.crud.tasks.trello.TrelloBadges;
import com.crud.tasks.trello.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void createdTrelloCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        when(trelloClient.createNewCard(any())).thenReturn(
                new CreatedTrelloCardDto("test_id", "test_name", "test_Url", new TrelloBadges()));
        when(adminConfig.getAdminMail()).thenReturn("Admin");

        //when
        CreatedTrelloCardDto createdCard = trelloService.createdTrelloCard(trelloCardDto);

        //then
        verify(simpleEmailService, times(1)).send(any(),eq(false));
        Assert.assertEquals(createdCard.getId(), "test_id");
    }


}