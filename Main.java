package tpe;

public class Main {

    public static void main(String args[]) {

        System.out.println("---Original---");
        Servicios servicios = new Servicios("./datasets/Procesadores.csv",
                "./datasets/Tareas.csv");

        System.out.println("---Servicio 1---");
        System.out.println(servicios.servicio1("T2"));
        System.out.println("---Servicio 2---");
        System.out.println(servicios.servicio2(true));
        System.out.println("---Servicio 3---");
        System.out.println(servicios.servicio3(32, 92));
        servicios.backtracking(63);
        servicios.greedy(63);


        System.out.println("---Procesadores sin refrigerar---");
        Servicios svc1 = new Servicios("datasets/Procesadores-unrefrigerated.csv",
                "./datasets/Tareas.csv");
        svc1.backtracking(0);
        svc1.greedy(0);

        System.out.println("---All critical tasks---");
        Servicios svc2 = new Servicios("datasets/Procesadores.csv",
                "./datasets/Tareas-all_critical.csv");
        svc2.backtracking(105);
        svc2.greedy(105);

        System.out.println("---More tasks---");
        Servicios svc3 = new Servicios("datasets/Procesadores.csv",
                "./datasets/Tareas-more.csv");
        svc3.backtracking(63);
        svc3.greedy(63);


    }
}
