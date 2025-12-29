public class A{
    public HttpResponse execute(HttpHost host, HttpRequest req, HttpContext ctx) throws IOException, ClientProtocolException {
        HttpResponse resp = backend.execute(host, req, ctx);
        if (assessor.isFailure(resp)) {
            throw new UnsuccessfulResponseException(resp);
        }
        return resp;
    }
}