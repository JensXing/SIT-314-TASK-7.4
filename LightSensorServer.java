import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;

public class LightSensorServer extends CoapServer {
	public static void main(String[] args) {		
		LightSensorServer ss = new LightSensorServer();
		ss.start();
	}
	
	public LightSensorServer() {
		LightSensorResource lightResObj = new LightSensorResource();
		lightResObj.setObservable(true);
		lightResObj.getAttributes().hasObservable();
		add(lightResObj);
	}
	
	
	void addEndpoints() {
	for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
		if(addr instanceof Inet4Address || addr.isLoopbackAddress()) {
			InetSocketAddress bindToAddress = new InetSocketAddress(addr, NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT));
			CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
			//addEndpoint(new CoapEndpoint(bindToAddress));
			//new CoapEndpoint doesn't work. INstead we have to do builder.build as given in the hello world file
			addEndpoint(builder.build());
		}
	}

}
}