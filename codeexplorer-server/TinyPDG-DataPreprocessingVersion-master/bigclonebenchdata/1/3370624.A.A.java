public class A{
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            log.trace("passing in cookies: ", cookies);
            connection.setRequestProperty("Cookie", cookies);
            connection.getContent();
        } catch (Exception e) {
            log.error(e);
        }
    }
}