public class Main {

    public static void main(String[] args) { // Тест функционала

        Manager manager = new Manager();

        Task task1 = new Task("Помыть ежа","Помыть ежа и не уколоться","NEW");
        Task task2 = new Task("Купить слона","Купить слона и не сойти с ума","NEWS");
        Epic epic1 = new Epic("Переехать в Ботсвану","Купить билеты и собрать чемодан");
        Epic epic2 = new Epic("Убраться в комнате","Пропылесосить комнату");
        SubTask sub1 = new SubTask("Купить билеты на самолет","Купить билеты на рейс в Ботсвану", "NEW", epic1.getTaskID());
        SubTask sub2 = new SubTask("Сесть в самолет","Сесть в самолет и улететь в Ботсвану","NEW", epic1.getTaskID());
        SubTask sub3 = new SubTask("Пропылесосить комнату","Включить пылесос и пропылесосить комнату","NEW",epic2.getTaskID());

        //Добавляем объекты в БД

        manager.createTask(task1);
        manager.createTask(task2);
        task2.setStatus("NEW");
        manager.createTask(task2);
        manager.printAllTasks();
        task1.setDescription("Помыть ежа с мылом и щеткой");
        manager.updateTask(task1);
        manager.printAllTasks();
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubTask(sub1);
        manager.createSubTask(sub2);
        manager.createSubTask(sub3);
    }
}
