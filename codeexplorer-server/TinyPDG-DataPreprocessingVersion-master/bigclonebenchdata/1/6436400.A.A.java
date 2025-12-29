public class A{
    @Override
    protected HttpResponse<HttpURLConnection> execute(HttpRequest<HttpURLConnection> con) throws HttpRequestException {
        HttpURLConnection unwrap = con.unwrap();
        try {
            unwrap.connect();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
        return new UrlHttpResponse(unwrap);
    }
}