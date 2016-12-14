package org.kdea.board;

import java.util.List;

public interface BoardDAO {

	public List<BoardVO> list(SearchPageVO SearchPageVo);
	public BoardVO detail(BoardVO vo);
	public int delete(int boardnum);
	public int update(BoardVO vo);
	public int writing(BoardVO vo);
	public BoardVO writingbefore();
	public int comment(BoardVO vo);
	public List<BoardVO> search(SearchPageVO SearchPageVo);
   
}
