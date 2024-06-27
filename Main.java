package tpe;

public class Main {

    public static void main(String args[]) {

        System.out.println("---Original---");
        Servicios servicios = new Servicios("./datasets/Procesadores.csv",
                "./datasets/Tareas.csv");

        System.out.println("---Servicio 1---");
        System.out.println(servicios.servicio1("T2"));
        System.out.println(servicios.servicio1("T200"));
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

        System.out.println("---Casos de correccion---");
        Servicios svc4 = new Servicios("datasets/Procesadores01.csv", "datasets/Tareas01.csv");
        svc4.backtracking(200);
        svc4.greedy(200);
        Servicios svc5 = new Servicios("datasets/Procesadores01.csv", "datasets/Tareas01.csv");
        svc5.backtracking(10);
        svc5.greedy(10);
        Servicios svc6 = new Servicios("datasets/Procesadores01.csv", "datasets/Tareas02.csv");
        svc6.backtracking(200);
        svc6.greedy(200);
        Servicios svc7 = new Servicios("datasets/Procesadores01.csv", "datasets/Tareas02.csv");
        svc7.backtracking(100);
        svc7.greedy(100);
        // "Se rompe greedy con este caso de test: tiempo maximo para no refrigerados: 80
        Servicios svc8 = new Servicios("datasets/Procesadores01.csv", "datasets/Tareas02.csv");
        svc8.backtracking(80);
        svc8.greedy(80);
    }
}
