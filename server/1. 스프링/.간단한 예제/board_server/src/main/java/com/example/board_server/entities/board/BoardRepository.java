package com.example.board_server.entities.board;

import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board,Integer> {
}
