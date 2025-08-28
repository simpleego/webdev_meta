package com.springbook.biz.board.impl;

import com.springbook.biz.entity.BoardVO;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class BoardServiceClient {
    public static void main(String[] args) {

        // 1. Spring 컨테이너를 구동한다.
        AbstractApplicationContext container =
                new GenericXmlApplicationContext("applicationContext.xml");

        // 2. Spring 컨테이너로부터 BoardServiceImpl 객체를 Lookup 한다.
        BoardService boardService = (BoardService) container.getBean("boardService");

        // 3 게시글 입력
        String title = "test1 update";
        String content = "test1 board service..update.......";
        String writer = "simple";
        int seq = 2;

        // 4. 게시글 생성(객체화)
        BoardVO  vo = new BoardVO(title,writer,content);
        vo.setSeq(3);

        // 5. 게시글 등록 서비스 요청
        // boardService.insertBoard(vo);

        //  게시글 수정
        // boardService.updateBoard(vo);

        // 게시글 삭제
        // boardService.deleteBoard(vo);

        // 게시글 상세보기
        BoardVO board1 = boardService.getBoard(vo);
        System.out.println(board1);

        // 6. 글 목록 검색 기능 테스트
        List<BoardVO> boardList = boardService.getBoardList(vo);
        for (BoardVO board : boardList) {
            System.out.println("---> " + board);
        }

        // 5. Spring 컨테이너 종료
        container.close();
    }
}
