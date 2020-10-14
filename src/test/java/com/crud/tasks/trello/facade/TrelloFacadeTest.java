package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.TrelloBoardDto;
import com.crud.tasks.trello.TrelloListDto;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloValidator trelloValidator;

    @Test
    public void shouldFetchEmptyList() {

        //given
        List<TrelloListDto> trelloLists = new ArrayList<>(Collections.singletonList(
                new TrelloListDto("1", "test_list", false)));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>(Collections.singletonList(
                new TrelloBoardDto("1", "test", trelloLists)));

        List<TrelloList> mappedtrelloLists = new ArrayList<>(Collections.singletonList(
                new TrelloList("1", "test_list", false)));


        List<TrelloBoard> mappedtrelloBoards = new ArrayList<>(Collections.singletonList(
                new TrelloBoard("1", "test", mappedtrelloLists)));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        Mockito.when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedtrelloBoards);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(trelloValidator.validateTrelloBoards(mappedtrelloBoards)).thenReturn(new ArrayList<>());

        //when
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {

        //given
        List<TrelloListDto> trelloLists = new ArrayList<>(Collections.singletonList(
                new TrelloListDto("1", "my_list", false)));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>(Collections.singletonList(
                new TrelloBoardDto("1", "my_task", trelloLists)));

        List<TrelloList> mappedtrelloLists = new ArrayList<>(Collections.singletonList(
                new TrelloList("1", "my_list", false)));

        List<TrelloBoard> mappedtrelloBoards = new ArrayList<>(Collections.singletonList(
                new TrelloBoard("1", "my_task", mappedtrelloLists)));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        Mockito.when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedtrelloBoards);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(trelloBoards);
        Mockito.when(trelloValidator.validateTrelloBoards(mappedtrelloBoards)).thenReturn(mappedtrelloBoards);

        //when
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            Assert.assertEquals("1", trelloBoardDto.getId());
            Assert.assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                Assert.assertEquals("1", trelloListDto.getId());
                Assert.assertEquals("my_list", trelloListDto.getName());
                Assert.assertFalse(trelloListDto.isClosed());
            });
        });
    }

}