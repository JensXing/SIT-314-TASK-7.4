import java.io.IOException;
import java.util.Scanner;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.elements.exception.ConnectorException;

public class LightSensorCLient {
	public static void main(String[] args) {
					
		//Synchronously send the GET message (blocking call)
		//Try and catch block is required for client.advanced(request)
		CoapResponse coapResp;
		try {
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
		    String readString = scanner.nextLine();
		    while(readString!=null) {
		        //System.out.println(readString);
		        if (readString.isEmpty()) {
		            System.out.println("Read Enter Key.");
		            //System.exit(0);
		            CoapClient client = new CoapClient("coap://127.0.0.1:5683/LightSensor");
		    		Request request = new Request(Code.GET);
		    		
		            coapResp = client.advanced(request);
					//The "CoapResponse" message contains the response.
					System.out.println(Utils.prettyPrint(coapResp));
					
					CoapObserveRelation relation = client.observe(new CoapHandler() {
						@Override
						public void onLoad(CoapResponse response) {
							// TODO Auto-generated method stub
							@SuppressWarnings("unused")
							String content = response.getResponseText();
							System.out.println("NOTIFICATION: " + content);
						}
						@Override
						public void onError() {
							// TODO Auto-generated method stub
							System.err.println("OBSERVING FAILED!!");
						}
					});					
					relation.proactiveCancel();					
		        }
		        if (scanner.hasNextLine()) {
		            readString = scanner.nextLine();
		        } else {
		            readString = null;
		        }
		    }
		    
		} catch (ConnectorException A) {
			// TODO Auto-generated catch block
			A.printStackTrace();
		} catch (IOException A) {
			// TODO Auto-generated catch block
			A.printStackTrace();
		}
		
	}
}
