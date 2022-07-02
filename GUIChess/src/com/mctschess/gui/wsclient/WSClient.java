package com.mctschess.gui.wsclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mctschess.dto.MoveDto;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Move;

public class WSClient {
	
	private Logger LOG = Logger.getLogger(WSClient.class.getName());
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private static final HttpClient client = HttpClient.newHttpClient();

	
	private String toJSON(IBoard board) {
		try {
			return mapper.writeValueAsString(board.toDto());
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Could not generate JSON file", e);
		}
	}
	
	private Move convertJsonToMove(HttpResponse<String> response) {
		if (response.statusCode() != 200) {
			LOG.warning("Server returned an error code: " + response.statusCode());
			return null;
		}
		String json = response.body();
		try {
			return Move.fromDto(mapper.readValue(json, MoveDto.class));
		} catch (JsonProcessingException e) {
			LOG.log(Level.WARNING, "Error while processing response JSON", e);
			return null;
		}
	}
	
	public CompletableFuture<Move> play(IBoard board) {

		BodyPublisher body = BodyPublishers.ofString(toJSON(board));
		HttpRequest playRequest = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/play"))
				.header("Content-Type", "application/json")
				.POST(body)
				.build();
		
		return client.sendAsync(playRequest, BodyHandlers.ofString())
			.thenApply(this::convertJsonToMove);
	}

}
