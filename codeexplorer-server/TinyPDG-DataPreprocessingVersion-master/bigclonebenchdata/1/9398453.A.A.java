public class A{
    private static boolean isRemoteFileExist(String url) {
        InputStream in = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            in = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in != null) {
            return true;
        } else {
            return false;
        }
    }
}