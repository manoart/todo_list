package model;


/**
 * An simple Class to represent a task for the TODO-List with a text and a date.
 *
 * @author manschwa
 */
public class Task
{
    /** Flag to see if the task is selected. */
    private boolean checked;
    /** The text of the task. */
    private String task;
    /** The due date of the task. */
    private String date;

    /**
     * Default-Constructor.
     */
    public Task()
    {
        this(false, "", "");
    }

    /**
     * Custom-Constructor of a task.
     *
     * @param checked Shows if the task is selected.
     * @param task The task's text.
     * @param date The task's due date.
     */
    public Task(boolean checked, String task, String date)
    {
        this.checked = checked;
        this.task = task;
        this.date = date;
    }

    /**
     * Getter for instance variable checked.
     *
     * @return true if task is selected, false otherwise.
     */
    public boolean isChecked()
    {
        return checked;
    }

    /**
     * Getter for instance variable task.
     *
     * @return Text of the task as a String.
     */
    public String getTask()
    {
        return this.task;
    }

    /**
     * Getter for instance variable date.
     *
     * @return Date of the task as a String.
     */
    public String getDate()
    {
        return this.date;
    }

    /**
     * Setter for instance variable checked.
     */
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    /**
     * Setter for instance variable task.
     */
    public void setTask(String task)
    {
        this.task = task;
    }

    /**
     * Setter for instance variable date.
     */
    public void setDate(String date)
    {
        this.date = date;
    }
}
