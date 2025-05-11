package data;

public interface RequestHandler {
    Answer handleRequestFromClient(Task req);
}
