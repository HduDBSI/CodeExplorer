public class A{
    protected HttpResponse executeRequest(AbstractHttpRequest request) throws ConnectionException, RequestCancelledException {
        try {
            HttpResponse response = getHttpClient().execute(request);
            if (!response.is2xxSuccess()) {
                throw new ConnectionException();
            }
            return response;
        } catch (IOException ex) {
            throw new ConnectionException();
        } catch (TimeoutException ex) {
            throw new ConnectionException();
        }
    }
}