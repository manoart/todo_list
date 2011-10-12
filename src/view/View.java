package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Model;
import model.Task;

/**
 * GUI for the TODO-List.
 * 
 * @author manschwa
 */
public class View implements Observer
{
    /** Main frame. */
    private JFrame frame;
    /** Panel for the headline. */
    private JPanel headPanel;
    /** Panel for the checkboxes. */
    private JPanel checkboxPanel;
    /** Panel for the tasks. */
    private JPanel taskPanel;
    /** 2D-Array for the labels. */
    private JLabel[][] labels;
    /** Panel for the buttons. */
    private JPanel buttonPanel;
    /** (Observable) model of the TODO-implementation. */
    private Model model;

    /**
     * Constuctor for the GUI of the TODO-List.
     */
    public View()
    {
        frame = new JFrame("TODO");
        frame.setJMenuBar(menubar());

        model = new Model();
        model.addObserver(this);

        headPanel = new JPanel(new GridLayout(1,2));
        // create a Panel with 10 rows for the JCheckboxes
        checkboxPanel = new JPanel(new GridLayout(10,1));
        // create a Panel with 10 rows and 2 columns for the tasks
        taskPanel = new JPanel(new GridLayout(10,2));
        // create labels for the tasks instanciate them and
        // add them to the Panel
        labels = new JLabel[10][2];
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                labels[i][j] = new JLabel(" ");
                taskPanel.add(labels[i][j]);
            }
        }

        // create a JPanel and add two JButtons to it
        buttonPanel = new JPanel();
        // adds a new Task to the list
        JButton newTask = new JButton("New Task");
        newTask.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newTask();
            }
        });
        // deletes a Task from the list, because it is completed
        JButton complete = new JButton("Complete");
        complete.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.deleteTasks();
            }
        });

        // bring everything together
        headPanel.add(new JLabel("       Task"));
        headPanel.add(new JLabel("   Due Date"));
        buttonPanel.add(newTask);
        buttonPanel.add(complete);

        frame.add(headPanel, BorderLayout.NORTH);
        frame.add(checkboxPanel, BorderLayout.WEST);
        frame.add(taskPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(550, 400);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Creates the menu of the program.
     *
     * @return menu of the program
     */
    private JMenuBar menubar()
    {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        // clears all data and creates a fresh list
        JMenuItem todoList = new JMenuItem("New TODO-List");
        todoList.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.resetList();
                resetList();
            }
        });
        fileMenu.add(todoList);

        // quits the program
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        fileMenu.add(quit);

        JMenu optionsMenu = new JMenu("Options");

        // adds a new task to the list
        JMenuItem newTask = new JMenuItem("New Task");
        newTask.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newTask();
            }
        });
        optionsMenu.add(newTask);

        // deletes all selected tasks
        JMenuItem complete = new JMenuItem("Complete");
        complete.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.deleteTasks();
            }
        });
        optionsMenu.add(complete);

        // edit the selected task's text
        JMenuItem edit = new JMenuItem("Edit Task");
        edit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                if(model.oneChecked())
                {
                    String input = JOptionPane.showInputDialog("Please enter a "
                            + "new task.");
                    //only add, if there is a task entered
                    if(input != null && !input.isEmpty() &&
                            !input.matches("\\s+"))
                    {
                        model.setTask(input);
                    }
                }
            }
        });
        optionsMenu.add(edit);

        // edit the selected task's date
        JMenuItem postpone = new JMenuItem("Postpone");
        postpone.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(model.oneChecked())
                {
                    String input = JOptionPane.showInputDialog("Please enter "
                            + "a new date.");
                    //only add, if there is a task entered
                    if(input != null && !input.isEmpty() &&
                            !input.matches("\\s+"))
                    {
                        model.setDate(input);
                    }
                }
            }
        });
        optionsMenu.add(postpone);

        menu.add(fileMenu);
        menu.add(optionsMenu);

        return menu;
    }

    /**
     * Method to reset the list. It clears all JLabels and removes all
     * JCheckBoxes from the checkboxPanel.
     */
    public void resetList()
    {
        // clear all the labels
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                labels[i][j].setText(" ");
            }
        }
        // clear the checkboxPanel
        checkboxPanel.removeAll();
    }

    /**
     * Adds a new task to the list by using JOptionPanes for user input.
     * A task can only be added if there are less than 10 tasks in the list
     * already and if the user wrote something in the input-TextField.
     */
    public void newTask()
    {
        // make sure to have not more than 10 tasks in the list
        if(model.getListLength() >= 10)
        {
            JOptionPane.showMessageDialog(frame, "The allowed maximum "
                    + "of tasks is 10!", "Error",
                            JOptionPane.ERROR_MESSAGE);
        }else
        {
            Task task = new Task();
            String input = JOptionPane.showInputDialog("Please enter "
                    + "your task.");
            // only add, if there was a task entered
            // (whitespaces only are not allowed)
            if(input != null && !input.isEmpty() && !input.matches("\\s+"))
            {
                task.setTask(input);
                // a date can be entered but it is not required
                String time = JOptionPane.showInputDialog("Please enter "
                        + "a due date.");
                task.setDate(time);
                model.addTask(task);
            }
        }
    }

    /**
     * This method is called whenever the observed object is changed.
     * An application calls an <tt>Observable</tt> object's
     * <tt>notifyObservers</tt> method to have all the object's
     * observers notified of the change.
     *
     * @param o the observable object
     * @param arg an argument passed to the notifyObservers method
     */
    public void update(Observable o, Object arg)
    {
        if(o instanceof Model)              // if the notifier was a Model
        {
            if(arg instanceof LinkedList)   // if the notifier sent a LinkedList
            {
                resetList();
                // clear the frame from all remaining checkboxes
                frame.repaint();   

                LinkedList<Task> tasks = (LinkedList<Task>) arg;
                // print everything from the tasks-list everytime something
                // changed and add a Listener to the new JCheckBoxes.
                for(int i = 0; i < tasks.size(); i++)
                {
                        final Task task = tasks.get(i);
                        JCheckBox cb = new JCheckBox("", task.isChecked());
                        cb.addItemListener(new ItemListener()
                        {
                            public void itemStateChanged(ItemEvent e)
                            {
                                task.setChecked(!task.isChecked());
                            }
                        });
                        checkboxPanel.add(cb);
                        labels[i][0].setText(tasks.get(i).getTask());
                        labels[i][1].setText(tasks.get(i).getDate());
                }
            }else if (arg instanceof String)  // if the notifier sent a string
            {
                // create a JOptionPane with an error message
                String s = (String) arg;
                if (s.substring(0, 5).equals("ERROR"))
                {
                    JOptionPane.showMessageDialog(frame, s, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }        
            }
        }
    }

}
