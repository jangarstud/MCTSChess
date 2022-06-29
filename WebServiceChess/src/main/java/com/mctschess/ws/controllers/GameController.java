package com.mctschess.ws.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mctschess.ai.MCTS;
import com.mctschess.dto.BoardDto;
import com.mctschess.dto.MoveDto;
import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Move;

@RestController
public class GameController {

	@PostMapping(
			value = "/play",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public MoveDto play(@RequestBody BoardDto dto) {
		
		IBoard board = Board.fromDto(dto);
		
		MCTS mcts = new MCTS(board);
		mcts.learn(1000);
		Move move = mcts.selectBestMove();
		
		return move.toDto();
	}
}
