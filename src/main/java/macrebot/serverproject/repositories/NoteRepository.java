package macrebot.serverproject.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import macrebot.serverproject.models.Note;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Note, Integer> {

    Optional<Note> findById(Long id);

    Boolean deleteNoteById(int id);

}