public class A{
    public void sendBinaryFile(String filename) throws IOException {
        Checker.checkEmpty(filename, "filename");
        URL url = _getFile(filename);
        OutputStream out = getOutputStream();
        Streams.copy(url.openStream(), out);
        out.close();
    }
}