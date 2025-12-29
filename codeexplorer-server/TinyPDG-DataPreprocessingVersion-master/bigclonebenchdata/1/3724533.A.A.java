public class A{
    public static Document getDocument(URL url) throws Exception {
        InputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            return getDocumentBuilder().parse(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }
}