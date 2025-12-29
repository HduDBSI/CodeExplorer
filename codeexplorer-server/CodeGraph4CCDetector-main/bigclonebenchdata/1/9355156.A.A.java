public class A{
    public void addScanURL(final URL url) {
        if (url == null) throw new NullArgumentException();
        try {
            url.openConnection().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlList.add(url);
    }
}