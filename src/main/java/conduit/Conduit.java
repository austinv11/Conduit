package conduit;

import conduit.common.RequestMethod;
import conduit.facade.*;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

public class Conduit {
	
	private final String baseUri;
	private final boolean followRedirects;
	private final HttpConnector connector;
	
	private Conduit(String baseUri, boolean followRedirects, HttpConnector connector) {
		this.baseUri = baseUri;
		this.followRedirects = followRedirects;
		this.connector = connector;
	}
	
	public String getBaseUri() {
		return baseUri;
	}
	
	public boolean isFollowingRedirects() {
		return followRedirects;
	}
	
	//TODO the rest of the methods
	
	private <I, O> Mono<HttpResponse<O>> request(String path, 
	                                             Function<? super HttpRequestBuilder<byte[], byte[]>, HttpRequestBuilder<I, O>> handler, 
	                                             RequestMethod method) {
		if (path.startsWith("/"))
			path = path.substring(1, path.length());
		
		return Mono.just(connector.createRequestBuilder(this, baseUri + path, method))
				.map(handler)
				.map(HttpRequestBuilder::build)
				.flatMap(r -> connector.submitRequest(this, r));
	}
	
	public Mono<HttpResponse<byte[]>> get(String path) {
		return get(path, req -> req);
	}
	
	public <I, O> Mono<HttpResponse<O>> get(String path, Function<? super HttpRequestBuilder<byte[], byte[]>, HttpRequestBuilder<I, O>> handler) {
		return request(path, handler, RequestMethod.GET);
	}
	
	public Mono<HttpResponse<byte[]>> post(String path) {
		return get(path, req -> req);
	}
	
	public <I, O> Mono<HttpResponse<O>> post(String path, Function<? super HttpRequestBuilder<byte[], byte[]>, HttpRequestBuilder<I, O>> handler) {
		return request(path, handler, RequestMethod.POST);
	}
	
	public Mono<HttpResponse<byte[]>> put(String path) {
		return get(path, req -> req);
	}
	
	public <I, O> Mono<HttpResponse<O>> put(String path, Function<? super HttpRequestBuilder<byte[], byte[]>, HttpRequestBuilder<I, O>> handler) {
		return request(path, handler, RequestMethod.PUT);
	}
	
	public Mono<HttpResponse<byte[]>> delete(String path) {
		return get(path, req -> req);
	}
	
	public <I, O> Mono<HttpResponse<O>> delete(String path, Function<? super HttpRequestBuilder<byte[], byte[]>, HttpRequestBuilder<I, O>> handler) {
		return request(path, handler, RequestMethod.DELETE);
	}
	
	public Mono<WebsocketConnection> ws() {
		return ws(req -> {});
	}
	
	public Mono<WebsocketConnection> ws(Consumer<? super WebsocketRequestBuilder> handler) {
		return Mono.just(connector.createWebsocketBuilder(this))
				.doOnNext(handler)
				.map(WebsocketRequestBuilder::build)
				.flatMap(r -> connector.openWebsocket(this, r));
	}
	
	public static Builder buildFor(String baseUri) {
		if (!baseUri.endsWith("/"))
			baseUri += "/";
		
		return new Builder(baseUri);
	}
	
	public static class Builder {
		
		private static final HttpConnector DEFAULT_CONNECTOR = null; //TODO add default connector
		
		private final String baseUri;
		private boolean followRedirects = true;
		private HttpConnector connector = DEFAULT_CONNECTOR;
		
		public Builder(String baseUri) {
			this.baseUri = baseUri;
		}
		
		public Builder setFollowRedirects(boolean followRedirects) {
			this.followRedirects = followRedirects;
			return this;
		}
		
		public Builder setConnector(HttpConnector connector) {
			this.connector = connector;
			return this;
		}
		
		public Conduit build() {
			return new Conduit(baseUri, followRedirects, connector);
		}
	}
}
