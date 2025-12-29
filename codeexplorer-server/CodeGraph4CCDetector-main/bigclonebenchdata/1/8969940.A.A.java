public class A{
    private void Download(String uri) throws MalformedURLException {
        URL url = new URL(uri);
        try {
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException ex) {
            bm = getError();
        }
    }
}