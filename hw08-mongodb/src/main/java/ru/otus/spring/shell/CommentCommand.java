package ru.otus.spring.shell;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.CommentService;

@ShellComponent
@ShellCommandGroup("Book commands")
@RequiredArgsConstructor
public class CommentCommand {

    private final CommentService commentService;

    @ShellMethod(value = "Insert a new comment", key = {"ic", "insertcomment"})
    public ObjectId insert(@ShellOption ObjectId bookId,
                           @ShellOption(defaultValue = "that man was mad!!!") String text) {
        return commentService.save(null, bookId, text).getId();
    }

    @ShellMethod(value = "Get a comment by id", key = {"gc", "getcomment"})
    public Comment getById(@ShellOption ObjectId id) {
        return commentService.getById(id);
    }

    @ShellMethod(value = "Get all comments by bookId", key = {"gac", "getallcomment"})
    public List<Comment> getAll(@ShellOption ObjectId bookId) {
        return commentService.getByBookId(bookId);
    }

    @ShellMethod(value = "Delete a comment by id", key = {"dc", "deletecomment"})
    public void deleteById(@ShellOption ObjectId id) {
        commentService.deleteById(id);
    }

}
