public class A{
    public static ByteBuffer readURL(URL url) throws IOException, MalformedURLException {
        URLConnection connection = null;
        try {
            connection = url.openConnection();
            return readInputStream(new BufferedInputStream(connection.getInputStream()));
        } catch (IOException e) {
            throw e;
        }
    }
}