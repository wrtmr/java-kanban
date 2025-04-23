import java.util.Objects;

public class Task {

    private int id;
    protected String name;
    protected String description;
    protected TaskStatus status;

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    public void setStatus(TaskStatus status) { this.status = status;}
    public TaskStatus getStatus() {return status;}

    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this != object && getClass() != object.getClass()) return false;
        Task otherTask = (Task) object;
        return Objects.equals(this.id, otherTask.getId());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (id > 0) {
            hash += id;
        }
        hash *= 31;
        if (name != null) {
            hash += name.hashCode();
        }
        return hash;
    }

    @Override
    public String toString(){
        return "Task ID: " + id + ", " +
                "Name: " + name +", " +
                "Description: " + description + ", " +
                "Status: " + status + ".";
    }
}
