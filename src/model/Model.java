package model;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Model of the TODO-List (contains the data).
 *
 * @author manschwa
 */
public class Model extends Observable
{
    /** A List for all the tasks */
    private LinkedList<Task> tasks;

    /**
     * Default-Constructor which creates a new LinkedList.
     */
    public Model()
    {
        this.tasks = new LinkedList<Task>();
        this.tasks.clear();
    }

    /**
     * Getter for the list with all the tasks.
     *
     * @return A LinkedList with Tasks
     */
    public LinkedList getList()
    {
        return this.tasks;
    }

    /**
     * Resets/Clears the LinkedList.
     */
    public void resetList()
    {
        this.tasks.clear();
    }

    /**
     * Adds a new task to the list and notifies the Observers.
     *
     * @param task Taks to be added.
     */
    public void addTask(Task task)
    {
        if(this.tasks.add(task))
        {
            setChanged();
            notifyObservers(tasks);
        }
    }

    /**
     * Deletes all selected tasks from the list.
     */
    public void deleteTasks()
    {
        for(int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).isChecked())
            {
                tasks.remove(i);
                // The counter must be set back to make sure to get the next
                // task. Otherwise the task after that would be evaluated.
                i--;
            }
        }
        setChanged();
        notifyObservers(tasks);
    }

    /**
     * Checks if one task is selected.
     *
     * @return true if exactly one task is selected, false otherwise.
     */
    public boolean oneChecked()
    {
        // be sure that only one checkbox is checked
        int counter = 0;
        for(int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).isChecked())
            {
                counter++;
            }
        }
        if(counter > 1)         // more than one task is selected
        {
            setChanged();
            notifyObservers("ERROR: More than one task is selected.");
            return false;

        }else if(counter == 0)  // no task is selected
        {
            setChanged();
            notifyObservers("ERROR: No task is selected.");
            return false;
        }else
        {
            return true;
        }
    }

    /**
     * Sets a new text of a selected Task.
     *
     * @param s The task's new text.
     */
    public void setTask(String s)
    {
        Task task = null;
        // find the selected task
        for(int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).isChecked())
            {
                task = tasks.get(i);
            }
        }
        if(task != null) // be sure that at least one task is checked
        {
            task.setTask(s);
            setChanged();
            notifyObservers(tasks);
        }
    }

    /**
     * Sets a new date of a selected Task.
     *
     * @param s The task's new date.
     */
    public void setDate(String s)
    {
        Task task = null;
        //find the selected task.
        for(int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).isChecked())
            {
                task = tasks.get(i);
            }
        }
        if(task != null) // be sure that at least one task is checked
        {
            task.setDate(s);
            setChanged();
            notifyObservers(tasks);
        }
    }

    /**
     * Getter for the length of the list.
     *
     * @return The length of the list.
     */
    public int getListLength()
    {
        return tasks.size();
    }
    
}
