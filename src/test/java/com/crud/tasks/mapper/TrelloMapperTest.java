package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.TrelloBoardDto;
import com.crud.tasks.trello.TrelloCardDto;
import com.crud.tasks.trello.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardTest() {
        //given
        TrelloBoard trelloBoardOne = new TrelloBoard("1", "TestBoard", new ArrayList<>());
        TrelloBoard trelloBoardTwo = new TrelloBoard("2", "TestBoard", new ArrayList<>());
        ArrayList<TrelloBoard> boardList = new ArrayList<>(Arrays.asList(trelloBoardOne, trelloBoardTwo));

        //when
        List<TrelloBoardDto> trelloListDto = trelloMapper.mapToBoardsDto(boardList);
        TrelloBoardDto trelloBoardDtoOne = trelloListDto.get(0);
        TrelloBoardDto trelloBoardDtoTwo = trelloListDto.get(1);

        //then
        Assert.assertEquals(trelloBoardOne.getId(), trelloBoardDtoOne.getId());
        Assert.assertEquals(trelloBoardOne.getName(), trelloBoardDtoOne.getName());
        Assert.assertEquals(trelloBoardTwo.getId(), trelloBoardDtoTwo.getId());
        Assert.assertEquals(trelloBoardTwo.getName(), trelloBoardDtoTwo.getName());
        Assert.assertEquals(trelloBoardOne.getLists().size(), trelloBoardDtoOne.getLists().size());
        Assert.assertEquals(trelloBoardTwo.getLists().size(), trelloBoardDtoTwo.getLists().size());

    }

    @Test
    public void mapToBoardDtoTest() {
        //given
        TrelloBoardDto trelloBoardDtoOne = new TrelloBoardDto("1", "TestBoard", new ArrayList<>());
        TrelloBoardDto trelloBoardDtoTwo = new TrelloBoardDto("2", "TestBoard", new ArrayList<>());
        ArrayList<TrelloBoardDto> boardListDto = new ArrayList<>(Arrays.asList(trelloBoardDtoOne, trelloBoardDtoTwo));

        //when
        List<TrelloBoard> trelloListDto = trelloMapper.mapToBoards(boardListDto);
        TrelloBoard trelloBoardOne = trelloListDto.get(0);
        TrelloBoard trelloBoardTwo = trelloListDto.get(1);

        //then
        Assert.assertEquals(trelloBoardOne.getId(), trelloBoardDtoOne.getId());
        Assert.assertEquals(trelloBoardOne.getName(), trelloBoardDtoOne.getName());
        Assert.assertEquals(trelloBoardTwo.getId(), trelloBoardDtoTwo.getId());
        Assert.assertEquals(trelloBoardTwo.getName(), trelloBoardDtoTwo.getName());
        Assert.assertEquals(trelloBoardOne.getLists().size(), trelloBoardDtoOne.getLists().size());
        Assert.assertEquals(trelloBoardTwo.getLists().size(), trelloBoardDtoTwo.getLists().size());

    }

    @Test
    public void mapToListTest() {
        //given
        List<TrelloListDto> trelloListDto = new ArrayList<>(Arrays.asList(
                new TrelloListDto("1", "test", true),
                new TrelloListDto("2", "test", true)
        ));

        //when
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);
        TrelloList trelloListOne = trelloLists.get(0);
        TrelloList trelloListTwo = trelloLists.get(1);

        //then
        Assert.assertEquals(trelloListOne, new TrelloList("1", "test", true));
        Assert.assertEquals(trelloListTwo, new TrelloList("2", "test", true));
    }

    @Test
    public void mapToListDtoTest() {
        //given
        List<TrelloList> trelloList = new ArrayList<>(Arrays.asList(
                new TrelloList("1", "test", true),
                new TrelloList("2", "test", true)
        ));

        //when
        List<TrelloListDto> trelloLists = trelloMapper.mapToListDto(trelloList);
        TrelloListDto trelloListOne = trelloLists.get(0);
        TrelloListDto trelloListTwo = trelloLists.get(1);

        //then
        Assert.assertEquals(trelloListOne, new TrelloListDto("1", "test", true));
        Assert.assertEquals(trelloListTwo, new TrelloListDto("2", "test", true));
    }

    @Test
    public void mapToCardDtoTest() {
        //given
        TrelloCard trelloCard = new TrelloCard("test", "testDesc", "testPos", "testListid");
        String name = trelloCard.getName();
        String desc = trelloCard.getDescription();
        String pos = trelloCard.getPos();
        String listId = trelloCard.getListId();

        //when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //then
        Assert.assertEquals(trelloCardDto.getName(), name);
        Assert.assertEquals(trelloCardDto.getDescription(), desc);
        Assert.assertEquals(trelloCardDto.getPos(), pos);
        Assert.assertEquals(trelloCardDto.getListId(), listId);
    }

    @Test
    public void mapToCardTest() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "testDesc", "testPos", "testListid");
        String name = trelloCardDto.getName();
        String desc = trelloCardDto.getDescription();
        String pos = trelloCardDto.getPos();
        String listId = trelloCardDto.getListId();

        //when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //then
        Assert.assertEquals(trelloCard.getName(), name);
        Assert.assertEquals(trelloCard.getDescription(), desc);
        Assert.assertEquals(trelloCard.getPos(), pos);
        Assert.assertEquals(trelloCard.getListId(), listId);
    }


}