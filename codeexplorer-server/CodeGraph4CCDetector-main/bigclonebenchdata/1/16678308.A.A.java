public class A{
    private static String completeGet(String encodedURLStr) throws IOException {
        URL url = new URL(encodedURLStr);
        HttpURLConnection connection = initConnection(url);
        String result = getReply(url.openStream());
        connection.disconnect();
        return result;
    }
}