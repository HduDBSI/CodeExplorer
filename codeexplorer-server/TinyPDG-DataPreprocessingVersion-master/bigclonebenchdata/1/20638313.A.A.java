public class A{
    public void init() throws IOException {
        if (this.inputStream == null) this.inputStream = new BufferedInputStream(url.openStream()); else {
            this.inputStream.close();
            this.inputStream = new BufferedInputStream(url.openStream());
        }
    }
}