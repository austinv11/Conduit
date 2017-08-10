package conduit.facade;

import conduit.Conduit;
import conduit.common.RequestMethod;
import reactor.core.publisher.Mono;

public interface HttpConnector {
	
	<I, O> Mono<HttpResponse<O>> submitRequest(Conduit conduit, HttpRequest<I, O> packet);
	
	Mono<WebsocketConnection> openWebsocket(Conduit conduit, WebsocketRequest packet);
	
	HttpRequestBuilder<byte[], byte[]> createRequestBuilder(Conduit conduit, String uri, RequestMethod method);
	
	WebsocketRequestBuilder<byte[], byte[]> createWebsocketBuilder(Conduit conduit);
}
