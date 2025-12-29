public class A{
    public GifImage(URL url) throws IOException {
        fromUrl = url;
        InputStream is = null;
        try {
            is = url.openStream();
            process(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}