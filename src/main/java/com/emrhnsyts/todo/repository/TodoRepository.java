package com.emrhnsyts.todo.repository;

import com.emrhnsyts.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
