public class A{
    private boolean load(URL url) {
        try {
            URLConnection connection = url.openConnection();
            parser = new PDFParser(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}