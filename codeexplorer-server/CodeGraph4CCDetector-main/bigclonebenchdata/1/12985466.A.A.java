public class A{
    @Override
    protected Map<String, Definition> loadDefinitionsFromURL(URL url) {
        Map<String, Definition> defsMap = null;
        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            lastModifiedDates.put(url.toExternalForm(), connection.getLastModified());
            defsMap = reader.read(connection.getInputStream());
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("File " + null + " not found, continue [" + e.getMessage() + "]");
            }
        }
        return defsMap;
    }
}