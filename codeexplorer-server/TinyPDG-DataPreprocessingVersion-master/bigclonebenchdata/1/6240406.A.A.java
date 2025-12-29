public class A{
    public static Image loadImage(URL url) throws IOException {
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        try {
            return getLoader(url.getFile()).loadImage(in);
        } finally {
            in.close();
        }
    }
}