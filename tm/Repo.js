import {Task} from './Task';

export class Repo{
    constructor(){
        this.tasks = [
            new Task("task1","to do","12.12.2017"),
            new Task("task2","in progress","12.12.2017"),
            new Task("task3","done","12.12.2017")
        ];
    }
};