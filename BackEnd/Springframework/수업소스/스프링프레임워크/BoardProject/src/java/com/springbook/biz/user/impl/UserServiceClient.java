package com.springbook.biz.user.impl;

import com.springbook.biz.board.impl.BoardService;
import com.springbook.biz.entity.BoardVO;
import com.springbook.biz.entity.UserVO;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class UserServiceClient {
    public static void main(String[] args) {

        // 1. Spring 컨테이너를 구동한다.
        AbstractApplicationContext container =
                new GenericXmlApplicationContext("applicationContext.xml");

        // 2. Spring 컨테이너로부터 BoardServiceImpl 객체를 Lookup 한다.
        UserService Service = (UserService) container.getBean("userService");

        // 3 게시글 입력

        String id = "test";
        String password = "test123";

        // 4. 게시글 생성(객체화)
        UserVO  vo = new UserVO();
        vo.setId(id);
        vo.setPassword(password);

        // 5. 게시글 등록 서비스 요청
        // boardService.insertBoard(vo);

        //  게시글 수정
        // boardService.updateBoard(vo);

        // 게시글 삭제
        // boardService.deleteBoard(vo);

        // 게시글 상세보기
        UserVO user = Service.getUser(vo);
        System.out.println(user);

//        // 6. 글 목록 검색 기능 테스트
//        List<BoardVO> boardList = userService.getUserList(vo);
//        for (BoardVO board : boardList) {
//            System.out.println("---> " + board);
//        }

        // 5. Spring 컨테이너 종료
        container.close();
    }
}
