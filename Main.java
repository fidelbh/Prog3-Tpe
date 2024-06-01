package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
		System.out.println(servicios.servicio1("T2"));

		System.out.println(servicios.servicio3(32,92));
	}
}
