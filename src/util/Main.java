package util;

import elements.Epic;
import elements.SubTask;
import elements.Task;

public class Main {

    public static void main(String[] args) { // Тест функционала

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Помыть ежа","Помыть ежа и не уколоться",Statuses.NEW);
        Task task2 = new Task("Купить слона","Купить слона и не сойти с ума",Statuses.NEW);
        Epic epic1 = new Epic("Переехать в Ботсвану","Купить билеты и собрать чемодан");
        Epic epic2 = new Epic("Убраться в комнате","Пропылесосить комнату");
        SubTask sub1 = new SubTask("Купить билеты на самолет","Купить билеты на рейс в Ботсвану", Statuses.NEW,3);
        SubTask sub2 = new SubTask("Сесть в самолет","Сесть в самолет и улететь в Ботсвану",Statuses.NEW, 3);
        SubTask sub3 = new SubTask("Пропылесосить комнату","Включить пылесос и пропылесосить комнату",Statuses.NEW,4);

        //Добавляем объекты в БД

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.printAllTasks();
        task1.setDescription("Помыть ежа с мылом и щеткой");
        taskManager.getTask(1);
        taskManager.getTask(2);
        System.out.println("История:");
        System.out.println(taskManager.getHistory());
        taskManager.updateTask(task1);
        taskManager.printAllTasks();
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createSubTask(sub1);
        taskManager.createSubTask(sub2);
        taskManager.createSubTask(sub3);
        taskManager.printAllSubTasks();
        taskManager.printAllEpics();
        taskManager.updateSubTask(sub1);
        sub2.setStatus(Statuses.DONE);
        sub3.setStatus(Statuses.DONE);
        taskManager.updateSubTask(sub2);
        taskManager.updateSubTask(sub3);
        taskManager.printAllSubTasks();
        taskManager.printAllEpics();
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
        taskManager.deleteSubTask(sub3.getTaskID());
        taskManager.printAllEpics();
        taskManager.deleteEpic(epic2.getTaskID());
        taskManager.printAllEpics();
        taskManager.printAllSubTasks();
        taskManager.removeAllTasks();
        taskManager.printAllTasks();
        taskManager.removeAllSubTasks();
        taskManager.printAllEpics();
        taskManager.printAllSubTasks();
        taskManager.removeAllEpics();
        taskManager.printAllEpics();
    }
}
