import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.TouchAdapter;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class BrickMorse {
	EV3TouchSensor pulsadorMorse;
	SampleProvider sampleProvider;
	TouchAdapter touchAdapter;
	
	EV3LargeRegulatedMotor izquierda;
	EV3LargeRegulatedMotor derecha;
	EV3LargeRegulatedMotor lapiz;
	
	Stopwatch stopwatch;

	public BrickMorse() {
		pulsadorMorse = new EV3TouchSensor(SensorPort.S3);
		sampleProvider = pulsadorMorse.getTouchMode();
		touchAdapter = new TouchAdapter(pulsadorMorse);//
		
		stopwatch = new Stopwatch();

		izquierda = new EV3LargeRegulatedMotor(MotorPort.A);
		derecha = new EV3LargeRegulatedMotor(MotorPort.D);
		lapiz = new EV3LargeRegulatedMotor(MotorPort.C);

	}

	
	public String capturarCaracterMorse() {
		String simbolo = "";
		float[] dato = new float[sampleProvider.sampleSize()];
		sampleProvider.fetchSample(dato, 0);
		float estado = dato[0];
		
		stopwatch.reset();
		int tiempoPulsacion = 0;
		if (estado == 1.0){
			while(touchAdapter.isPressed() == true) {
				System.out.println("hay pulsacion en touch morse");
				Delay.msDelay(5000);
			}
			tiempoPulsacion = stopwatch.elapsed();
		}
		else if(estado == 0.0) {
			while(touchAdapter.isPressed() == false) {
				System.out.println("No hay pulsacion en touch morse");
				//Delay.msDelay(500);
			}
			tiempoPulsacion = -1 * stopwatch.elapsed();
		}
		
		System.out.println("tiempoPulsacion=" + tiempoPulsacion);
		
		if(tiempoPulsacion > 0 && tiempoPulsacion < 1000){
			
			simbolo = ".";
			System.out.println("espunto" + simbolo);
			
		} else if(tiempoPulsacion >= 1000 && tiempoPulsacion < 2000) {
			simbolo = "_";
		
	    } else if(tiempoPulsacion < 0 && Math.abs(tiempoPulsacion) <1000) {
			simbolo = "%"; // separamos simbolos
		} else if(tiempoPulsacion < 0 && Math.abs(tiempoPulsacion) >= 1000 && Math.abs(tiempoPulsacion) <= 2000) {
			simbolo = "$"; // separamos letras
		}
		return simbolo;
	}
	
	
	
	public void dibujarMorse(String morse) {
		char[] morseArey = morse.toCharArray();
		// int contador=0;
		for (int i = 0; i < morseArey.length; i++) {
			char caracter = morseArey[i];
			char anterior = 'a';

			int gradosLapiz1 = 100;

			int gradosLapizMenos2 = -100;

			double distancia = 10;

			if (caracter == '.') {

				// bajar lapiz super rapido

				bajarLapiz((-1) * gradosLapiz1);

				lapiz.rotate(gradosLapizMenos2);

				// subir lapiz

				subirLapiz(gradosLapiz1);

				lapiz.rotate(gradosLapiz1);

				anterior = caracter;

			} else if (caracter == '_') {

				// bajar lapiz asta el suelo
				bajarLapiz((-1) * gradosLapiz1);

				lapiz.rotate(gradosLapizMenos2);

				// avanzar distancia e 40 cm con lapiz abajo
				double avanzarDistancia = 10;
				avanzarDistancia(avanzarDistancia);

				// subir lapiz

				subirLapiz(gradosLapiz1);
				lapiz.rotate(gradosLapiz1);

				// lapiz.stop();

				anterior = caracter;

			} else if (caracter == '$') {
				if (anterior != '.' || anterior != '_') {
					// hacer espacio entre carcteres de una letra

					// avanzar
					double avanzarDistancia = 10;
					avanzarDistancia(avanzarDistancia);

				} else {

				}
			}
		}

	}

	public void avanzarDistancia(double avanzarDistancia) {

		int grados = 0;
		double diametro = 5.5;
		double circunferencia = diametro * Math.PI;
		double numeroRotaciones = avanzarDistancia / circunferencia;
		grados = (int) (numeroRotaciones * 360);

		int velocidad = 100;

		derecha.setSpeed(velocidad);
		derecha.rotate(grados, true);
		izquierda.setSpeed(velocidad);
		izquierda.rotate(grados);

	}

	public void subirLapiz(int gradosLapiz1) {
		double gradoRuedaLapizArriba = 10;

		double diametro = 5.5;
		double circunferencia = diametro * Math.PI;
		// calcular lapiz ariba usando la distancia lapiz arriba
		// double distanciaLapizArriba=40;
		double numeroRotaciones = gradoRuedaLapizArriba / circunferencia;
		gradosLapiz1 = (int) (numeroRotaciones * 360);
		
		//int velocidad = 100;

		//lapiz.setSpeed(velocidad);
		//lapiz.rotate(gradosLapiz1, true);
		//lapiz.setSpeed(velocidad);
		//lapiz.rotate(gradosLapiz1);

	}

	private void bajarLapiz(int i) {

		double diametro = 5.5;
		double circunferencia = diametro * Math.PI;
		// calcular lapiz ariba usando la distancia lapiz arriba
		double distanciaLapizAbajo = 10;
		double numeroRotaciones = distanciaLapizAbajo / circunferencia;
		i = (int) (numeroRotaciones * 360);

	}

	public String buscarLetraMorse(String letra) {
		// la letra dependiendo A LO QUE SE VA USAR
		String resultado = "";
		if (letra.equals("...")) {
			resultado = "s";
		} else if (letra.equals("___")) {
			resultado = "o";
		} else if (letra.equals("._.")) {
			resultado = "r";

		} else if (letra.equals("_...")) {
			resultado = "b";
		} else if (letra.equals("_")) {
			resultado = "t";

		} else if (letra.equals("..")) {
			resultado = "i";
		} else if (letra.equals("_._.")) {
			resultado = "c";
		} else if (letra.equals("._")) {
			resultado = "a";
		} else if (letra.equals("__")) {
			resultado = "m";
		} else if (letra.equals("_.")) {
			resultado = "n";
		} else if (letra.equals(".")) {
			resultado = "e";
		} else if (letra.equals(".._")) {
			resultado = "u";
		} else if (letra.equals("_..")) {
			resultado = "d";
		} else if (letra.equals("._")) {
			resultado = "a";
		}

		return resultado;
	}

	public String traducir(String morse) {
		String resultado = "";
		System.out.println("morse :)" + morse);
		char[] codigo = morse.toCharArray();
		String letra = "";
		for (int posicion = 0; posicion < codigo.length; posicion = posicion + 1) {
			char simbolo = codigo[posicion];
			if (simbolo == '.') {
				letra = letra + simbolo;

			} else if (simbolo == '_') {
				letra = letra + simbolo;

			} else if (simbolo == '$') {
				System.out.println(" estan separados");

			} else if (simbolo == '%') {
				System.out.println("encuentre separador de letra");

				String traducido = buscarLetraMorse(letra);
				resultado = resultado + traducido;
				System.out.println("jejeje :" + resultado);
				letra = "";
			}
		}
		System.out.println("jejeje :)" + resultado);
		return resultado;

	}

	/*public boolean estapresionandoTouch() {

		boolean estaPressed = false;
		float[] dato = new float[capturaTouch.sampleSize()];
		capturaTouch.fetchSample(dato, 0);
		float estado = dato[0];
		if (estado == 1.0) {
			estaPressed = true;
		}

		return estaPressed;
	}*/

}
