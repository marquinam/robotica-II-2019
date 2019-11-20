
import lejos.hardware.Button;


public class JefeRobotMain {

	public static void main(String[] args) {
		BrickMorse brick = new BrickMorse();
		TerminarTelegrafo terminador = new TerminarTelegrafo();
		terminador.start();

		System.out.println("presione un boton para comenzar");
		Button.waitForAnyPress();
		// mientras no toque el pulsador S2, puedo capturar morse
		String morse = "";
		while(terminador.terminar == false){
			String simbolo = brick.capturarCaracterMorse();
			morse = morse + simbolo;
		}
		System.out.println("morse="+morse);
		
		System.out.println("presione un boton para dibujar");
		Button.waitForAnyPress();		
		brick.dibujarMorse(morse);
		
		System.out.println("presione un boton para traducir");
		Button.waitForAnyPress();
		String traducido = brick.traducir(morse);
		System.out.println("mose=" + morse + " ,traducido=" + traducido);
		
		System.out.println("presione un boton para salir");
		Button.waitForAnyPress();

	}

}
