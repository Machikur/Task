package com.crud.tasks.trello.client;

import com.crud.tasks.trello.CreatedTrelloCardDto;
import com.crud.tasks.trello.TrelloBadges;
import com.crud.tasks.trello.TrelloBoardDto;
import com.crud.tasks.trello.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getUsername()).thenReturn("marcink95");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", new ArrayList<>());

        URI uri = new URI("http://test.com/members/marcink95/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //when
        List<TrelloBoardDto> fetchedTrelloBoard = trelloClient.getTrelloBoards();

        //then
        Assert.assertEquals(1, fetchedTrelloBoard.size());
        Assert.assertEquals("test_id", fetchedTrelloBoard.get(0).getId());
        Assert.assertEquals("test_board", fetchedTrelloBoard.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchedTrelloBoard.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new TrelloBadges()
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //when
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        //then
        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
        Assert.assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //given
        URI uri = new URI("http://test.com/members/marcink95/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //when
        List<TrelloBoardDto> testList = trelloClient.getTrelloBoards();

        //then
        Assert.assertNotNull(testList);
        Assert.assertEquals(0, testList.size());
    }
}