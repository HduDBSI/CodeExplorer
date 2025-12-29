public class A{
    public InputStream getPage(String page) throws IOException {
        URL url = new URL(hattrickServerURL + "/Common/" + page);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestProperty("Cookie", sessionCookie);
        return huc.getInputStream();
    }
}