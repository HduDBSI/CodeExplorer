public class A{
    public static void run(File targetFolder, URL url) throws UpdateException {
        try {
            run(targetFolder, new ZipInputStream(url.openStream()));
        } catch (Exception e) {
            if (e instanceof UpdateException) throw (UpdateException) e; else throw new UpdateException(e);
        }
    }
}