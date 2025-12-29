public class A{
    public void restoreDrivers() throws ExplorerException {
        try {
            drivers.clear();
            URL url = URLUtil.getResourceURL("default_drivers.xml");
            loadDefaultDrivers(url.openStream());
        } catch (IOException e) {
            throw new ExplorerException(e);
        }
    }
}