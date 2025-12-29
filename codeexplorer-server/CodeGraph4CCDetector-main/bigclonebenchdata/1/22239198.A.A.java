public class A{
    public boolean loadURL(URL url) {
        try {
            propertyBundle.load(url.openStream());
            LOG.info("Configuration loaded from " + url + "\n");
            return true;
        } catch (Exception e) {
            if (canComplain) {
                LOG.warn("Unable to load configuration " + url + "\n");
            }
            canComplain = false;
            return false;
        }
    }
}