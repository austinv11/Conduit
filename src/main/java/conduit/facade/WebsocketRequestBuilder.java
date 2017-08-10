package conduit.facade;

public interface WebsocketRequestBuilder<IN, OUT> {
	
	WebsocketRequest build();
}
