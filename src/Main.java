import manager.FileBackedTaskManager;
import manager.Managers;
import model.Status;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) { // Тест функционала
     //TaskManager taskManager = Managers.getDefault();
     FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile("fileManager.csv");
         System.out.println(taskManager.getEpics());
         System.out.println(taskManager.getTasks());
         System.out.println(taskManager.getSubTasks());
         System.out.println(taskManager.getHistory());
        Task task1 = new Task(taskManager.getNewId(), "Помыть ежа","Помыть ежа и не уколоться", Status.NEW);
        taskManager.createTask(task1);
        taskManager.getTask(8);
 /*        Task task2 = new Task(taskManager.getNewId(), "Купить слона","Купить слона и не сойти с ума", Status.NEW);
        Epic epic1 = new Epic(taskManager.getNewId(), "Переехать в Ботсвану","Купить билеты и собрать чемодан");
        Epic epic2 = new Epic(taskManager.getNewId(), "Убраться в комнате","Пропылесосить комнату");
        SubTask sub1 = new SubTask(taskManager.getNewId(), "Купить билеты на самолет","Купить билеты на рейс в Ботсвану", Status.NEW,3);
        SubTask sub2 = new SubTask(taskManager.getNewId(), "Сесть в самолет","Сесть в самолет и улететь в Ботсвану", Status.NEW, 3);
        SubTask sub3 = new SubTask(taskManager.getNewId(), "Пропылесосить комнату","Включить пылесос и пропылесосить комнату", Status.NEW,4);

        //Добавляем объекты в БД


        taskManager.createTask(task2);
        System.out.println(taskManager.getTasks());
        task1.setDescription("Помыть ежа с мылом и щеткой");
        taskManager.getTask(1);
        taskManager.getTask(2);
//      System.out.println("История:");
//      System.out.println(taskManager.getHistory());
        taskManager.updateTask(task1);
        System.out.println(taskManager.getTasks());
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createSubTask(sub1);
        taskManager.createSubTask(sub2);
        taskManager.createSubTask(sub3);
       System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        taskManager.updateSubTask(sub1);
        sub2.setStatus(Status.DONE);
        sub3.setStatus(Status.DONE);
        taskManager.updateSubTask(sub2);
        taskManager.updateSubTask(sub3);
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        taskManager.getSubTask(5);
        taskManager.getSubTask(6);
        taskManager.getSubTask(7);
        System.out.println("История:");
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(4);
        taskManager.getEpic(3);
        System.out.println("История 1:");
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(3);
        taskManager.getEpic(3);
        taskManager.getEpic(3);
        taskManager.getEpic(3);
        taskManager.getEpic(3);
        System.out.println("История 2:");
        System.out.println(taskManager.getHistory());
        taskManager.deleteSubTask(sub3.getId());
        System.out.println(taskManager.getEpics());
        taskManager.deleteEpic(epic2.getId());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        taskManager.removeAllTasks();
        System.out.println(taskManager.getEpics());
        taskManager.removeAllSubTasks();
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        taskManager.removeAllEpics();
        System.out.println(taskManager.getEpics()); */
    }
}
