public class A{
    public HttpResponse executeHttp(final HttpUriRequest request, final int expectedCode) throws ClientProtocolException, IOException, HttpException {
        final HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != expectedCode) {
            throw newHttpException(request, response);
        }
        return response;
    }
}