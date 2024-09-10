package macrebot.serverproject.controllers;

import java.net.URI;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import macrebot.serverproject.models.Note;
import macrebot.serverproject.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        if (note != null) {
            return ResponseEntity.ok(note);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found with the id: " + id);
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        noteService.createNote(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(note.getId())
                .toUri();

        return ResponseEntity.created(location).body(note);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchNote(@RequestBody Note note, @PathVariable Long id) {
        Optional<Note> noteToPatch = noteService.modifyNoteById(note, id);

        if (noteToPatch.isPresent()) {
            Note notePatched = noteService.getNoteById(id);
            return ResponseEntity.ok(notePatched);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        boolean isDeleted = noteService.deleteNoteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

}
