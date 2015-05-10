package de.aquariumshow.repositories;

import org.springframework.data.repository.CrudRepository;

import de.aquariumshow.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

}
