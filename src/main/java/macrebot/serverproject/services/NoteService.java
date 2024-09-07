package macrebot.serverproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import macrebot.serverproject.models.Note;
import macrebot.serverproject.repositories.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CounterIdService counterIdService;

    public Note createNote(Note note) {
        note.setId(counterIdService.generateId(2L));
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Boolean deleteNoteById(Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            noteRepository.delete(optionalNote.get());
            return true;
        }
        return false;
    }

    public Optional<Note> modifyNoteById(Note note, Long id) {
        Optional<Note> noteToModify = noteRepository.findById(id);

        if (noteToModify.isPresent()) {
            if (note.getId() != null) {
                noteToModify.get().setId(note.getId());
            }
            if (note.getName() != null) {
                noteToModify.get().setName(note.getName());
            }
            if (note.getContent() != null) {
                noteToModify.get().setContent(note.getContent());
            }
            noteRepository.save(noteToModify.get());
            return noteToModify;
        }
        return Optional.empty();
    }

}
