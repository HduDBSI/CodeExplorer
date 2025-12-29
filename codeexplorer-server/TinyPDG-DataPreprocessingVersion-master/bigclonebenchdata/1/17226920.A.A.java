public class A{
    public void setUrl(URL url) throws PDFException, PDFSecurityException, IOException {
        InputStream in = null;
        try {
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            String pathOrURL = url.toString();
            setInputStream(in, pathOrURL);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}