package tpe;

public class Main {

	public static void main(String args[]) {
		System.out.println("---Original---");
		Servicios servicios = new Servicios("./datasets/Procesadores.csv",
				"./datasets/Tareas.csv");
		System.out.println(servicios.servicio1("T2"));
		System.out.println(servicios.servicio2(true));

		System.out.println(servicios.servicio3(32,92));
		servicios.backtracking(63);

		System.out.println("---Procesadores sin refrigerar---");
		Servicios svc1 = new Servicios("datasets/Procesadores-unrefrigerated.csv",
				"./datasets/Tareas.csv");
		svc1.backtracking(0);

		System.out.println("---All critical tasks---");
		Servicios svc2 = new Servicios("datasets/Procesadores.csv",
				"./datasets/Tareas-all_critical.csv");
		svc2.backtracking(105);
	}
}
