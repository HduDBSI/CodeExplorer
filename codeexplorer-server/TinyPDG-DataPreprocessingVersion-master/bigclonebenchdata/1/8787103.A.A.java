public class A{
    private void headinfoThread() {
        try {
            URLConnection urlc = resource.url.openConnection();
            resource.setFileSize(urlc.getContentLength());
            resource.setDate(urlc.getLastModified());
        } catch (IOException e) {
            System.out.println("Error ResourceConnection, downloading headinfo");
            System.out.println(e);
        }
    }
}