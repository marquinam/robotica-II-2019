import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


// trabaja con el touch en el S2
public class TerminarTelegrafo extends Thread {
	EV3TouchSensor pulsadorTerminar;
	SampleProvider sampleProvider;
	
	public boolean terminar;
	
	public TerminarTelegrafo(){
		pulsadorTerminar = new EV3TouchSensor(SensorPort.S2);
		sampleProvider = pulsadorTerminar.getTouchMode();
		
		terminar = false;
	}
	
	
	public void run(){
		while(terminar == false) {
			float[] dato = new float[sampleProvider.sampleSize()];
			sampleProvider.fetchSample(dato, 0);
			float estado = dato[0];
			if (estado == 1.0) {
				terminar = true;
			}

		}
	}
}
