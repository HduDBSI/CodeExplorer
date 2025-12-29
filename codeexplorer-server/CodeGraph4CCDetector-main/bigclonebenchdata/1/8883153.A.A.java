public class A{
    protected int getResponseCode(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        try {
            con.connect();
            return con.getResponseCode();
        } finally {
            con.disconnect();
        }
    }
}