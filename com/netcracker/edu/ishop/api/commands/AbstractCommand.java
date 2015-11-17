package netcracker.edu.ishop.api.commands;

public abstract class AbstractCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute(String[] cmdArgs);
    public abstract String toString();
}
