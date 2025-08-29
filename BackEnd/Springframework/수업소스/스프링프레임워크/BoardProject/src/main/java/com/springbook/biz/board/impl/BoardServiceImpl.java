package com.springbook.biz.board.impl;

import com.springbook.biz.common.Log4JAdvice;
import com.springbook.biz.common.LogAdvice;
import com.springbook.biz.entity.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// BoardServiceImpl boardService = new BoardServiceImpl();
@Service("boardService") // boardService = new BoardServiceImpl()
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardService boardDAO;

    public BoardServiceImpl() {
        System.out.println("BoardServiceImpl() 생성자 호출됨");
    }

    @Override
    public void insertBoard(BoardVO vo) {
//        if (vo.getSeq() == 0) {
//            throw new IllegalArgumentException("0번 글은 등록할 수 없습니다.");
//        }
        boardDAO.insertBoard(vo);
    }

    @Override
    public void updateBoard(BoardVO vo) {
        boardDAO.updateBoard(vo);
    }

    @Override
    public void deleteBoard(BoardVO vo) {
        boardDAO.deleteBoard(vo);
    }

    @Override
    public BoardVO getBoard(BoardVO vo) {
        return boardDAO.getBoard(vo);
    }

    @Override
    public List<BoardVO> getBoardList(BoardVO vo) {
        return boardDAO.getBoardList(vo);
    }
}
