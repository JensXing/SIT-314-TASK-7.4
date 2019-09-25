import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class LightSensorResource extends CoapResource {

	private int light = 0;
	public LightSensorResource() {
		super("LightSensor");
		
		Timer time = new Timer();
		time.schedule(new UpdateTask(this),0,1000);
		// TODO Auto-generated constructor stub
	}
	
	private class UpdateTask extends TimerTask {
		private CoapResource mCoapres;
		
		public UpdateTask(CoapResource coapres) {
			mCoapres = coapres;
		}
		
		public void run() {
			light = new Random().nextInt(20);
			mCoapres.changed();
		}
	}
	
	public void handleGET(CoapExchange exchange) {
		exchange.respond(ResponseCode.CONTENT, " " + light, MediaTypeRegistry.TEXT_PLAIN);
	}

}
